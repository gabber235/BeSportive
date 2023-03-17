package nl.tue.besportive;

import android.os.Bundle;

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
}