package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ConfigureChallengesRepository;

public class ConfigureChallengesViewModel extends ViewModel {
    private final ConfigureChallengesRepository configureChallengesRepository;

    public ConfigureChallengesViewModel() {
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
}
