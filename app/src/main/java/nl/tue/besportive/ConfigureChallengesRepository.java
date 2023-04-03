package nl.tue.besportive;

import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigureChallengesRepository {
    private final MutableLiveData<List<Challenges>> GroupChallengesList = new MutableLiveData<>();
    private final MutableLiveData<List<Challenges>> DefaultChallengesList = new MutableLiveData<>();
    private MutableLiveData<String> groupIdLiveData = new MutableLiveData<>();
    private final FirebaseFirestore firestore;

    public ConfigureChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<Challenges>> getChallenges() {
        Log.i("ConfigureChallengesRepository", "Getting CompletedChallenges");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        System.out.println(uid);
        // Find the group collection where members array contains the current user id
        firestore.collection("groups").whereNotEqualTo("members." + uid, null).limit(1).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("ConfigureChallengesRepository", "Failed to get Group", error);
                return;
            }
            if (value != null) {
                DocumentSnapshot groupDoc = value.getDocuments().get(0);
                String groupId = groupDoc.getId();
                firestore.collection("groups").document(groupId).collection("challenges").addSnapshotListener((challengesQuerySnapshot, challengesError) -> {
                    if (challengesError != null) {
                        Log.d("ConfigureChallengesRepository", "Failed to get Challenges");
                        return;
                    }
                    List<Challenges> challengesList = new ArrayList<>();
                    for (DocumentSnapshot document : challengesQuerySnapshot.getDocuments()) {
                        Challenges challenge = document.toObject(Challenges.class);
                        challenge.setChallengeId(document.getId());
                        challengesList.add(challenge);
                    }
                    GroupChallengesList.setValue(challengesList);
                });
            }
        });
        return GroupChallengesList;
    }
    public MutableLiveData<String> getGroupId(){
        Log.i("ConfigureChallengesRepository", "Getting Group");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        System.out.println(uid);
        // Find the group collection where members array contains the current user id
        firestore.collection("groups").whereNotEqualTo("members." + uid, null).limit(1).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("ConfigureChallengesRepository", "Failed to get Group", error);
                return;
            }
            if (value != null) {
                DocumentSnapshot groupDoc = value.getDocuments().get(0);
                String groupId = groupDoc.getId();
                Log.d("ConfigureChallengesResp1", "GroupId: " + groupId);
                groupIdLiveData.setValue(groupId);
            }
        });
        Log.d("ConfigureChallengesResp2", "GroupId: " + groupIdLiveData.getValue());
        return groupIdLiveData;
    }
    public MutableLiveData<List<Challenges>> getDefaultChallenges() {
        Log.i("ConfigureChallengesRepository", "Getting defaultChallenges");
        firestore.collection("defaultChallenges").addSnapshotListener((challengesQuerySnapshot, challengesError) -> {
            if (challengesError != null) {
                Log.d("ConfigureChallengesRepository", "Failed to get Challenges");
                return;
            }
            List<Challenges> challengesList = new ArrayList<>();
            for (DocumentSnapshot document : challengesQuerySnapshot.getDocuments()) {
                Challenges challenge = document.toObject(Challenges.class);
                challenge.setChallengeId(document.getId());
                challengesList.add(challenge);
            }
            DefaultChallengesList.setValue(challengesList);
            System.out.println("ChallengesList " + challengesList.get(0).getName());
            System.out.println("ChallengesList " + challengesList.get(0).getChallengeId());
        });
        return DefaultChallengesList;
    }

}
