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
    private Map<String, LiveData<QuerySnapshot>> completedUserChallengesSnapshot;
    private Map<String, LiveData<List<CompletedChallenge>>> completedUserChallenges;

    private final GroupRepository groupRepository;
    private final FirebaseFirestore firestore;

    public CompletedChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
        groupRepository = new GroupRepository();
    }

    private Query getCompletedChallengesQuery(String groupId, String uid) {
        return firestore.collection("groups").document(groupId).collection("completedChallenges").whereEqualTo("uid", uid);
    }

    private LiveData<QuerySnapshot> getCompletedChallengesSnapshot(String userId) {
        if (completedUserChallengesSnapshot.containsKey(userId)) {
            return completedUserChallengesSnapshot.get(userId);
        }
        LiveData<QuerySnapshot> completedChallenges = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return new FirebaseQueryLiveData(getCompletedChallengesQuery(group.getId(), userId));
        });
        completedUserChallengesSnapshot.put(userId, completedChallenges);

        return completedChallenges;
    }

    public LiveData<List<CompletedChallenge>> getCompletedChallenges(String uid) {
        if (completedUserChallenges.containsKey(uid)) {
            return completedUserChallenges.get(uid);
        }
        LiveData<List<CompletedChallenge>> completedChallenges = Transformations.map(getCompletedChallengesSnapshot(uid), snapshot -> {
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
}

