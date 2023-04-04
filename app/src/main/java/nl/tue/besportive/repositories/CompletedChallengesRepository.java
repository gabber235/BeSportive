package nl.tue.besportive.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.utils.FirebaseQueryLiveData;

public class CompletedChallengesRepository {
    private Map<String, LiveData<QuerySnapshot>> completedUserChallengesSnapshot;
    private Map<String, LiveData<List<CompletedChallenge>>> completedUserChallenges;

    private LiveData<QuerySnapshot> finishedChallengesSnapshot;
    private LiveData<List<CompletedChallenge>> finishedChallenges;

    private LiveData<QuerySnapshot> activeChallengesSnapshot;
    private LiveData<CompletedChallenge> activeChallenge;


    private final GroupRepository groupRepository;
    private final FirebaseFirestore firestore;

    public CompletedChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
        groupRepository = new GroupRepository();
    }

    private Query getUserCompletedChallengesQuery(String groupId, String uid) {
        return firestore.collection("groups").document(groupId).collection("completedChallenges").whereEqualTo("uid", uid);
    }

    private LiveData<QuerySnapshot> getUserCompletedChallengesSnapshot(String userId) {
        if (completedUserChallengesSnapshot.containsKey(userId)) {
            return completedUserChallengesSnapshot.get(userId);
        }
        LiveData<QuerySnapshot> completedChallenges = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return new FirebaseQueryLiveData(getUserCompletedChallengesQuery(group.getId(), userId));
        });
        completedUserChallengesSnapshot.put(userId, completedChallenges);

        return completedChallenges;
    }

    public LiveData<List<CompletedChallenge>> getUserCompletedChallenges(String uid) {
        if (completedUserChallenges.containsKey(uid)) {
            return completedUserChallenges.get(uid);
        }
        LiveData<List<CompletedChallenge>> completedChallenges = Transformations.map(getUserCompletedChallengesSnapshot(uid), snapshot -> {
            if (snapshot == null || snapshot.isEmpty()) {
                return null;
            }
            List<CompletedChallenge> completedChallengesList = snapshot.toObjects(CompletedChallenge.class);

            // The id is not automatically set by Firebase, so we have to do it manually
            for (int i = 0; i < completedChallengesList.size(); i++) {
                completedChallengesList.get(i).setId(snapshot.getDocuments().get(i).getId());
            }

            return completedChallengesList;
        });
        completedUserChallenges.put(uid, completedChallenges);
        return completedChallenges;
    }

    private Query getFinishedCompletedChallengesQuery(String groupId) {
        return firestore.collection("groups").document(groupId).collection("completedChallenges").whereEqualTo("status", 1);
    }

    private LiveData<QuerySnapshot> getFinishedCompletedChallengesSnapshot() {
        if (finishedChallengesSnapshot != null) {
            return finishedChallengesSnapshot;
        }
        LiveData<QuerySnapshot> completedChallenges = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return new FirebaseQueryLiveData(getFinishedCompletedChallengesQuery(group.getId()));
        });
        finishedChallengesSnapshot = completedChallenges;

        return completedChallenges;
    }

    public LiveData<List<CompletedChallenge>> getFinishedCompletedChallenges() {
        if (finishedChallenges != null) {
            return finishedChallenges;
        }
        LiveData<List<CompletedChallenge>> completedChallenges = Transformations.map(getFinishedCompletedChallengesSnapshot(), snapshot -> {
            if (snapshot == null || snapshot.isEmpty()) {
                return null;
            }
            List<CompletedChallenge> completedChallengesList = snapshot.toObjects(CompletedChallenge.class);

            // The id is not automatically set by Firebase, so we have to do it manually
            for (int i = 0; i < completedChallengesList.size(); i++) {
                completedChallengesList.get(i).setId(snapshot.getDocuments().get(i).getId());
            }

            return completedChallengesList;
        });
        finishedChallenges = completedChallenges;
        return completedChallenges;
    }

    private Query getActiveChallengeQuery(String groupId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        return firestore.collection("groups/" + groupId + "/completedChallenges").whereEqualTo("status", 0).whereEqualTo("userId", uid).limit(1);
    }

    public LiveData<QuerySnapshot> getLiveActiveChallengeSnapshot() {
        if (activeChallengesSnapshot != null) {
            return activeChallengesSnapshot;
        }
        return activeChallengesSnapshot = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return new FirebaseQueryLiveData(getActiveChallengeQuery(group.getId()));
        });
    }

    public LiveData<CompletedChallenge> getLiveActiveChallenge() {
        if (activeChallenge != null) {
            return activeChallenge;
        }
        return activeChallenge = Transformations.map(getLiveActiveChallengeSnapshot(), snapshot -> {
            if (snapshot == null || snapshot.isEmpty()) {
                return null;
            }
            CompletedChallenge completedChallenge = snapshot.toObjects(CompletedChallenge.class).get(0);
            completedChallenge.setId(snapshot.getDocuments().get(0).getId());
            return completedChallenge;
        });
    }
}

