package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.repositories.CompletedChallengesRepository;

public class MemberOverviewViewModel extends ViewModel {
    private final CompletedChallengesRepository completedChallengesRepository;


    public MemberOverviewViewModel() {
        completedChallengesRepository = new CompletedChallengesRepository();
    }

    public LiveData<List<CompletedChallenge>> getCompletedChallenges(String userId) {
        return completedChallengesRepository.getLiveUserCompletedChallenges(userId);
    }
}
