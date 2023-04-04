package nl.tue.besportive.repositories;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import nl.tue.besportive.data.ActiveChallenge;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.utils.FirebaseQueryLiveData;

public class ActiveChallengeRepository {
    private LiveData<QuerySnapshot> activeChallengesSnapshot;
    private LiveData<ActiveChallenge> activeChallenge;
    private final FirebaseFirestore firestore;

    private final GroupRepository groupRepository;

    public ActiveChallengeRepository() {
        firestore = FirebaseFirestore.getInstance();
        groupRepository = new GroupRepository();
    }

    private Query getActiveChallengeQuery(String groupId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        return firestore.collection("groups/" + groupId + "/activeChallenges").whereEqualTo("userId", uid).limit(1);
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

    public LiveData<ActiveChallenge> getLiveActiveChallenge() {
        if (activeChallenge != null) {
            return activeChallenge;
        }
        return activeChallenge = Transformations.map(getLiveActiveChallengeSnapshot(), snapshot -> {
            if (snapshot == null || snapshot.isEmpty()) {
                return null;
            }
            ActiveChallenge activeChallenge = snapshot.toObjects(ActiveChallenge.class).get(0);
            activeChallenge.setId(snapshot.getDocuments().get(0).getId());
            return activeChallenge;
        });
    }

    public UploadTask uploadPhoto(Bitmap photo) {
        String groupId = Objects.requireNonNull(groupRepository.getLiveGroup().getValue()).getId();
        String activeChallengeId = Objects.requireNonNull(getLiveActiveChallenge().getValue()).getId();

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference reference = storage.getReference().child(groupId + "/" + activeChallengeId + "/image.jpg");

        Log.d("ActiveChallengeRepo", "uploadPhoto: " + reference.getPath());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        return reference.putBytes(data);
    }

    public void completeChallenge(Challenge challenge) {
        String groupId = Objects.requireNonNull(groupRepository.getLiveGroup().getValue()).getId();
        String activeChallengeId = Objects.requireNonNull(getLiveActiveChallenge().getValue()).getId();

        ActiveChallenge activeChallenge = getLiveActiveChallenge().getValue();

        CompletedChallenge completedChallenge = new CompletedChallenge(challenge, activeChallenge);

        firestore.document("groups/" + groupId + "/completedChallenges/" + activeChallengeId).set(completedChallenge);

        deleteActiveChallenge();
    }

    public void deleteActiveChallenge() {
        String groupId = Objects.requireNonNull(groupRepository.getLiveGroup().getValue()).getId();
        String activeChallengeId = Objects.requireNonNull(getLiveActiveChallenge().getValue()).getId();

        firestore.collection("groups/" + groupId + "/activeChallenges").document(activeChallengeId).delete();
    }
}
