package nl.tue.besportive.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.utils.FirebaseQueryLiveData;

public class ConfigureChallengesRepository {
    private LiveData<QuerySnapshot> groupChallengesSnapshot;
    private LiveData<List<Challenge>> groupChallenges;
    private LiveData<QuerySnapshot> defaultChallengesSnapshot;
    private LiveData<List<Challenge>> defaultChallenges;
    private final GroupRepository groupRepository;
    private final FirebaseFirestore firestore;

    public ConfigureChallengesRepository() {
        firestore = FirebaseFirestore.getInstance();
        groupRepository = new GroupRepository();
    }

    public LiveData<QuerySnapshot> getGroupChallengesSnapshot() {
        if (groupChallengesSnapshot != null) {
            return groupChallengesSnapshot;
        }

        return groupChallengesSnapshot = Transformations.switchMap(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }

            return new FirebaseQueryLiveData(firestore.collection("groups").document(group.getId()).collection("challenges"));
        });
    }

    public LiveData<List<Challenge>> getChallenges() {
        if (groupChallenges != null) {
            return groupChallenges;
        }

        return groupChallenges = Transformations.map(getGroupChallengesSnapshot(), snapshot -> {
            List<Challenge> challenges = snapshot.toObjects(Challenge.class);

            // The id is not automatically set by Firebase, so we have to do it manually
            for (int i = 0; i < challenges.size(); i++) {
                challenges.get(i).setId(snapshot.getDocuments().get(i).getId());
            }

            return challenges;
        });
    }

    public LiveData<QuerySnapshot> getDefaultChallengesSnapshot() {
        if (defaultChallengesSnapshot != null) {
            return defaultChallengesSnapshot;
        }

        return defaultChallengesSnapshot = new FirebaseQueryLiveData(firestore.collection("defaultChallenges"));
    }

    public LiveData<List<Challenge>> getDefaultChallenges() {
        if (defaultChallenges != null) {
            return defaultChallenges;
        }

        return defaultChallenges = Transformations.map(getDefaultChallengesSnapshot(), snapshot -> {
            List<Challenge> challenges = snapshot.toObjects(Challenge.class);

            // The id is not automatically set by Firebase, so we have to do it manually
            for (int i = 0; i < challenges.size(); i++) {
                challenges.get(i).setId(snapshot.getDocuments().get(i).getId());
            }

            return challenges;
        });
    }

    public LiveData<String> getGroupId() {
        return groupRepository.getLiveGroupId();
    }

    public void addChallenge(Challenge challenge) {
        groupRepository.fetchGroupId((groupId) -> {
            String path = "groups/" + groupId + "/challenges/" + challenge.getId();
            firestore.document(path).set(challenge);
        });
    }

    public void removeChallenge(Challenge challenge) {
        groupRepository.fetchGroupId((groupId) -> {
            String path = "groups/" + groupId + "/challenges/" + challenge.getId();
            firestore.document(path).delete();
        });
    }
}
