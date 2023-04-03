package nl.tue.besportive;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderboardViewModel extends ViewModel {
    private MutableLiveData<Group> group;
    private MutableLiveData<List<Member>> members;
    private GroupRepository groupRepository;

    private Map<String, Group.Member> membersList;

    private LiveData<List<Member>> items;

    public LeaderboardViewModel() {
        groupRepository = new GroupRepository();
        group = groupRepository.getGroup();
        members = groupRepository.getMemberItems();
    }

    public MutableLiveData<Group> getGroup() {
        return group;
    }
    public MutableLiveData<List<Member>> getMembersFromViewModel() {
        return members;
    }




}
