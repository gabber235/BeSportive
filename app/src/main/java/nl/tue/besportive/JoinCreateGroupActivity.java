package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityJoinCreateGroupBinding;

public class JoinCreateGroupActivity extends AppCompatActivity {
    private ActivityJoinCreateGroupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinCreateGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createGroupButton.setOnClickListener(this::createGroup);
        binding.joinButton.setOnClickListener(this::joinGroup);
    }

    public void createGroup(View view) {
        startCreateGroupActivity();
    }

    public void joinGroup(View view) {
        startFeedActivity();
    }

    public void startCreateGroupActivity() {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
        finish();
    }

    public void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}