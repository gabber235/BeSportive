package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityStartChallengeBinding;
import nl.tue.besportive.models.StartChallengeViewModel;
import nl.tue.besportive.utils.BarUtils;

public class StartChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStartChallengeBinding binding = ActivityStartChallengeBinding.inflate(getLayoutInflater());

        String challengeId = getIntent().getStringExtra("challengeId");

        StartChallengeViewModel viewModel = new ViewModelProvider(this,
                new StartChallengeViewModel.StartChallengeViewModelFactory(challengeId)).get(StartChallengeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarChallenges.toolbar));
    }
}