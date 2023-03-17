package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityMemberOverviewBinding;

public class MemberOverviewActivity extends AppCompatActivity {
    private ActivityMemberOverviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    private void leaderboard(View view) {
        startLeaderboardActivity();
    }
    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}