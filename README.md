# BeSportive

BeSportive is an Android application to help people be more physically active and to increase social cohesion whilst doing so. 

In this README file an explanation will be given for how to get access to the app: 
- The app can be started by using the firebase emulator in the Terminal of Android Studio. 
By opening a Command Prompt in the Terminal and filling in the commands below in the "How to start the firebase emulator" section.
- Sometimes an error is shown in which it is indicated that "the port is already taken". 
In this case the user can follow the trouble shooting section indicated underneath the "How to start the firebase emulator" section.

## How to start the firebase emulator

1. Install npm and nodejs: https://nodejs.org/en/download/
2. Open a terminal in the root of the project
3. Install firebase-tools: `npm install -g firebase-tools`
4. Login to firebase: `firebase login`
5. Build the cloud functions: `cd functions && npm i && npm run build && cd ../`
6. Start the emulator: `firebase emulators:start --import=./firebase_mock`

### Troubleshooting when getting the error "port already taken"

1. Open up the cmd.exe
2. Run the command: `netstat -ano | findstr :<PORT>` (Replace "<PORT>" with the port number 8080, but keep the colon)
3. Find the PID and copy this PID
4. Run the command: `taskkill / PID <PID> /F` (Replace "<PID>" with the PID number copied in step 3)
