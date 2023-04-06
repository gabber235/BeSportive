package nl.tue.besportive.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import nl.tue.besportive.R;
import nl.tue.besportive.data.SportiveUser;
import nl.tue.besportive.repositories.UserRepository;
import nl.tue.besportive.utils.Navigator;

public class ProfileViewModel extends ViewModel {
    private final UserRepository userRepository;

    public ProfileViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<SportiveUser> getUser() {
        return userRepository.getLiveUser();
    }

    public void regenerateProfilePicture() {
        userRepository.regenerateProfilePicture();
    }

    public void logout(Context context) {
        // Show a dialog to confirm logout then call userRepository.logout()
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(R.string.logout, (dialog, which) -> {
                    userRepository.logout();
                    Navigator.navigateToOnboardingActivity(context, true);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
