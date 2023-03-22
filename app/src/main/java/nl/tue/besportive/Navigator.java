package nl.tue.besportive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Navigator {
    private static final String TAG = "Navigator";

    public static void navigateToCreateGroupActivity(Context context) {
        Log.i(TAG, "navigateToCreateGroupActivity");
        Intent intent = new Intent(context, CreateGroupActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToConfigureChallengesActivity(Context context) {
        Log.i(TAG, "navigateToConfigureChallengesActivity");
        Intent intent = new Intent(context, ConfigureChallengesActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToJoinCreateGroupActivity(Context context) {
        Log.i(TAG, "navigateToJoinCreateGroupActivity");
        Intent intent = new Intent(context, JoinCreateGroupActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToInviteMembersActivity(Context context) {
        Log.i(TAG, "navigateToInviteMembersActivity");
        Intent intent = new Intent(context, InviteMembersActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToFeedActivity(Context context) {
        Log.i(TAG, "navigateToFeedActivity");
        Intent intent = new Intent(context, FeedActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToOnboardingActivity(Context context) {
        Log.i(TAG, "navigateToOnboardingActivity");
        Intent intent = new Intent(context, OnboardingActivity.class);
        context.startActivity(intent);
    }
}
