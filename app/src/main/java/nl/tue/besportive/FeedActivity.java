package nl.tue.besportive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nl.tue.besportive.databinding.ActivityFeedBinding;

public class FeedActivity extends AppCompatActivity {
    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.configureChallengeButton.setOnClickListener(this::configureChallenges);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.feed);

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
                    case R.id.feed:
                        return true;
                    case R.id.leaderboard:
                        startActivity(new Intent(getApplicationContext(),LeaderboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("TAG", "logged in");
            // User is signed in
            System.out.println(user.getUid());
        } else {
            // No user is signed in
            Log.d("TAG", "Not logged in");

        }
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