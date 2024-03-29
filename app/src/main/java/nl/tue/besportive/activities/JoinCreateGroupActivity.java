package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityJoinCreateGroupBinding;
import nl.tue.besportive.models.JoinCreateGroupViewModel;
import nl.tue.besportive.utils.BarUtils;

public class JoinCreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityJoinCreateGroupBinding binding = ActivityJoinCreateGroupBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        // initialize variables here
        JoinCreateGroupViewModel viewModel = new ViewModelProvider(this).get(JoinCreateGroupViewModel.class);
        binding.setViewModel(viewModel);

        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupPrimaryToolbar(binding.toolbarJoinCreateGroup.toolbar));
    }
}
