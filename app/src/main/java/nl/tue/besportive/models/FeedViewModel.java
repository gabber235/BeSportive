package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.adapters.FeedAdapter;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.repositories.CompletedChallengesRepository;

public class FeedViewModel extends ViewModel implements FeedAdapter.FeedAdapterListener {
    private final CompletedChallengesRepository completedChallengesRepository;


    public FeedViewModel() {
        completedChallengesRepository = new CompletedChallengesRepository();
    }

    public LiveData<List<CompletedChallenge>> getCompletedChallenges() {
        return completedChallengesRepository.getLiveCompletedChallenges();
    }

    @Override
    public void onLike(CompletedChallenge completedChallenge) {
        // TODO: implement
    }
}
