package nl.tue.besportive.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.utils.FirebaseQueryLiveData;

public class CompletedChallengesRepository {
    private Map<String, LiveData<QuerySnapshot>> userCompletedChallengesSnapshot;
    private Map<String, LiveData<List<CompletedChallenge>>> userCompletedChallenges;

    private LiveData<QuerySnapshot> completedChallengesSnapshot;
    private LiveData<List<CompletedChallenge>> completedChallenges;


    private final GroupRepository groupRepository;
    private final FirebaseFirestore firestore;

    public CompletedChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
        groupRepository = new GroupRepository();
    }

    private Query getUserCompletedChallengesQuery(String groupId, String uid) {
        return firestore.collection("groups/" + groupId + "/completedChallenges").whereEqualTo("userId", uid);
    }

    private LiveData<QuerySnapshot> getLiveUserCompletedChallengesSnapshot(String userId) {
        if (userCompletedChallengesSnapshot.containsKey(userId)) {
            return userCompletedChallengesSnapshot.get(userId);
        }
        LiveData<QuerySnapshot> completedChallenges = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return new FirebaseQueryLiveData(getUserCompletedChallengesQuery(group.getId(), userId));
        });
        userCompletedChallengesSnapshot.put(userId, completedChallenges);

        return completedChallenges;
    }

    public LiveData<List<CompletedChallenge>> getLiveUserCompletedChallenges(String uid) {
        if (userCompletedChallenges.containsKey(uid)) {
            return userCompletedChallenges.get(uid);
        }
        LiveData<List<CompletedChallenge>> completedChallenges = Transformations.map(getLiveUserCompletedChallengesSnapshot(uid), snapshot -> {
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
        userCompletedChallenges.put(uid, completedChallenges);
        return completedChallenges;
    }

    private Query getCompletedChallengesQuery(String groupId) {
        return firestore.collection("groups/" + groupId + "/completedChallenges").orderBy("completedAt", Query.Direction.DESCENDING);
    }

    private LiveData<QuerySnapshot> getLiveCompletedChallengesSnapshot() {
        if (completedChallengesSnapshot != null) {
            return completedChallengesSnapshot;
        }
        LiveData<QuerySnapshot> completedChallenges = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return new FirebaseQueryLiveData(getCompletedChallengesQuery(group.getId()));
        });
        completedChallengesSnapshot = completedChallenges;

        return completedChallenges;
    }

    public LiveData<List<CompletedChallenge>> getLiveCompletedChallenges() {
        if (completedChallenges != null) {
            return completedChallenges;
        }
        LiveData<List<CompletedChallenge>> completedChallenges = Transformations.map(getLiveCompletedChallengesSnapshot(), snapshot -> {
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
        this.completedChallenges = completedChallenges;
        return completedChallenges;
    }
}

