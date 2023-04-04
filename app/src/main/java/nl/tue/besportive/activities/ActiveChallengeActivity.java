package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityActiveChallengeBinding;
import nl.tue.besportive.models.ActiveChallengeViewModel;

public class ActiveChallengeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityActiveChallengeBinding binding = ActivityActiveChallengeBinding.inflate(getLayoutInflater());

        ActiveChallengeViewModel viewModel = new ViewModelProvider(this).get(ActiveChallengeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());
    }
}