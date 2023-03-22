package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityJoinCreateGroupBinding;

public class JoinCreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJoinCreateGroupBinding binding = ActivityJoinCreateGroupBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        JoinCreateGroupViewModel viewModel = new ViewModelProvider(this).get(JoinCreateGroupViewModel.class);
        binding.setViewModel(viewModel);

        setContentView(binding.getRoot());
    }
}