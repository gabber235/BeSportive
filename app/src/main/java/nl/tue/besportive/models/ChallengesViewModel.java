package nl.tue.besportive.models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.tue.besportive.adapters.ChallengesAdapter;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ChallengesRepository;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.Navigator;

public class ChallengesViewModel extends ViewModel implements ChallengesAdapter.ChallengesAdapterListener {
    private final ChallengesRepository challengesRepository;
    private final GroupRepository groupRepository = new GroupRepository();

    public ChallengesViewModel() {
        challengesRepository = new ChallengesRepository();
    }

    public LiveData<List<Challenge>> getChallenges() {
        return challengesRepository.getLiveChallenges();
    }

    public LiveData<Boolean> isAdministrator() {
        return groupRepository.isAdministrator();
    }

    @Override
    public void onStart(Context context, Challenge challenge) {
        Log.d("ChallengesViewModel", "onStart: " + challenge.getId());
        Navigator.navigateToStartChallengeActivity(context, challenge.getId());
    }
}
