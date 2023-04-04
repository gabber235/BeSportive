package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.repositories.ChallengesRepository;
import nl.tue.besportive.repositories.CompletedChallengesRepository;
import nl.tue.besportive.utils.DurationLiveData;

public class ActiveChallengeViewModel extends ViewModel {
    private final ChallengesRepository challengesRepository = new ChallengesRepository();
    private final CompletedChallengesRepository completedChallengesRepository = new CompletedChallengesRepository();

    private LiveData<Challenge> challenge;

    private LiveData<String> timer;

    public LiveData<CompletedChallenge> getActiveChallenge() {
        return completedChallengesRepository.getLiveActiveChallenge();
    }

    public LiveData<Challenge> getChallenge() {
        if (challenge != null) {
            return challenge;
        }

        return challenge = Transformations.switchMap(getActiveChallenge(), completedChallenge -> {
            if (completedChallenge == null) {
                return null;
            }
            return challengesRepository.getLiveChallenge(completedChallenge.getChallenge());
        });
    }

    public LiveData<String> getTimer() {
        if (timer != null) {
            return timer;
        }

        return timer = Transformations.switchMap(getActiveChallenge(), completedChallenge -> {
            if (completedChallenge == null) {
                return null;
            }
            return new DurationLiveData(completedChallenge.getStartedAt());
        });
    }
}
