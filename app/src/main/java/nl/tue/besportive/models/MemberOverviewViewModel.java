package nl.tue.besportive.models;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.tue.besportive.adapters.FeedAdapter;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.data.FeedItem;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.repositories.CompletedChallengesRepository;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.Navigator;

public class MemberOverviewViewModel extends ViewModel implements FeedAdapter.FeedAdapterListener {
    private final CompletedChallengesRepository completedChallengesRepository;
    private final GroupRepository groupRepository;

    private LiveData<List<FeedItem>> feedItems;

    private final String userId;

    public MemberOverviewViewModel(String userId) {
        completedChallengesRepository = new CompletedChallengesRepository();
        groupRepository = new GroupRepository();
        this.userId = userId;
    }

    public LiveData<List<CompletedChallenge>> getCompletedChallenges() {
        return completedChallengesRepository.getLiveUserCompletedChallenges(userId);
    }

    public LiveData<Member> getMember() {
        return groupRepository.getLiveMember(userId);
    }

    public LiveData<List<FeedItem>> getFeedItems() {
        if (feedItems != null) {
            return feedItems;
        }

        MediatorLiveData<List<FeedItem>> mediator = new MediatorLiveData<>();

        LiveData<List<CompletedChallenge>> completedChallenges = getCompletedChallenges();
        LiveData<Member> members = getMember();

        mediator.addSource(completedChallenges, challenges -> mediator.setValue(mergeFeedItems(challenges, members.getValue())));
        mediator.addSource(members, member -> mediator.setValue(mergeFeedItems(completedChallenges.getValue(), member)));

        return feedItems = mediator;
    }

    private List<FeedItem> mergeFeedItems(List<CompletedChallenge> challenges, Member member) {
        Log.d("MemberOverviewViewModel", "mergeFeedItems: " + challenges + " " + member);
        if (challenges == null || member == null) {
            return new ArrayList<>();
        }
        return challenges.stream()
                .map(challenge -> new FeedItem(challenge, member))
                .collect(Collectors.toList());
    }


    public LiveData<Boolean> viewKickButton() {
        return Transformations.map(groupRepository.getLiveGroup(), group -> {
            if (group == null) {
                return false;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                return false;
            }

            if (group.getAdmin() == null) {
                return false;
            }

            if (!group.getAdmin().equals(user.getUid())) {
                return false;
            }

            return userId != null && !userId.equals(user.getUid());
        });
    }

    @Override
    public void onLove(CompletedChallenge completedChallenge) {
        completedChallengesRepository.love(completedChallenge.getId());
    }

    @Override
    public void openMember(Context context, Member member) {
        // Empty as we are already in the member overview
    }

    public void kickMember(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Kick member")
                .setMessage("Are you sure you want to kick this member?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    groupRepository.kickMember(userId);
                    Navigator.finishActivity(context);
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static class MemberOverviewViewModelFactory implements ViewModelProvider.Factory {
        private final String userId;

        public MemberOverviewViewModelFactory(String userId) {
            this.userId = userId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MemberOverviewViewModel.class)) {
                return modelClass.cast(new MemberOverviewViewModel(userId));
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
