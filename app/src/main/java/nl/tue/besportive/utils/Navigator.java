package nl.tue.besportive.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import nl.tue.besportive.activities.ChallengesActivity;
import nl.tue.besportive.activities.ConfigureChallengesActivity;
import nl.tue.besportive.activities.CreateGroupActivity;
import nl.tue.besportive.activities.FeedActivity;
import nl.tue.besportive.activities.InviteMembersActivity;
import nl.tue.besportive.activities.JoinCreateGroupActivity;
import nl.tue.besportive.activities.OnboardingActivity;
import nl.tue.besportive.activities.ProfileActivity;
import nl.tue.besportive.activities.StartChallengeActivity;

public class Navigator {
    private static final String TAG = "Navigator";

    public static void navigateToCreateGroupActivity(Context context) {
        navigateToCreateGroupActivity(context, false);
    }

    public static void navigateToCreateGroupActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToCreateGroupActivity");
        Intent intent = new Intent(context, CreateGroupActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }


    public static void navigateToConfigureChallengesActivity(Context context) {
        navigateToConfigureChallengesActivity(context, false);
    }

    public static void navigateToConfigureChallengesActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToConfigureChallengesActivity");
        Intent intent = new Intent(context, ConfigureChallengesActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }

    public static void navigateToJoinCreateGroupActivity(Context context) {
        navigateToJoinCreateGroupActivity(context, false);
    }

    public static void navigateToJoinCreateGroupActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToJoinCreateGroupActivity");
        Intent intent = new Intent(context, JoinCreateGroupActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }

    public static void navigateToInviteMembersActivity(Context context) {
        navigateToInviteMembersActivity(context, false);
    }

    public static void navigateToInviteMembersActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToInviteMembersActivity");
        Intent intent = new Intent(context, InviteMembersActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }

    public static void navigateToFeedActivity(Context context) {
        navigateToFeedActivity(context, false);
    }

    public static void navigateToFeedActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToFeedActivity");
        Intent intent = new Intent(context, FeedActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }

    public static void navigateToOnboardingActivity(Context context) {
        navigateToOnboardingActivity(context, false);
    }

    public static void navigateToOnboardingActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToOnboardingActivity");
        Intent intent = new Intent(context, OnboardingActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }


    public static void navigateToChallengesActivity(Context context) {
        navigateToChallengesActivity(context, false);
    }

    public static void navigateToChallengesActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToChallengesActivity");
        Intent intent = new Intent(context, ChallengesActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }

    public static void navigateToLeaderboardActivity(Context context) {
        navigateToLeaderboardActivity(context, false);
    }

    public static void navigateToLeaderboardActivity(Context context, boolean finishActivity) {
        Log.i(TAG, "navigateToLeaderboardActivity");
        Intent intent = new Intent(context, ChallengesActivity.class);
        context.startActivity(intent);
        if (finishActivity) finishActivity(context);
    }

    public static void navigateToStartChallengeActivity(Context context, String challengeId) {
        Log.i(TAG, "navigateToStartChallengeActivity");
        Intent intent = new Intent(context, StartChallengeActivity.class);
        intent.putExtra("challengeId", challengeId);
        context.startActivity(intent);
    }

    public static void navigateToProfileActivity(Context context) {
        Log.i(TAG, "navigateToProfile");
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    private static void finishActivity(Context context) {
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }


    public static void navigateToStartingPage(Context context) {
        Log.i(TAG, "navigateToStartingPage");
        checkIfUserIsStillLoggedIn(context);
    }

    private static void checkIfUserIsStillLoggedIn(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            navigateToOnboardingActivity(context, true);
            return;
        }

        user.reload().addOnCompleteListener((task) -> onUserReloaded(context, task));
    }

    private static void onUserReloaded(Context context, Task<Void> task) {
        if (task.isSuccessful()) {
            checkIfUserHasGroup(context, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()));
        } else {
            Navigator.navigateToOnboardingActivity(context, true);
        }
    }

    private static void checkIfUserHasGroup(Context context, FirebaseUser user) {
        FirebaseFirestore.getInstance().collection("groups")
                .whereNotEqualTo("members." + user.getUid(), null)
                .get()
                .addOnCompleteListener((task) -> onGroupQueryComplete(context, task));
    }

    private static void onGroupQueryComplete(Context context, Task<QuerySnapshot> task) {
        if (task.isSuccessful() && !task.getResult().isEmpty()) {
            Navigator.navigateToFeedActivity(context, true);
        } else {
            Navigator.navigateToJoinCreateGroupActivity(context, true);
        }
    }
}
