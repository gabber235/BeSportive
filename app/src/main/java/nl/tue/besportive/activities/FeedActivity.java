package nl.tue.besportive.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.R;
import nl.tue.besportive.databinding.ActivityFeedBinding;

public class FeedActivity extends AppCompatActivity {
    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar_feed);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(""); // hide title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.img); // Replace back button with profile image.

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.feed);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        }
        switch (item.getItemId()) {
            case R.id.invite_members:
                startActivity(new Intent(getApplicationContext(), InviteMembersActivity.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.configure_challenges:
                startActivity(new Intent(getApplicationContext(), ConfigureChallengesActivity.class));
                overridePendingTransition(0, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void configureChallenges(View view) {
        startConfigureChallengesActivity();
    }

    private void startConfigureChallengesActivity() {
        Intent intent = new Intent(this, ConfigureChallengesActivity.class);
        startActivity(intent);
        finish();
    }

}