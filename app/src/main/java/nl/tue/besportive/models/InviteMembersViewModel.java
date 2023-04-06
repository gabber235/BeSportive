package nl.tue.besportive.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import nl.tue.besportive.data.Group;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.Navigator;

public class InviteMembersViewModel extends ViewModel {
    private final GroupRepository groupRepository;

    private final boolean inCreateGroupFlow;

    public InviteMembersViewModel(boolean inCreateGroupFlow) {
        this.inCreateGroupFlow = inCreateGroupFlow;
        groupRepository = new GroupRepository();
    }

    public LiveData<Group> getGroup() {
        return groupRepository.getLiveGroup();
    }

    public boolean isInCreateGroupFlow() {
        return inCreateGroupFlow;
    }

    public void done(Context context) {
        Navigator.navigateToFeedActivity(context, true);
    }

    public static class InviteMembersViewModelFactory implements ViewModelProvider.Factory {
        private final boolean inCreateGroupFlow;

        public InviteMembersViewModelFactory(boolean inCreateGroupFlow) {
            this.inCreateGroupFlow = inCreateGroupFlow;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(InviteMembersViewModel.class)) {
                return Objects.requireNonNull(modelClass.cast(new InviteMembersViewModel(inCreateGroupFlow)));
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
