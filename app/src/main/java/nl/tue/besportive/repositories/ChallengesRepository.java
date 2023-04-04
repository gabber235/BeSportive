package nl.tue.besportive.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.utils.FirebaseDocumentLiveData;
import nl.tue.besportive.utils.FirebaseQueryLiveData;

public class ChallengesRepository {
    private final Map<String, LiveData<Challenge>> challenge = new HashMap<>();
    private final Map<String, LiveData<DocumentSnapshot>> challengeSnapshot = new HashMap<>();
    private final GroupRepository groupRepository = new GroupRepository();
    private LiveData<List<Challenge>> challenges;
    private LiveData<QuerySnapshot> challengesSnapshot;

    private LiveData<DocumentSnapshot> getFirebaseDocumentLiveData(String challengeId) {
        if (challengeSnapshot.containsKey(challengeId)) {
            return challengeSnapshot.get(challengeId);
        }

        LiveData<DocumentSnapshot> challengeSnapshot = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }

            String path = "groups/" + group.getId() + "/challenges/" + challengeId;
            return new FirebaseDocumentLiveData(path);
        });

        this.challengeSnapshot.put(challengeId, challengeSnapshot);
        return challengeSnapshot;
    }

    public LiveData<Challenge> getLiveChallenge(String challengeId) {
        if (challenge.containsKey(challengeId)) {
            return challenge.get(challengeId);
        }

        LiveData<Challenge> challengeLiveData = Transformations.map(getFirebaseDocumentLiveData(challengeId), input -> {
            if (input == null) {
                return null;
            }
            if (!input.exists()) {
                return null;
            }

            Challenge challenge = input.toObject(Challenge.class);
            if (challenge == null) {
                Log.e("ChallengesRepository", "Challenge is null");
                return null;
            }

            challenge.setId(input.getId());
            return challenge;
        });

        challenge.put(challengeId, challengeLiveData);
        return challengeLiveData;
    }

    public LiveData<QuerySnapshot> getLiveChallengesSnapshot() {
        if (challengesSnapshot != null) {
            return challengesSnapshot;
        }

        challengesSnapshot = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }

            Query query = FirebaseFirestore.getInstance()
                    .collection("groups/" + group.getId() + "/challenges")
                    .orderBy("difficulty", Query.Direction.ASCENDING);

            return new FirebaseQueryLiveData(query);
        });

        return challengesSnapshot;
    }

    public LiveData<List<Challenge>> getLiveChallenges() {
        if (challenges != null) {
            return challenges;
        }

        challenges = Transformations.map(getLiveChallengesSnapshot(), input -> {
            if (input == null) {
                return null;
            }

            List<Challenge> challenges = input.toObjects(Challenge.class);

            // The id is not automatically set by Firebase, so we have to do it manually
            for (int i = 0; i < challenges.size(); i++) {
                challenges.get(i).setId(input.getDocuments().get(i).getId());
            }

            return challenges;
        });

        return challenges;
    }

    public LiveData<String> getGroupId() {
        return groupRepository.getLiveGroupId();
    }

    public void startChallenge(String challengeId, OnChallengeStartedListener listener, OnChallengeFailedToStartListener failedListener) {
        String groupId = Objects.requireNonNull(groupRepository.getLiveGroup().getValue()).getId();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        HashMap<String, Object> data = new HashMap<>();
        data.put("challenge", challengeId);
        data.put("userId", user.getUid());
        data.put("startedAt", new Date());
        data.put("status", 0);
        
        db.collection("groups/" + groupId + "/completedChallenges")
                .add(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onChallengeStarted(task.getResult().getId());
                    } else {
                        failedListener.onChallengeFailedToStart();
                        Log.e("ChallengesRepository", "Failed to start challenge", task.getException());
                    }
                });
    }

    public interface OnChallengeStartedListener {
        void onChallengeStarted(String completedChallengeId);
    }

    public interface OnChallengeFailedToStartListener {
        void onChallengeFailedToStart();
    }
}
