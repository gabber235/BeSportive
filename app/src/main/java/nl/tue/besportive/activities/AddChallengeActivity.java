package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import nl.tue.besportive.R;
import nl.tue.besportive.models.ConfigureChallengesViewModel;

public class AddChallengeActivity extends AppCompatActivity {

    private EditText challengeNameEditText;
    private Spinner difficultyLevelSpinner;
    private String challengeName;
    private String difficultyLevel;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);
        // Get references to the UI elements
        challengeNameEditText = findViewById(R.id.challenge_name_edittext);
        difficultyLevelSpinner = findViewById(R.id.difficulty_level_spinner);

        // Set up the spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyLevelSpinner.setAdapter(adapter);

        // Create a reference to viewmodel
        ConfigureChallengesViewModel viewModel = new ViewModelProvider(this).get(ConfigureChallengesViewModel.class);


        // Set up the button click listener
        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user input
                challengeName = challengeNameEditText.getText().toString();
                difficultyLevel = difficultyLevelSpinner.getSelectedItem().toString();

                // Convert the difficulty string to an integer value
                int difficultyInt;
                Map<String, Integer> difficultyMap = new HashMap<>();
                difficultyMap.put("Easy", 0);
                difficultyMap.put("Medium", 1);
                difficultyMap.put("Hard", 2);
                if (difficultyMap.containsKey(difficultyLevel)) {
                    difficultyInt = difficultyMap.get(difficultyLevel);
                } else {
                    difficultyInt = -1; // Indicates an invalid difficulty
                }

                // Check if the challenge name is empty
                if (TextUtils.isEmpty(challengeName)) {
                    Toast.makeText(AddChallengeActivity.this, "Please enter a challenge name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("CreateChallengeActivity", "Challenge name: " + challengeName);
                Log.d("CreateChallengeActivity", "Challenge name: " + difficultyLevel);
                Log.i("CreateChallengeActivity", "Challenge name: " + difficultyInt);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Set up a Map object with the challenge data
                Map<String, Object> challengeData = new HashMap<>();
                challengeData.put("name", challengeName);
                challengeData.put("difficulty", difficultyInt);

                // Call the getGroupId() method to get the MutableLiveData object
                LiveData<String> groupIdLiveData = viewModel.getGroupId();

                // Create an observer to listen for changes to the groupIdLiveData
                groupIdLiveData.observe(AddChallengeActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String newGroupId) {
                        // Update the groupId variable with the new value
                        groupId = newGroupId;

                        Log.d("AddChallengeActivity", "GroupId: " + groupId);
                    }
                });

                // Add the challenge to the Firestore database
                db.collection("groups").document(groupId).collection("challenges")
                        .add(challengeData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // The challenge was added successfully
                                Log.d("AddChallengeActivity", "Challenge added with ID: " + documentReference.getId());
                                // Finish the activity and return to the previous activity
                                returnToConfigureChallenges();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // There was an error adding the challenge
                                Log.w("CreateChallengeActivity", "Error adding challenge", e);
                                // Show a toast message to the user
                                Toast.makeText(AddChallengeActivity.this, "Error adding challenge", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public void returnToConfigureChallenges() {
        Intent intent = new Intent(this, ConfigureChallengesActivity.class);
        startActivity(intent);
        finish();
    }
}