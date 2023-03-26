package nl.tue.besportive;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CompletedChallengesRepository {
    private final MutableLiveData<List<CompletedChallenges>> completedChallenges = new MutableLiveData<>();
    private final FirebaseFirestore firestore;
    private MutableLiveData<Challenges> specificChallenge = new MutableLiveData<>();
    public CompletedChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<CompletedChallenges>> getCompletedChallenges(String groupId, String uid) {
        Log.i("GroupRepository", "Getting CompletedChallenges");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String currUid = user.getUid();
        firestore.collection("groups").document(groupId).collection("completedChallenges").whereEqualTo("userId", uid).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("GroupRepository", "Failed to get Completed Challenges", error);
                return;
            }
            if (value != null) {
                Log.i("GroupRepository", "Got Completed Challenges: " + value);
                System.out.println(value.getDocuments());
                System.out.println(value.toObjects(CompletedChallenges.class));
                List<CompletedChallenges> completedChallengesList = value.toObjects(CompletedChallenges.class);
                for (CompletedChallenges item: completedChallengesList){
                    MutableLiveData<Challenges> SpecificChallengeObject = new MutableLiveData<Challenges>();
                    specificChallenge = getChallengeInformation(groupId, item.getChallenge());
                    System.out.println(specificChallenge);
                    System.out.println("Inside handler");
                    System.out.println(specificChallenge.getValue());

                    //item.setName(specificChallenge.getValue().getName());

                }
                completedChallenges.setValue(completedChallengesList);
            }
        });
        System.out.println(completedChallenges.getValue());
        return completedChallenges;
    }
    public MutableLiveData<Challenges> getChallengeInformation(String groupId, String challengeId){
        firestore.collection("groups").document(groupId).collection("challenges").document(challengeId).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("GroupRepository", "Failed to get challenges", error);
                return;
            }
            if (value != null) {
                Log.i("GroupRepository", "Found Challenge: " + value);
                Challenges challenges = new Challenges(value.getLong("difficulty").intValue(), value.getBoolean("distanceBased"), value.getString("name"));
                System.out.println("Inside of getChallengeInformation");
                System.out.println(challenges.getName());
                specificChallenge.setValue(challenges);
            }
        });
        return specificChallenge;
    }
}

