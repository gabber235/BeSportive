package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityConfigureChallengesBinding;

public class ConfigureChallengesActivity extends AppCompatActivity {
    private ActivityConfigureChallengesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigureChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    private void inviteMembers(View view) {
        startInviteMembersActivity();
    }

    private void feed(View view) {
        startFeedActivity();
    }
    private void startInviteMembersActivity() {
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