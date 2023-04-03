package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityInviteMembersBinding;

public class InviteMembersActivity extends AppCompatActivity {
    private ActivityInviteMembersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInviteMembersBinding.inflate(getLayoutInflater());

        InviteMembersViewModel viewModel = new ViewModelProvider(this).get(InviteMembersViewModel.class);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());

        binding.doneButton.setOnClickListener(this::feed);
    }
    private void feed(View view) {
        startFeedActivity();
    }
    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}