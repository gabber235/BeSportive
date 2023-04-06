package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.adapters.AddChallengeDifficultyAdapter;
import nl.tue.besportive.databinding.ActivityAddChallengeBinding;
import nl.tue.besportive.models.AddChallengeViewModel;
import nl.tue.besportive.utils.BarUtils;

public class AddChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddChallengeBinding binding = ActivityAddChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AddChallengeViewModel viewModel = new ViewModelProvider(this).get(AddChallengeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        AddChallengeDifficultyAdapter adapter = new AddChallengeDifficultyAdapter(this);
        binding.difficultyLevelSpinner.setAdapter(adapter);

        setSupportActionBar(BarUtils.setupBackToolbar(binding.addChallengeToolbar.toolbar));
    }
}