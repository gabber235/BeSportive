package nl.tue.besportive;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ConfigureChallengesViewModel extends ViewModel {
    private MutableLiveData<List<Challenges>> challengesLiveData;
    private MutableLiveData<List<Challenges>> defaultChallengesLiveData;
    private MutableLiveData<String> groupId;
    private ConfigureChallengesRepository configureChallengesRepository;

    public ConfigureChallengesViewModel() {
        configureChallengesRepository = new ConfigureChallengesRepository();
        challengesLiveData = configureChallengesRepository.getChallenges();
        groupId = configureChallengesRepository.getGroupId();
        defaultChallengesLiveData = configureChallengesRepository.getDefaultChallenges();
    }
    public MutableLiveData<List<Challenges>> getFetchedChallenges(){
        return challengesLiveData;
    }
    public MutableLiveData<List<Challenges>> getFetchedDefaultChallenges(){
        return defaultChallengesLiveData;
    }
    public MutableLiveData<String> getGroupId(){
        return groupId;
    }
}
