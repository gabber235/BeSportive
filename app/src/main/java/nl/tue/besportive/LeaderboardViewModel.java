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
    private GroupRepository groupRepository;

    private Map<String, Group.Member> membersList;

    private LiveData<List<Member>> items;

    public LeaderboardViewModel() {
        groupRepository = new GroupRepository();
        group = groupRepository.getGroup();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                items = getMemberItems(group);
            }
        }, 500);

    }

    public MutableLiveData<List<Member>> getMemberItems (MutableLiveData<Group> group ) {
        MutableLiveData<List<Member>> membersLiveData = new MutableLiveData<>();
        System.out.println("InGetMemberItems");
        System.out.println(groupRepository.getGroup().getValue().getClass());
        List<Member> items = new ArrayList<Member>();
        Map<String, Group.Member> membersList = group.getValue().getMembers();
        for (Map.Entry<String, Group.Member> entry : membersList.entrySet()) {
            //System.out.println(entry);
            String name = (String) entry.getValue().getName();
            String photoUrl = (String) entry.getValue().getPhotoUrl();
            items.add(new Member(name, "test_email", R.drawable.a, 10, entry.getKey()));
        }
        membersLiveData.setValue(items);
        return membersLiveData;
    }

    public MutableLiveData<Group> getGroup() {
        return group;
    }




}

