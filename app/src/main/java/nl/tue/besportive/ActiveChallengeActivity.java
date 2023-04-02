package nl.tue.besportive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import nl.tue.besportive.databinding.ActivityActiveChallengeBinding;

public class ActiveChallengeActivity extends AppCompatActivity {


    private ActivityActiveChallengeBinding binding;

    TextView timerText;
    Button start_button;
    Button cancel_button;

    Button complete_button;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    private static final String TAG = "ActiveChallengesActivity";
    TextView activeChallengedisplay;
    TextView tv_challengedifficulty;
    private String activeChallengeDocId;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_challenge);

        timerText = (TextView) findViewById(R.id.timerText);
        start_button = (Button) findViewById(R.id.start_button);
        cancel_button = (Button) findViewById(R.id.cancel_button) ;
        complete_button = (Button) findViewById(R.id.complete_button);
        timer = new Timer();

        activeChallengedisplay = (TextView) findViewById(R.id.activeChallengedisplay);
        tv_challengedifficulty = (TextView) findViewById(R.id.tv_challengeDifficulty);
        String name ="Challenge Not Set";
        String difficultytext= "Difficulty not set";
        Bundle extras =  getIntent().getExtras();

        if (extras != null){
            name = extras.getString("name");
            difficultytext = extras.getString("difficulty");

        }
        if(timerStarted == false)
        {
            cancel_button.setVisibility(View.INVISIBLE);
            complete_button.setVisibility(View.INVISIBLE);

        }


        activeChallengedisplay.setText(name);
        tv_challengedifficulty.setText(difficultytext);
    }



    public void startTapped(View view)
    {
        if(timerStarted == false)
        {
            timerStarted = true;
            cancel_button.setVisibility(View.VISIBLE);
            complete_button.setVisibility(View.VISIBLE);
            start_button.setVisibility(View.INVISIBLE);
            startTimer();
        }
        else
        {
            timerStarted = false;
            setButtonUI("START", R.color.white);
            cancel_button.setVisibility(View.INVISIBLE);
            timerTask.cancel();
        }
    }


    private void setButtonUI(String start, int color)
    {
        start_button.setText(start);
        start_button.setTextColor(ContextCompat.getColor(this, color));
    }

    private void startTimer() {
        // Create a new Firestore collection for active challenges
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference activeChallengesRef = db.collection("activeChallenges");

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());

                        // Add the data to Firestore
                        if (time == 1.0) {
                            // Create a new document with the required data
                            Map<String, Object> activeChallenge = new HashMap<>();
                            activeChallenge.put("challengeId", "<challengeId>");
                            activeChallenge.put("userId", "<userId>");
                            activeChallenge.put("status", "<0>");
                            activeChallenge.put("startTime", FieldValue.serverTimestamp());

                            // Add the document to the activeChallengesRef collection
                            activeChallengesRef.add(activeChallenge)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            activeChallengeDocId = documentReference.getId();
                                            Log.d(TAG, "Active challenge document added with ID: " + activeChallengeDocId);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding active challenge document", e);
                                        }
                                    });
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }

    public void cancelChallengeTapped(View view) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Cancel Challenge");
        resetAlert.setMessage("Are you sure you want to cancel the challenge?");

        resetAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                complete_button.setVisibility(View.INVISIBLE);
                start_button.setVisibility(View.VISIBLE);
                cancel_button.setVisibility(View.INVISIBLE);
                // Get a reference to the activeChallenges collection in Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference activeChallengesRef = db.collection("group/{groupId}/completedChallenges");
// define a group {group id TResVKvwgVKs7rLgOcmL}
                // Delete the active challenge document
                activeChallengesRef.document(activeChallengeDocId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // The document was successfully deleted, so cancel the timer and reset the UI
                                if (timerTask != null) {
                                    timerTask.cancel();
                                    setButtonUI("START", R.color.white);
                                    time = 0.0;
                                    timerStarted = false;
                                    timerText.setText(formatTime(0, 0, 0));
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // There was an error deleting the document, so log the error
                                Log.e("ActiveChallengeActivity", "Error deleting active challenge document", e);
                            }
                        });
            }
        });
        resetAlert.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked "No", do nothing
            }
        });

        resetAlert.show();
    }

    public void completeChallengeTapped(View view) {
        // Show the confirmation dialog
        AlertDialog.Builder completeAlert = new AlertDialog.Builder(this);
        completeAlert.setTitle("Complete Challenge");
        completeAlert.setMessage("Are you sure you want to complete the challenge?");

        completeAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Get the current user's ID
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Get the challenge ID from the extras
                Bundle extras = getIntent().getExtras();
                String challengeId = extras.getString("challengeId");

                // Check if challengeId is null or empty
                if (TextUtils.isEmpty(challengeId)) {
                    Toast.makeText(ActiveChallengeActivity.this, "Challenge ID is null or empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get a reference to the activeChallenges collection in Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference activeChallengesRef = db.collection("activeChallenges");

                // Create a query to find the active challenge document with the specified challengeId and userId
                Query query = activeChallengesRef.whereEqualTo("challengeId", challengeId)
                        .whereEqualTo("userId", userId);

                // Execute the query
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Check if the query returned any documents
                            QuerySnapshot snapshot = task.getResult();
                            if (snapshot != null && !snapshot.isEmpty()) {
                                // Get the first document
                                DocumentSnapshot document = snapshot.getDocuments().get(0);

                                // Update the status to 1 and duration to the difference between the start and complete times
                                Timestamp startTime = document.getTimestamp("startTime");
                                Timestamp completeTime = new Timestamp(new Date());
                                long durationInSeconds = (completeTime.getSeconds() - startTime.getSeconds());
                                Map<String, Object> updateData = new HashMap<>();
                                updateData.put("status", 1);
                                updateData.put("duration", durationInSeconds);

                                // Update the document in the activeChallenges collection
                                activeChallengesRef.document(document.getId())
                                        .update(updateData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "Active challenge document updated with status 1 and duration " + durationInSeconds);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e(TAG, "Error updating active challenge document", e);
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No active challenge documents found with challenge ID " + challengeId + " and user ID " + userId);
                            }
                        } else {
                            Log.e(TAG, "Error querying active challenge documents", task.getException());
                        }
                    }
                });

                // Return to the main activity
                Intent intent = new Intent(ActiveChallengeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        completeAlert.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked "No", do nothing
            }
        });

        completeAlert.show();
    }
    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }


    private void leaderboard(View view) {
        startLeaderboardActivity();
    }

    private void challenges(View view) {
        startChallengesActivity();
    }

    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startChallengesActivity() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivity(intent);
        finish();
    }

}