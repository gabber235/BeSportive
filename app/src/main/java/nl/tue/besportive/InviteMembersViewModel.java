package nl.tue.besportive;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class InviteMembersViewModel extends ViewModel {
    private LiveData<Group> group;
    private GroupRepository groupRepository;

    public InviteMembersViewModel() {
        groupRepository = new GroupRepository();
        group = groupRepository.getLiveGroup();
    }

    public LiveData<Group> getGroup() {
        return group;
    }

    public void done(Context context) {
        Navigator.navigateToFeedActivity(context);
    }
}
