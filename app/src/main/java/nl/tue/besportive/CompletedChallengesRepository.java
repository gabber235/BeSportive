package nl.tue.besportive;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletedChallengesRepository {
    private final MutableLiveData<List<CompletedChallenges>> completedChallenges = new MutableLiveData<>();
    private final FirebaseFirestore firestore;
    private MutableLiveData<Challenges> specificChallenge = new MutableLiveData<>();
    public CompletedChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface OnChallengeFetchedListener {
        void onChallengeFetched(Challenges challenges);
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
                AtomicInteger count = new AtomicInteger(0);
                for (CompletedChallenges item: completedChallengesList){
                    getChallengeInformation(groupId, item.getChallenge(), new OnChallengeFetchedListener() {
                        @Override
                        public void onChallengeFetched(Challenges challenges) {
                            System.out.println("Challenge name: " + challenges.getName());
                            item.setName(challenges.getName());
                            count.incrementAndGet();
                            if (count.get() == completedChallengesList.size()) {
                                completedChallenges.setValue(completedChallengesList);
                            }
                        }
                    });
                }
            }
        });
        System.out.println(completedChallenges.getValue());
        return completedChallenges;
    }
    public void getChallengeInformation(String groupId, String challengeId, OnChallengeFetchedListener listener){
        firestore.collection("groups").document(groupId).collection("challenges").document(challengeId).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("GroupRepository", "Failed to get challenges", error);
                return;
            }
            if (value != null) {
                Log.i("GroupRepository", "Found Challenge: " + value);
                Challenges challenges = new Challenges(value.getLong("difficulty").intValue(), value.getBoolean("distanceBased"), value.getString("name"));
                listener.onChallengeFetched(challenges);
            }
        });
    }
}

