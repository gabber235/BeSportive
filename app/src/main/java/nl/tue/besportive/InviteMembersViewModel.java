package nl.tue.besportive;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InviteMembersViewModel extends ViewModel {
    private MutableLiveData<Group> group;
    private GroupRepository groupRepository;

    public InviteMembersViewModel() {
        groupRepository = new GroupRepository();
        group = groupRepository.getGroup();
    }

    public MutableLiveData<Group> getGroup() {
        return group;
    }

    public void done(Context context) {
        Navigator.navigateToFeedActivity(context);
    }
}
