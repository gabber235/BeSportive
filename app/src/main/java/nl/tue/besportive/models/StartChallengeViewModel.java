package nl.tue.besportive.models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ChallengesRepository;

public class StartChallengeViewModel extends ViewModel {
    private final ChallengesRepository challengesRepository;

    private final String challengeId;

    public StartChallengeViewModel(String challengeId) {
        challengesRepository = new ChallengesRepository();
        this.challengeId = challengeId;
    }

    public LiveData<Challenge> getChallenge() {
        return challengesRepository.getLiveChallenge(challengeId);
    }


    public void startChallenge(Context context) {
        // TODO Create the completedChallenge in the database and navigate to the active challenge activity
        Log.d("StartChallengeViewModel", "Start challenge");
    }

    public static class StartChallengeViewModelFactory implements ViewModelProvider.Factory {
        private final String challengeId;

        public StartChallengeViewModelFactory(String challengeId) {
            this.challengeId = challengeId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(StartChallengeViewModel.class)) {
                return (T) new StartChallengeViewModel(challengeId);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
