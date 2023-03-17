package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityJoinCreateGroupBinding;

public class JoinCreateGroupActivity extends AppCompatActivity {
    private ActivityJoinCreateGroupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinCreateGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}