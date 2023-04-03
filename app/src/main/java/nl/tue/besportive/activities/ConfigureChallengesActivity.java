package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.ConfigureChallengesAdapter;
import nl.tue.besportive.adapters.DefaultChallengesAdapter;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.databinding.ActivityConfigureChallengesBinding;
import nl.tue.besportive.models.ConfigureChallengesViewModel;

public class ConfigureChallengesActivity extends AppCompatActivity {
    private ActivityConfigureChallengesBinding binding;

    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigureChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Call recyclerview
        RecyclerView recyclerView = findViewById(R.id.my_challenges_recyclerview);
        RecyclerView recyclerViewDefaultChallenges = findViewById(R.id.default_challenges_recyclerview);
        // List of Challenges
        List<Challenge> items = new ArrayList<>();
        // RecyclerView configuration
        // Call ViewModel
        ConfigureChallengesViewModel viewModel = new ViewModelProvider(this).get(ConfigureChallengesViewModel.class);

        viewModel.getGroupId().observe(this, groupId -> this.groupId = groupId);

        // Group challenges Recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConfigureChallengesAdapter configureChallengesAdapter = new ConfigureChallengesAdapter(getApplicationContext(), items, viewModel, item -> {
            System.out.println("Item" + item.getId());
            removeChallenge(item.getId());
        });
        recyclerView.setAdapter(configureChallengesAdapter);
        // Default challenges Recyclerview
        recyclerViewDefaultChallenges.setLayoutManager(new LinearLayoutManager(this));
        DefaultChallengesAdapter defaultChallengesAdapter = new DefaultChallengesAdapter(getApplicationContext(), items, viewModel, item -> {
            System.out.println("Item" + item.getId());
            addChallenge(item);
        });
        recyclerViewDefaultChallenges.setAdapter(defaultChallengesAdapter);

        // Add fetched challenges to the adapter
        viewModel.getChallenges().observe(this, challenges -> configureChallengesAdapter.setChallenges(viewModel.getChallenges().getValue()));
        viewModel.getDefaultChallenges().observe(this, challenges -> defaultChallengesAdapter.setChallenges(viewModel.getDefaultChallenges().getValue()));
        // Add a new Challenge
        binding.addCustomChallengeButton.setOnClickListener(this::addChallengeActivity);
    }

    private void removeChallenge(String challengeId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "groups/" + groupId + "/challenges/" + challengeId;
        Log.d("TAG", "Path: " + path);
        db.document(path)
                .delete()
                .addOnSuccessListener(aVoid -> Log.i("ConfigureChallengesActivity", "Challenge updated successfully"))
                .addOnFailureListener(e -> Log.e("ConfigureChallengesActivity", "Error updating challenge", e));
    }

    private void addChallenge(Challenge challengeData) {
        String challengeId = challengeData.getId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add the challenge to the Firestore database
        DocumentReference docRef = db.collection("groups").document(groupId).collection("challenges").document(challengeId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // The document already exists
                    Log.d("TAG", "Document already exists");
                    Toast.makeText(ConfigureChallengesActivity.this, "This Challenge already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // The document does not exist, you can proceed
                    Log.d("TAG", "Document does not exist");
                    Map<String, Object> myChallenge = new HashMap<>();
                    myChallenge.put("name", challengeData.getName());
                    myChallenge.put("difficulty", challengeData.getDifficulty());
                    db.collection("groups").document(groupId).collection("challenges").document(challengeId)
                            .set(myChallenge).addOnSuccessListener(aVoid -> {
                                // Document was successfully added
                                Log.d("AddChallengeActivity", "Challenge added with ID: " + challengeId);
                            })
                            .addOnFailureListener(e -> {
                                // There was an error adding the challenge
                                Log.w("CreateChallengeActivity", "Error adding challenge", e);
                                // Show a toast message to the user
                                Toast.makeText(ConfigureChallengesActivity.this, "Error adding challenge", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                Log.w("TAG", "Error getting document", task.getException());
            }
        });
    }

    private void addChallengeActivity(View view) {
        Intent intent = new Intent(this, AddChallengeActivity.class);
        startActivity(intent);
        finish();
    }
}