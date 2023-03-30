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
import androidx.constraintlayout.motion.widget.Debug;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import nl.tue.besportive.databinding.ActivityActiveChallengeBinding;

public class ActiveChallengeActivity extends AppCompatActivity {


    private ActivityActiveChallengeBinding binding;

    TextView timerText;
    Button start_button;
    Button cancel_button;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    private static final String TAG = "ActiveChallengesActivity";
    TextView activeChallengedisplay;

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

        timer = new Timer();

        activeChallengedisplay = (TextView) findViewById(R.id.activeChallengedisplay);

        String name ="Challenge Not Set";
        Bundle extras =  getIntent().getExtras();

        if (extras != null){
            name = extras.getString("name");

        }
        if(timerStarted == false)
        {
            cancel_button.setVisibility(View.INVISIBLE);

        }


        activeChallengedisplay.setText(name);
    }



    public void startStopTapped(View view)
    {
        if(timerStarted == false)
        {
            timerStarted = true;
            setButtonUI("COMPLETE", R.color.white);
            cancel_button.setVisibility(View.VISIBLE);

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
                // Get a reference to the activeChallenges collection in Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference activeChallengesRef = db.collection("activeChallenges");

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