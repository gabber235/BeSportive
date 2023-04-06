package nl.tue.besportive.models;

import android.content.Context;

import androidx.databinding.InverseMethod;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.data.Difficulty;
import nl.tue.besportive.repositories.ConfigureChallengesRepository;
import nl.tue.besportive.utils.Navigator;

public class AddChallengeViewModel extends ViewModel {
    private final ConfigureChallengesRepository configureChallengesRepository;

    private final MutableLiveData<String> challengeName = new MutableLiveData<>();
    private final MutableLiveData<Difficulty> difficulty = new MutableLiveData<>(Difficulty.EASY);

    public AddChallengeViewModel() {
        configureChallengesRepository = new ConfigureChallengesRepository();
    }

    public MutableLiveData<String> getChallengeName() {
        return challengeName;
    }

    public MutableLiveData<Difficulty> getDifficulty() {
        return difficulty;
    }

    public void addChallenge(Context context) {
        Challenge challenge = new Challenge(challengeName.getValue(), Objects.requireNonNull(difficulty.getValue()));
        configureChallengesRepository.addChallenge(challenge);
        Navigator.finishActivity(context);
    }


    @InverseMethod("getDifficultyFromIndex")
    public static int getDifficultyIndex(Difficulty difficulty) {
        return difficulty.ordinal();
    }

    public static Difficulty getDifficultyFromIndex(int index) {
        return Difficulty.values()[index];
    }
}
