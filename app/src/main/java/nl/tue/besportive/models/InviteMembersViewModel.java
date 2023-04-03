package nl.tue.besportive.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import nl.tue.besportive.data.Group;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.Navigator;

public class InviteMembersViewModel extends ViewModel {
    private final GroupRepository groupRepository;

    public InviteMembersViewModel() {
        groupRepository = new GroupRepository();
    }

    public LiveData<Group> getGroup() {
        return groupRepository.getLiveGroup();
    }

    public void done(Context context) {
        Navigator.navigateToFeedActivity(context);
    }
}
