package nl.tue.besportive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.databinding.ActivityChallengesBinding;

public class ChallengesActivity extends AppCompatActivity {

    private ActivityChallengesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.challenges);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.challenges:
                        return true;
                    case R.id.leaderboard:
                        startActivity(new Intent(getApplicationContext(),LeaderboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.feed:
                        startActivity(new Intent(getApplicationContext(),FeedActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private void leaderboard(View view) {
        startLeaderboardActivity();
    }

    private void activeChallenge(View view) {
        startActiveChallengeActivity();
    }

    private void feed(View view) {
        startFeedActivity();
    }

    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startActiveChallengeActivity() {
        Intent intent = new Intent(this, InviteMembersActivity.class);
        startActivity(intent);
        finish();
    }

    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}