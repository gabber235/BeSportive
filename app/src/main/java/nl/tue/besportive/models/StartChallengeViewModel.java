package nl.tue.besportive.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ChallengesRepository;

public class StartChallengeViewModel extends ViewModel {
    private final ChallengesRepository challengesRepository = new ChallengesRepository();
    private final MutableLiveData<Boolean> startingChallenge = new MutableLiveData<>();

    private final String challengeId;

    public StartChallengeViewModel(String challengeId) {
        this.challengeId = challengeId;
    }

    public LiveData<Challenge> getChallenge() {
        return challengesRepository.getLiveChallenge(challengeId);
    }

    public LiveData<Boolean> isStartingChallenge() {
        return startingChallenge;
    }


    public void startChallenge(Context context) {
        if (startingChallenge.getValue() != null && startingChallenge.getValue()) return;
        startingChallenge.setValue(true);

        challengesRepository.startChallenge(challengeId, (completedChallengeId) -> {
//            Navigator.navigateToActiveChallengesActivity(context, completedChallengeId, true);
            Log.d("StartChallengeViewModel", "Challenge started successfully: " + completedChallengeId);
        }, () -> {
            startingChallenge.setValue(false);
            Toast.makeText(context, "Failed to start challenge", Toast.LENGTH_SHORT).show();
        });
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
