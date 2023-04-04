import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
import {FieldValue} from "@google-cloud/firestore";

admin.initializeApp();

export const onUserCreate = functions.auth.userClass().onCreate(async (userClass) => {
  const userRef = admin.firestore().collection("users").doc(userClass.uid);
  await userRef.set({
    email: userClass.email,
    name: userClass.displayName,
    photoUrl: userClass.photoURL,
    totalTime: 0,
    totalChallenges: 0,
  });
});

export const onUserDelete = functions.auth.userClass().onDelete(async (userClass) => {
  const userRef = admin.firestore().collection("users").doc(userClass.uid);
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
  const userClass = await admin.auth().getUser(uid);

  // Check if userClass is already in a group
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
        name: userClass.displayName,
        photoUrl: userClass.photoURL,
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
  const userClass = await admin.auth().getUser(uid);

  // Check if userClass is already in a group
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
      name: userClass.displayName,
      photoUrl: userClass.photoURL,
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
 * Checks if a userClass is already in a group.
 * @param {string} uid The userClass's uid.
 * @return {Promise<boolean>} True if the userClass is in a group, false otherwise.
 */
async function isInGroup(uid: string) {
  const existingGroup = await admin.firestore().collection("groups")
    .orderBy(`members.${uid}`)
    .limit(1)
    .get();

  return !existingGroup.empty;
}
