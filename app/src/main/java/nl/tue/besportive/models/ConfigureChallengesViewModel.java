package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ConfigureChallengesRepository;

public class ConfigureChallengesViewModel extends ViewModel {
    private LiveData<List<Challenge>> challenges;
    private LiveData<List<Challenge>> defaultChallenges;
    private final ConfigureChallengesRepository configureChallengesRepository;

    public ConfigureChallengesViewModel() {
        configureChallengesRepository = new ConfigureChallengesRepository();
    }

    public LiveData<List<Challenge>> getChallenges() {
        if (challenges != null) {
            return challenges;
        }

        return challenges = configureChallengesRepository.getChallenges();
    }

    public LiveData<List<Challenge>> getDefaultChallenges() {
        if (defaultChallenges != null) {
            return defaultChallenges;
        }
        return defaultChallenges = configureChallengesRepository.getDefaultChallenges();
    }

    public LiveData<String> getGroupId() {
        return configureChallengesRepository.getGroupId();
    }
}
