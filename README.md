# BeSportive

BeSportive is an Android application to help people be more physically active and to increase social cohesion whilst doing so.

-------Important Notice---------
We have decided not to deploy the app to production, and as a result, the Firebase client is not live either.
To run the app, you'll need to use the Android Studio emulator and have the Firebase emulator running in the background.
If you don't follow these steps, the app will not function properly,
as it won't be connected to the Firebase client and will be unable to store or retrieve data from the database.

In this README file an explanation will be given for how to get access to the app: 
- The app can be started by using the firebase emulator in the Terminal of Android Studio. 
By opening a Command Prompt in the Terminal and filling in the commands below in the "How to start the firebase emulator" section.
- Sometimes an error is shown in which it is indicated that "the port is already taken". 
In this case the user can follow the trouble shooting section indicated underneath the "How to start the firebase emulator" section.
- When the emulator is started up a test email and password can be used to make an account and get access to the app. 
This data is indicated below.
- Another error which can occur is when the cloud functions are not working. 
The steps which can be taken when this error happens are shown in the last comment below. 


## How to start the firebase emulator
Requirements: Node.js v16
1. Install npm and nodejs: https://nodejs.org/en/download/
2. Open a terminal in the root of the project
3. Install firebase-tools: `npm install -g firebase-tools`
4. Login to firebase: `firebase login`
5. Build the cloud functions: `cd functions && npm i && npm run build && cd ../`
6. Start the emulator: `firebase emulators:start --import=./firebase_mock`

### Troubleshooting when getting the error "port already taken"

Method 1:
1. Open up the cmd.exe in administrator mode
2. Run the command: `netstat -ano | findstr :<PORT>` (Replace "<PORT>" with the port number that is taken (common 8080), but keep the colon)
3. Find the PID and copy this PID
4. Run the command: `taskkill /PID <PID> /F` (Replace "<PID>" with the PID number copied in step 3)

Method 2:
Requirement: npm@5.2.0 or higher
1. npx kill-port <PORT> (Replace "<PORT>" with the port number that is taken (common 8080))

#### When the emulator is started the user will be able to log in with the following email and password: 
- email: john.do@gmail.com
- password: Test1234
Note: This user will be an admin that is currently in a group with other users.

##### Troubleshooting when the cloud functions are not working
1. Open the terminal in the root of the project
2. Run the command: `npm ci`


USER SCENARIO:
John is sitting alone at home watching tv with some pizza as he has done frequently for the last 3 months. 
He feels quite lonely and notices that his energy levels are low. 
Luckily John gets a message from Danny, a friend whom he has not spoken quite some time. Danny excitedly tells John about an awesome app 
called BeSportive. This app Danny says has changed his life for the better. He feels more energetic and more in touch with other peers. 
Just what John needs he thinks. Danny says that John should start using the app. To Johns surprise he had downloaded the app already long before 
the Covid pandemic. Once in the app he presses on the button which says: "Go to login page!". 
On the sign in page John presses on the button to sign in with email. 
He fills in his email: john.do@gmail.com and clicks on "Next". 
Because he already made an account before he fills in his password "Test1234" and clicks on "Sign in". 
He sees two options "Join group" and "Create group". 
John feels like he should start taking more initiative to be social and presses on "Create group". 
Here he fills in the name Awesome group and presses on "Create group". 
Thereafter, he presses on "Done". Now John gets redirected to the Configure challenges page. Here he sees a few default challenges for inspiration. 
He decides to add the challenge 100 Pushups by pressing on the green plus next to it. 
He now has one challenges, but he wants to start off with two. 
So he presses on "Add custom challenge" and types run 10 km in the textbox and changes the difficulty to Medium with the slider. 
Now he presses "Done" and the challenge is added. He is content with his final challenges list and goes to the next page. 
Now on the Invite members page he sees a generated code. 
He sends the code to a few friends right away and presses on "Done" after which he ends up on the Feed page. 
John feels content with himself as he has taken action to turn his life around. 

