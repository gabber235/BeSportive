package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.databinding.ActivityFeedBinding;

public class FeedActivity extends AppCompatActivity {

    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void leaderboard(View view) {
        startLeaderboardActivity();
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.feed);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(), ChallengesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.feed:
                        return true;
                    case R.id.leaderboard:
                        startActivity(new Intent(getApplicationContext(), LeaderboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_button:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.create_group:
                startActivity(new Intent(getApplicationContext(), CreateGroupActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.configure_challenges:
                startActivity(new Intent(getApplicationContext(), ConfigureChallengesActivity.class));
                overridePendingTransition(0, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);


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
