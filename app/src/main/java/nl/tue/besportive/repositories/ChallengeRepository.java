package nl.tue.besportive.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import nl.tue.besportive.Challenge;
import nl.tue.besportive.FirebaseDocumentLiveData;

public class ChallengeRepository {
    private LiveData<Challenge> challenge;
    private FirebaseDocumentLiveData challengeSnapshot;

    private FirebaseDocumentLiveData getFirebaseDocumentLiveData(String groupId, String challengeId) {
        if (challengeSnapshot != null) {
            return challengeSnapshot;
        }
        String path = "groups/" + groupId + "/challenges/" + challengeId;
        Log.d("ChallengeRepository", "Path: " + path);
        challengeSnapshot = new FirebaseDocumentLiveData(path);

        return challengeSnapshot;
    }

    public LiveData<Challenge> getLiveChallenge(String groupId, String challengeId) {
        if (challenge != null) {
            return challenge;
        }

        challenge = Transformations.map(getFirebaseDocumentLiveData(groupId, challengeId), input -> {
            if (input == null) {
                return null;
            }
            if (!input.exists()) {
                return null;
            }

            Log.d("ChallengeRepository", "Challenge: " + input.getData());

            Challenge challenge = input.toObject(Challenge.class);
            assert challenge != null;
            challenge.setId(input.getId());
            return challenge;
        });

        return challenge;
    }
}
