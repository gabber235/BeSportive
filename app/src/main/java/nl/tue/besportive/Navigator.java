package nl.tue.besportive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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


    public static void navigateToStartChallengeActivity(Context context, String groupId, String challengeId) {
        Log.i(TAG, "navigateToStartChallengeActivity");
        Intent intent = new Intent(context, StartChallengeActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("challengeId", challengeId);
        context.startActivity(intent);
    }

    private static void finishActivity(Context context) {
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
