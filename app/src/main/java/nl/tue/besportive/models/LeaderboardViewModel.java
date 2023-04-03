package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.data.Group;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.repositories.GroupRepository;

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
