package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityChallengesBinding;

public class ChallengesActivity extends AppCompatActivity {

    private ActivityChallengesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}