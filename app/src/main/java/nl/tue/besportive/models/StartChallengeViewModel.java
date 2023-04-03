package nl.tue.besportive.models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.Challenge;
import nl.tue.besportive.repositories.ChallengeRepository;

public class StartChallengeViewModel extends ViewModel {
    private final ChallengeRepository challengeRepository;

    private LiveData<Challenge> challenge;

    private String groupId;
    private String challengeId;

    public StartChallengeViewModel(String groupId, String challengeId) {
        challengeRepository = new ChallengeRepository();
        this.groupId = groupId;
        this.challengeId = challengeId;
    }

    public LiveData<Challenge> getChallenge() {
        if (challenge != null) {
            return challenge;
        }
        return challenge = challengeRepository.getLiveChallenge(groupId, challengeId);
    }


    public void startChallenge(Context context) {
        // TODO Create the completedChallenge in the database and navigate to the active challenge activity
        Log.d("StartChallengeViewModel", "Start challenge");
    }

    public static class StartChallengeViewModelFactory implements ViewModelProvider.Factory {
        private final String groupId;
        private final String challengeId;

        public StartChallengeViewModelFactory(String groupId, String challengeId) {
            this.groupId = groupId;
            this.challengeId = challengeId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(StartChallengeViewModel.class)) {
                return (T) new StartChallengeViewModel(groupId, challengeId);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
