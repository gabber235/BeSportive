package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.databinding.ActivityLeaderboardBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private ActivityLeaderboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setting onclicklistener on memberButton
        binding.memberOverviewButton.setOnClickListener(this::memberOverview);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.leaderboard);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(),ChallengesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.leaderboard:
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

    private void memberOverview(View view) {
        startMemberOverviewActivity();
    }

    private void challengesOverview(View view) {
        startChallengesOverviewActivity();
    }

    private void feed(View view) {
        startFeedActivity();
    }
    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMemberOverviewActivity() {
        Intent intent = new Intent(this, MemberOverviewActivity.class);
        startActivity(intent);
        finish();
    }
    private void startChallengesOverviewActivity() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivity(intent);
        finish();
    }
}