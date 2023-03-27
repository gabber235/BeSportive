import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
import {FieldValue} from "@google-cloud/firestore";
import {faker} from "@faker-js/faker";

admin.initializeApp();

// eslint-disable-next-line max-len
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
  });
});

export const onUserDelete = functions.auth.user().onDelete(async (user) => {
  const userRef = admin.firestore().collection("users").doc(user.uid);
  await userRef.delete();
});

export const createGroup = functions.https.onCall(async (data, context) => {
  const name = data;
  const auth = context.auth;
  if (!auth) {
    throw new functions.https.HttpsError(
      "unauthenticated",
      "User must be authenticated to create a group"
    );
  }
  const {uid} = auth;
  const user = await admin.auth().getUser(uid);

  // Check if user is already in a group
  if (await isInGroup(uid)) {
    throw new functions.https.HttpsError(
      "already-exists",
      "User is already in a group"
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
      "User must be authenticated to join a group"
    );
  }
  const {uid} = auth;
  const user = await admin.auth().getUser(uid);

  // Check if user is already in a group
  if (await isInGroup(uid)) {
    throw new functions.https.HttpsError(
      "already-exists",
      "User is already in a group"
    );
  }

  const groupRef = await admin.firestore().collection("groups")
    .where("code", "==", code)
    .limit(1)
    .get();

  if (groupRef.empty) {
    throw new functions.https.HttpsError(
      "not-found",
      "Group not found"
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
