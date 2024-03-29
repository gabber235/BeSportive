package nl.tue.besportive.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import nl.tue.besportive.R;
import nl.tue.besportive.repositories.GroupRepository;

public class BarUtils {
    public static Toolbar setupPrimaryToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.img); // This is the default icon. Will be replaced by the profile picture.

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                toolbar.setNavigationIcon(new BitmapDrawable(toolbar.getResources(), scaledBitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                toolbar.setNavigationIcon(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                toolbar.setNavigationIcon(placeHolderDrawable);
            }
        };

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return toolbar;
        }

        FirebaseFirestore.getInstance().document("users/" + user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String photoUrl = task.getResult().getString("photoUrl");
                if (photoUrl != null) {
                    Picasso.get().load(photoUrl)
                            .error(R.drawable.img)
                            .placeholder(R.drawable.img)
                            .into(target);
                }
            }
        });

        // The set navigation listener needs to be called after the toolbar is set as the support bar.
        new Handler().postDelayed(() -> {
            toolbar.setNavigationOnClickListener(v -> Navigator.navigateToProfileActivity(v.getContext()));
        }, 10);

        return toolbar;
    }

    public static Toolbar setupBackToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_back);

        // The set navigation listener needs to be called after the toolbar is set as the support bar.
        new Handler().postDelayed(() -> {
            toolbar.setNavigationOnClickListener(v -> {
                Navigator.finishActivity(v.getContext());
            });
        }, 10);

        return toolbar;
    }

    public static boolean selectToolbarMenuItem(Context context, MenuItem item) {
        if (item.getItemId() == R.id.invite_members) {
            Navigator.navigateToInviteMembersActivity(context);
            return true;
        }
        if (item.getItemId() == R.id.configure_challenges) {
            Navigator.navigateToConfigureChallengesActivity(context);
            return true;
        }
        if (item.getItemId() == R.id.disband_group) {
            verifyDisbandGroup(context, () -> {
                GroupRepository repository = new GroupRepository();
                repository.disbandGroup(() -> {
                    Navigator.navigateToStartingPage(context);
                });
            });
            return true;
        }
        if (item.getItemId() == R.id.leave_group) {
            verifyLeaveGroup(context, () -> {
                GroupRepository repository = new GroupRepository();
                repository.leaveGroup(() -> {
                    Navigator.navigateToStartingPage(context);
                });
            });
            return true;
        }
        return false;
    }

    private static void verifyDisbandGroup(Context context, Runnable onDisband) {
        new AlertDialog.Builder(context)
                .setTitle("Disband group")
                .setMessage("Are you sure you want to disband this group?")
                .setPositiveButton("Disband", (dialog, which) -> onDisband.run())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private static void verifyLeaveGroup(Context context, Runnable onLeave) {
        new AlertDialog.Builder(context)
                .setTitle("Leave group")
                .setMessage("Are you sure you want to leave this group?")
                .setPositiveButton("Leave", (dialog, which) -> onLeave.run())
                .setNegativeButton("Cancel", null)
                .show();
    }

    public static void setupBottomNavigation(Activity activity,
                                             BottomNavigationView bottomNavigationView,
                                             int selectedItemId) {
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
