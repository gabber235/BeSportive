import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
import {FieldValue} from "@google-cloud/firestore";
import {faker} from "@faker-js/faker";


admin.initializeApp();

export const onUserCreate = functions.auth.user().onCreate(async (callingUser) => {
    const userRef = admin.firestore().collection("users").doc(callingUser.uid);

    const user = await admin.auth().getUser(callingUser.uid);

    const email = user.email;
    let name = user.displayName;
    let photoUrl = user.photoURL;


    if (!name) {
        name = faker.name.fullName();
        await admin.auth().updateUser(user.uid, {
            displayName: name,
        });
    }
    if (!photoUrl) {
        photoUrl = `https://api.multiavatar.com/${user.uid}.png`;
        await admin.auth().updateUser(user.uid, {
            photoURL: photoUrl,
        });
    }

    await userRef.set({
        email: email,
        name: name,
        photoUrl: photoUrl,
        totalTime: 0,
        totalChallenges: 0,
        totalPoints: 0,
    });
});

export const onUserDelete = functions.auth.user().onDelete(async (user) => {
    const userRef = admin.firestore().collection("users").doc(user.uid);
    await userRef.delete();
});

/**
 * When the users document is updated in Firestore "/users/{userId}",
 * Check if the name or photoUrl has changed and update the user's name and photoUrl in the auth service
 * And update the user's name and photoUrl in the group document in Firestore "/groups/{groupId}" under
 * "members.{userId}.name" and "members.{userId}.photoUrl"
 */
export const onUserUpdate = functions.firestore
    .document("/users/{userId}")
    .onUpdate(async (change, context) => {
        const {userId} = context.params;
        const {name: oldName, photoUrl: oldPhotoUrl} = change.before.data() as { name: string, photoUrl: string };
        const {name, photoUrl} = change.after.data() as { name: string, photoUrl: string };

        // Check if the name or photoUrl has changed
        if (oldName === name && oldPhotoUrl === photoUrl) {
            return;
        }

        // Update the user's name and photoUrl in the auth service
        await admin.auth().updateUser(userId, {
            displayName: name,
            photoURL: photoUrl,
        });

        // Update the user's name and photoUrl in the group document in Firestore "/groups/{groupId}"

        const group = await admin.firestore().collection("groups")
            .where(`members.${userId}`, "!=", null)
            .limit(1)
            .get();

        if (group.empty) {
            return;
        }

        const groupRef = group.docs[0].ref;
        await groupRef.update({
            [`members.${userId}.name`]: name,
            [`members.${userId}.photoUrl`]: photoUrl,
        });
    });

export const createGroup = functions.https.onCall(async (data, context) => {
    const name = data;
    const auth = context.auth;
    if (!auth) {
        throw new functions.https.HttpsError(
            "unauthenticated",
            "User must be authenticated to create a group",
        );
    }
    const {uid} = auth;
    const user = await admin.auth().getUser(uid);

    // Check if user is already in a group
    if (await isInGroup(uid)) {
        throw new functions.https.HttpsError(
            "already-exists",
            "User is already in a group",
        );
    }

    const groupRef = admin.firestore().collection("groups").doc();
    await groupRef.set({
        name,
        creationDate: FieldValue.serverTimestamp(),
        admin: uid,
        members: {
            [uid]: {
                name: user.displayName,
                photoUrl: user.photoURL,
            },
        },
        code: generateGroupCode(),
    });

    return groupRef.id;
});

export const joinGroup = functions.https.onCall(async (data, context) => {
    const code = data;
    const auth = context.auth;
    if (!auth) {
        throw new functions.https.HttpsError(
            "unauthenticated",
            "User must be authenticated to join a group",
        );
    }
    const {uid} = auth;
    const user = await admin.auth().getUser(uid);

    // Check if user is already in a group
    if (await isInGroup(uid)) {
        throw new functions.https.HttpsError(
            "already-exists",
            "User is already in a group",
        );
    }

    const groupRef = await admin.firestore().collection("groups")
        .where("code", "==", code)
        .limit(1)
        .get();

    if (groupRef.empty) {
        throw new functions.https.HttpsError(
            "not-found",
            "Group not found",
        );
    }

    const group = groupRef.docs[0];
    await group.ref.update({
        [`members.${uid}`]: {
            name: user.displayName,
            photoUrl: user.photoURL,
        },
    });

    return group.id;
});
/**
 *  When a file is uploaded to "{groupId}/{activeChallengeId}/image.jpg" in the storage bucket,
 *  Add the image url to the challenge document in Firestore "/groups/{groupId}/activeChallenges/{activeChallengeId}"
 *  as "photoUrl"
 */
export const onUploadChallengePhoto = functions.storage.object().onFinalize(async (object) => {
    const {bucket, name} = object;
    const bucketRef = admin.storage().bucket(bucket);

    if (!name) {
        return;
    }

    // Check if the file is in the correct directory
    const path = name.split("/");
    if (path.length !== 3 || path[2] !== "image.jpg") {
        return;
    }

    const [groupId, activeChallengeId] = path;
    const docRef = admin.firestore().doc(`groups/${groupId}/activeChallenges/${activeChallengeId}`);

    // Check if the challenge exists
    if (!(await docRef.get()).exists) {
        return;
    }

    // Get the public url of the image
    const url = bucketRef.file(name).publicUrl();

    // Update the challenge document
    await docRef.update({
        photoUrl: url,
    });
});

/**
 * When a document is created in "/groups/{groupId}/completedChallenges/{completedChallengeId}",
 * Increase the user's total time and total challenges in the user document in Firestore "/users/{userId}"
 * And update the user's points in the group document in Firestore "/groups/{groupId}" under "members.{userId}.points"
 */
export const onCompletedChallengeCreate = functions.firestore
    .document("/groups/{groupId}/completedChallenges/{completedChallengeId}")
    .onCreate(async (snapshot, context) => {
        const {groupId} = context.params;
        const {userId, duration} = snapshot.data() as { userId: string, duration: number };

        // Update the user's points based on the difficulty of the challenge.
        // The difficulty is stored in the challenge document in Firestore "/groups/{groupId}/challenges/{challengeId}"
        const challengeId = snapshot.data()?.challengeId;
        const challengeRef = admin.firestore().doc(`groups/${groupId}/challenges/${challengeId}`);
        const challenge = await challengeRef.get();

        // Difficulty is stored as a number from 0 to 2 (0 = easy, 1 = medium, 2 = hard)
        const difficulty: number = challenge.data()?.difficulty;

        const points = 100 * (difficulty + 1);

        // Update the user's total time and total challenges
        await admin.firestore().doc(`users/${userId}`).update({
            totalTime: FieldValue.increment(duration),
            totalChallenges: FieldValue.increment(1),
            totalPoints: FieldValue.increment(points),
        });


        // Update the user's points
        await admin.firestore().doc(`groups/${groupId}`).update({
            [`members.${userId}.points`]: FieldValue.increment(points),
        });
    });

/**
 * Generates a random 5 character group code.
 * @return {string} The group code.
 */
function generateGroupCode() {
    const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    let code = "";
    for (let i = 0; i < 5; i++) {
        code += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return code;
}

/**
 * Checks if a user is already in a group.
 * @param {string} uid The user's uid.
 * @return {Promise<boolean>} True if the user is in a group, false otherwise.
 */
async function isInGroup(uid: string) {
    const existingGroup = await admin.firestore().collection("groups")
        .orderBy(`members.${uid}`)
        .limit(1)
        .get();

    return !existingGroup.empty;
}
