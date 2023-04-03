package nl.tue.besportive;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

public class CompletedChallengesViewModel extends ViewModel {
    private MutableLiveData<List<CompletedChallenges>> completedChallengesLiveData;
    private CompletedChallengesRepository completedChallengesRepository;

    private MutableLiveData<String> groupIdLiveData = new MutableLiveData<>();
    private MutableLiveData<String> userIdLiveData = new MutableLiveData<>();
    private String groupid;
    private String challengeId;

    public CompletedChallengesViewModel() {
        completedChallengesRepository = new CompletedChallengesRepository();


    }
    public LiveData<List<CompletedChallenges>> getCompletedChallenges(String groupId, String uid) {
        if (completedChallengesLiveData == null) {
            completedChallengesLiveData = completedChallengesRepository.getCompletedChallenges(groupId, uid);

        }
        return completedChallengesLiveData;
    }
}
