package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityActiveChallengeBinding;

public class ActiveChallengeActivity extends AppCompatActivity {
    private ActivityActiveChallengeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActiveChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    private void leaderboard(View view) {
        startLeaderboardActivity();
    }

    private void challenges(View view) {
        startChallengesActivity();
    }

    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startChallengesActivity() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivity(intent);
        finish();
    }

}