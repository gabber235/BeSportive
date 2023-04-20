package nl.tue.besportive.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.UploadTask;

import nl.tue.besportive.data.ActiveChallenge;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.repositories.ActiveChallengeRepository;
import nl.tue.besportive.repositories.ChallengesRepository;
import nl.tue.besportive.utils.DurationLiveData;
import nl.tue.besportive.utils.Navigator;

public class ActiveChallengeViewModel extends ViewModel {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private final ChallengesRepository challengesRepository = new ChallengesRepository();
    private final ActiveChallengeRepository activeChallengeRepository = new ActiveChallengeRepository();


    private final MutableLiveData<Integer> uploadProgress = new MutableLiveData<>();
    private LiveData<ViewState> state;
    private LiveData<Challenge> challenge;

    private LiveData<String> timer;


    public LiveData<ActiveChallenge> getActiveChallenge() {
        return activeChallengeRepository.getLiveActiveChallenge();
    }

    public LiveData<Challenge> getChallenge() {
        if (challenge != null) {
            return challenge;
        }

        return challenge = Transformations.switchMap(getActiveChallenge(), completedChallenge -> {
            if (completedChallenge == null) {
                return null;
            }
            return challengesRepository.getLiveChallenge(completedChallenge.getChallengeId());
        });
    }

    public LiveData<String> getTimer() {
        if (timer != null) {
            return timer;
        }

        return timer = Transformations.switchMap(getActiveChallenge(), completedChallenge -> {
            if (completedChallenge == null) {
                return null;
            }
            return new DurationLiveData(completedChallenge.getStartedAt());
        });
    }

    public void completeChallenge(Context context) {
        Challenge challenge = getChallenge().getValue();
        activeChallengeRepository.completeChallenge(challenge);
        Toast.makeText(context, "Challenge completed", Toast.LENGTH_SHORT).show();
        Navigator.navigateToLeaderboardActivity(context, true);
    }

    public void cancelChallenge(Context context) {
        activeChallengeRepository.deleteActiveChallenge();
        Toast.makeText(context, "Challenge cancelled", Toast.LENGTH_SHORT).show();
        Navigator.finishActivity(context);
    }

    public void takePhoto(Context context) {
        Log.i("ActiveChallengeViewModel", "Taking photo");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (!(context instanceof Activity)) {
            Log.d("ActiveChallengeViewModel", "Context is not an activity");
            return;
        }
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void onPhotoTaken(Context context, Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");

        UploadTask task = activeChallengeRepository.uploadPhoto(photo);


        task.addOnProgressListener(taskSnapshot -> {
            double progressValue = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            uploadProgress.setValue((int) progressValue);
        });

        task.addOnCompleteListener(taskSnapshot -> {
            uploadProgress.setValue(-1);

            if (!taskSnapshot.isSuccessful()) {
                Log.e("ActiveChallengeViewModel", "Failed to upload photo", taskSnapshot.getException());
                Toast.makeText(context, "Failed to upload photo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ViewState> getState() {
        if (state != null) {
            return state;
        }

        MediatorLiveData<ViewState> stateMerger = new MediatorLiveData<>();

        stateMerger.addSource(getActiveChallenge(), activeChallenge -> {
            if (activeChallenge == null) {
                stateMerger.setValue(new ViewState.Loading());
                return;
            }

            if (activeChallenge.getPhotoUrl() == null) {
                stateMerger.setValue(new ViewState.NoPhoto());
                return;
            }

            if (activeChallenge.getPhotoUrl().isEmpty()) {
                stateMerger.setValue(new ViewState.NoPhoto());
                return;
            }

            stateMerger.setValue(new ViewState.PhotoUploaded());
        });

        stateMerger.addSource(uploadProgress, progress -> {
            if (progress == null) {
                return;
            }

            if (progress == -1) {
                stateMerger.setValue(new ViewState.NoPhoto());
                return;
            }

            stateMerger.setValue(new ViewState.UploadingPhoto(progress));
        });

        stateMerger.setValue(new ViewState.Loading());

        return this.state = stateMerger;
    }

    public interface ViewState {
        default int getProgress() {
            return -1;
        }

        default boolean showTakePictureButton() {
            return false;
        }

        default boolean showUploadProgress() {
            return false;
        }

        default boolean showCompleteButton() {
            return false;
        }


        class Loading implements ViewState {
            @Override
            public boolean showUploadProgress() {
                return true;
            }
        }

        class NoPhoto implements ViewState {
            @Override
            public boolean showTakePictureButton() {
                return true;
            }
        }

        class UploadingPhoto implements ViewState {
            private final int progress;

            public UploadingPhoto(int progress) {
                this.progress = progress;
            }

            @Override
            public boolean showUploadProgress() {
                return true;
            }

            @Override
            public int getProgress() {
                return progress;
            }
        }

        class PhotoUploaded implements ViewState {
            @Override
            public boolean showCompleteButton() {
                return true;
            }
        }
    }
}
