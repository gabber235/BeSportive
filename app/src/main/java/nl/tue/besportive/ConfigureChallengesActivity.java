package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tue.besportive.databinding.ActivityConfigureChallengesBinding;
import okhttp3.Challenge;

public class ConfigureChallengesActivity extends AppCompatActivity {
    private ActivityConfigureChallengesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigureChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Call recyclerview
        RecyclerView recyclerView = findViewById(R.id.my_challenges_recyclerview);
        RecyclerView recyclerViewDefaultChallenges = findViewById(R.id.default_challenges_recyclerview);
        // List of Challenges
        List<Challenges> items = new ArrayList<Challenges>();
        // RecyclerView configuration
        // Call ViewModel
        ConfigureChallengesViewModel viewModel = new ViewModelProvider(this).get(ConfigureChallengesViewModel.class);
        // Group challenges Recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConfigureChallengesAdapter configureChallengesAdapter = new ConfigureChallengesAdapter(getApplicationContext(),items, viewModel, new ConfigureChallengesAdapter.OnItemClickListener() {
            @Override public void onItemClick(Challenges item) {
                System.out.println("Item" + item.getChallengeId());
                RemoveChallenge(item.getChallengeId(), viewModel.getGroupId().getValue());
            }
        });
        recyclerView.setAdapter(configureChallengesAdapter);
        // Default challenges Recyclerview
        recyclerViewDefaultChallenges.setLayoutManager(new LinearLayoutManager(this));
        DefaultChallengesAdapter defaultChallengesAdapter = new DefaultChallengesAdapter(getApplicationContext(),items, viewModel, new DefaultChallengesAdapter.OnItemClickListener() {
            @Override public void onItemClick(Challenges item) {
                System.out.println("Item" + item.getChallengeId());
                addChallenge(item, viewModel.getGroupId().getValue());
            }
        });
        recyclerViewDefaultChallenges.setAdapter(defaultChallengesAdapter);

        // Add fetched challenges to the adapter
        viewModel.getFetchedChallenges().observe(this, Challenges -> {
            configureChallengesAdapter.setChallenges(viewModel.getFetchedChallenges().getValue());
        });
        viewModel.getFetchedDefaultChallenges().observe(this, Challenges -> {
            defaultChallengesAdapter.setChallenges(viewModel.getFetchedDefaultChallenges().getValue());
        });
        // Add a new Challenge
        binding.addCustomChallengeButton.setOnClickListener(this::addChallengeActivity);
    }
    private void RemoveChallenge(String challengeId, String groupId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("groups").document(groupId).collection("challenges").document(challengeId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("ConfigureChallengesActivity", "Challenge updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ConfigureChallengesActivity", "Error updating challenge", e);
                    }
                });
    }
    private void addChallenge(Challenges challengeData, String groupId){
        String challengeId = challengeData.getChallengeId();
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
                            .set(myChallenge).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document was successfully added
                                    Log.d("AddChallengeActivity", "Challenge added with ID: " + challengeId);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // There was an error adding the challenge
                                    Log.w("CreateChallengeActivity", "Error adding challenge", e);
                                    // Show a toast message to the user
                                    Toast.makeText(ConfigureChallengesActivity.this, "Error adding challenge", Toast.LENGTH_SHORT).show();
                                }
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
    private void inviteMembers(View view) {
        startInviteMembersActivity();
    }

    private void feed(View view) {
        startFeedActivity();
    }
    private void startInviteMembersActivity() {
        Intent intent = new Intent(this, InviteMembersActivity.class);
        startActivity(intent);
        finish();
    }

    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}