package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityFeedBinding;

public class FeedActivity extends AppCompatActivity {
    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button2.setOnClickListener(this::Profile);
    }

    private void leaderboard(View view) {
        startLeaderboardActivity();
    }

    private void challengesOverview(View view) {
        startChallengesOverviewActivity();
    }

    private void createJoinGroup(View view) {
        startCreateJoinGroupActivity();
    }

    private void configureChallenges(View view) {
        startConfigureChallengesActivity();
    }

    private void inviteMembers(View view) {
        startInviteMembersActivity();
    }

    private void Profile(View view) {
        startProfileActivity();
    }


    private void startConfigureChallengesActivity() {
        Intent intent = new Intent(this, ConfigureChallengesActivity.class);
        startActivity(intent);
        finish();
    }

    private void startChallengesOverviewActivity() {
        Intent intent = new Intent(this, ActiveChallengeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startCreateJoinGroupActivity() {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
        finish();
    }

    private void startProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void startInviteMembersActivity() {
        Intent intent = new Intent(this, InviteMembersActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }


    public void feed(View view) {
        startFeedActivity();
    }

    //    I used public and you used private .....???
    public void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }

}