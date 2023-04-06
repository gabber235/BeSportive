package nl.tue.besportive.models;

import static nl.tue.besportive.adapters.ConfigureChallengesAdapter.GroupChallengesAdapter.GroupChallengesAdapterListener;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;

import nl.tue.besportive.adapters.ConfigureChallengesAdapter.DefaultChallengesAdapter.DefaultChallengesAdapterListener;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ConfigureChallengesRepository;
import nl.tue.besportive.utils.Navigator;

public class ConfigureChallengesViewModel extends ViewModel implements GroupChallengesAdapterListener, DefaultChallengesAdapterListener {
    private final ConfigureChallengesRepository configureChallengesRepository;

    private final boolean inCreateGroupFlow;

    public ConfigureChallengesViewModel(boolean inCreateGroupFlow) {
        this.inCreateGroupFlow = inCreateGroupFlow;
        configureChallengesRepository = new ConfigureChallengesRepository();
    }

    public LiveData<List<Challenge>> getChallenges() {
        return configureChallengesRepository.getChallenges();
    }

    public LiveData<List<Challenge>> getDefaultChallenges() {
        return configureChallengesRepository.getDefaultChallenges();
    }

    public LiveData<String> getGroupId() {
        return configureChallengesRepository.getGroupId();
    }

    public boolean isInCreateGroupFlow() {
        return inCreateGroupFlow;
    }

    public void nextButtonClicked(Context context) {
        Navigator.navigateToInviteMembersActivity(context, false, inCreateGroupFlow);
    }

    public void addButtonClicked(Context context) {
        Navigator.navigateToAddChallengeActivity(context);
    }

    @Override
    public void onAdd(Challenge challenge) {
        configureChallengesRepository.addChallenge(challenge);
    }

    @Override
    public void onRemove(Challenge challenge) {
        configureChallengesRepository.removeChallenge(challenge);
    }

    public static class ConfigureChallengesViewModelFactory implements ViewModelProvider.Factory {
        private final boolean inCreateGroupFlow;

        public ConfigureChallengesViewModelFactory(boolean inCreateGroupFlow) {
            this.inCreateGroupFlow = inCreateGroupFlow;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ConfigureChallengesViewModel.class)) {
                return Objects.requireNonNull(modelClass.cast(new ConfigureChallengesViewModel(inCreateGroupFlow)));
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
