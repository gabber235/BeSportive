package nl.tue.besportive.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.adapters.LeaderboardAdapter;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.Navigator;

public class LeaderboardViewModel extends ViewModel implements LeaderboardAdapter.LeaderboardAdapterListener {
    private final GroupRepository groupRepository;

    public LeaderboardViewModel() {
        groupRepository = new GroupRepository();
    }


    public LiveData<List<Member>> getMembers() {
        return groupRepository.getLiveMembers();
    }

    @Override
    public void onMemberClicked(Context context, Member member) {
        Navigator.navigateToMemberOverviewActivity(context, member.getId());
    }
}
