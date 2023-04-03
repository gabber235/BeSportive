package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.data.Group;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.repositories.GroupRepository;

public class LeaderboardViewModel extends ViewModel {
    private final GroupRepository groupRepository;

    public LeaderboardViewModel() {
        groupRepository = new GroupRepository();
    }

    public LiveData<Group> getGroup() {
        return groupRepository.getLiveGroup();
    }

    public LiveData<List<Member>> getMembersFromViewModel() {
        return groupRepository.getLiveMembers();
    }
}
