# BeSportive

An Android application to help people be more physically active.

## How to start the firebase emulator

1. Install npm and nodejs: https://nodejs.org/en/download/
2. Open a terminal in the root of the project
3. Install firebase-tools: `npm install -g firebase-tools`
4. Login to firebase: `firebase login`
5. Build the cloud functions: `cd functions && npm i && npm run build && cd ../`

Start the emulator: `firebase emulators:start --import=./firebase_mock`