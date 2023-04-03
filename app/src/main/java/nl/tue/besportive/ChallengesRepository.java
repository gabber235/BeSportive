package nl.tue.besportive;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class ChallengesRepository {
    private final MutableLiveData<List<Challenges>> challengesList = new MutableLiveData<>();
    private final FirebaseFirestore firestore;
    private MutableLiveData<Challenges> specificChallenge = new MutableLiveData<>();

    public ChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Challenges> getSpecificChallenge(String groupId, String challengeId) {
        Log.i("GroupRepository", "Getting CompletedChallenges");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String currUid = user.getUid();
        firestore.collection("groups").document(groupId).collection("challenges").document(challengeId).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("GroupRepository", "Failed to get challenges", error);
                return;
            }
            if (value != null) {
                Log.i("GroupRepository", "Got Completed Challenges: " + value);
                Challenges challenges = new Challenges(value.getLong("difficulty").intValue(), value.getBoolean("distanceBased"), value.getString("name"));
                specificChallenge.setValue(challenges);
            }
        });
        return specificChallenge;
    }
}
