package nl.tue.besportive.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.tue.besportive.adapters.FeedAdapter;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.data.FeedItem;
import nl.tue.besportive.data.Group;
import nl.tue.besportive.repositories.CompletedChallengesRepository;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.Navigator;

public class FeedViewModel extends ViewModel implements FeedAdapter.FeedAdapterListener {
    private final CompletedChallengesRepository completedChallengesRepository;

    private final GroupRepository groupRepository;

    private LiveData<List<FeedItem>> feedItems;

    public FeedViewModel() {
        completedChallengesRepository = new CompletedChallengesRepository();
        groupRepository = new GroupRepository();
    }

    public LiveData<List<CompletedChallenge>> getCompletedChallenges() {
        return completedChallengesRepository.getLiveCompletedChallenges();
    }

    public LiveData<Boolean> isAdministrator() {
        return groupRepository.isAdministrator();
    }

    public LiveData<List<FeedItem>> getFeedItems() {
        if (feedItems != null) {
            return feedItems;
        }

        MediatorLiveData<List<FeedItem>> mediator = new MediatorLiveData<>();

        LiveData<List<CompletedChallenge>> completedChallenges = completedChallengesRepository.getLiveCompletedChallenges();
        LiveData<List<Group.Member>> members = groupRepository.getLiveMembers();

        mediator.addSource(completedChallenges, challenges -> mediator.setValue(mediateFeedItems(challenges, members.getValue())));

        mediator.addSource(members, members1 -> mediator.setValue(mediateFeedItems(completedChallenges.getValue(), members1)));

        return feedItems = mediator;
    }

    private List<FeedItem> mediateFeedItems(List<CompletedChallenge> challenges, List<Group.Member> members) {
        if (challenges == null || members == null) {
            return new ArrayList<>();
        }
        return challenges.stream()
                .map(challenge -> {
                    Group.Member member = members.stream()
                            .filter(m -> m.getId().equals(challenge.getUserId()))
                            .findFirst()
                            .orElse(null);
                    return new FeedItem(challenge, member);
                })
                .collect(Collectors.toList());
    }


    @Override
    public void onLove(CompletedChallenge completedChallenge) {
        completedChallengesRepository.love(completedChallenge.getId());
    }

    @Override
    public void openMember(Context context, Group.Member member) {
        Navigator.navigateToMemberOverviewActivity(context, member.getId());
    }
}
