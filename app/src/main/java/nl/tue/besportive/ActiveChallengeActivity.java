package nl.tue.besportive;

import android.os.Bundle;

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
}