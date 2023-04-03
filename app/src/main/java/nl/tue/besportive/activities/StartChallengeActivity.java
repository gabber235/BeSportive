package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityStartChallengeBinding;
import nl.tue.besportive.models.StartChallengeViewModel;

public class StartChallengeActivity extends AppCompatActivity {

    private ActivityStartChallengeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartChallengeBinding.inflate(getLayoutInflater());

        String groupId = getIntent().getStringExtra("groupId");
        String challengeId = getIntent().getStringExtra("challengeId");

        StartChallengeViewModel viewModel = new ViewModelProvider(this, new StartChallengeViewModel.StartChallengeViewModelFactory(groupId, challengeId)).get(StartChallengeViewModel.class);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());
    }
}