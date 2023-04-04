package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import nl.tue.besportive.databinding.ActivityActiveChallengeBinding;
import nl.tue.besportive.models.ActiveChallengeViewModel;

public class ActiveChallengeActivity extends AppCompatActivity {

    ActiveChallengeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityActiveChallengeBinding binding = ActivityActiveChallengeBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(ActiveChallengeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActiveChallengeViewModel.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            viewModel.onPhotoTaken(this, Objects.requireNonNull(data));
        }
    }
}