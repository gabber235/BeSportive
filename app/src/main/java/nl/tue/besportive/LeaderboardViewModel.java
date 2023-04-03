package nl.tue.besportive;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.Group.Member;

public class LeaderboardViewModel extends ViewModel {
    private LiveData<Group> group;
    private LiveData<List<Member>> members;
    private GroupRepository groupRepository;


    public LeaderboardViewModel() {
        groupRepository = new GroupRepository();
    }

    public LiveData<Group> getGroup() {
        if (group != null) {
            return group;
        }
        return group = groupRepository.getLiveGroup();
    }

    public LiveData<List<Member>> getMembersFromViewModel() {
        if (members != null) {
            return members;
        }
        return members = groupRepository.getLiveMembers();
    }


}
