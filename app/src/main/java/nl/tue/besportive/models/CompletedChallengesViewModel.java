package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.repositories.CompletedChallengesRepository;

public class CompletedChallengesViewModel extends ViewModel {
    private LiveData<List<CompletedChallenge>> completedChallengesLiveData;
    private final CompletedChallengesRepository completedChallengesRepository;


    public CompletedChallengesViewModel() {
        completedChallengesRepository = new CompletedChallengesRepository();
    }

    public LiveData<List<CompletedChallenge>> getCompletedChallenges(String userId) {
        if (completedChallengesLiveData != null) {
            return completedChallengesLiveData;
        }
        return completedChallengesLiveData = completedChallengesRepository.getCompletedChallenges(userId);
    }
}
