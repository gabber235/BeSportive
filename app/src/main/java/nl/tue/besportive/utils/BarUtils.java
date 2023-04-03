package nl.tue.besportive.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.R;

public class BarUtils {
    public static Toolbar setupToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.img); // Replace back button with profile image.

        return toolbar;
    }

    public static boolean selectToolbarMenuItem(Context context, MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigator.navigateToProfileActivity(context);
            return true;
        }
        if (item.getItemId() == R.id.invite_members) {
            Navigator.navigateToInviteMembersActivity(context);
            return true;
        }
        if (item.getItemId() == R.id.configure_challenges) {
            Navigator.navigateToConfigureChallengesActivity(context);
            return true;
        }
        return false;
    }

    public static void setupBottomNavigation(Activity activity, BottomNavigationView bottomNavigationView, int selectedItemId) {
        // Set Home selected
        bottomNavigationView.setSelectedItemId(selectedItemId);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == selectedItemId) {
                return true;
            }

            if (item.getItemId() == R.id.feed) {
                Navigator.navigateToFeedActivity(activity, true);
                activity.overridePendingTransition(0, 0);
                return true;
            }

            if (item.getItemId() == R.id.challenges) {
                Navigator.navigateToChallengesActivity(activity, true);
                activity.overridePendingTransition(0, 0);
                return true;
            }

            if (item.getItemId() == R.id.leaderboard) {
                Navigator.navigateToLeaderboardActivity(activity, true);
                activity.overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
