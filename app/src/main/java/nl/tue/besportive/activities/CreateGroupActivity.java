package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityCreateGroupBinding;
import nl.tue.besportive.models.CreateGroupViewModel;
import nl.tue.besportive.utils.BarUtils;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCreateGroupBinding binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        // initialize variables here
        CreateGroupViewModel viewModel = new ViewModelProvider(this).get(CreateGroupViewModel.class);
        binding.setViewModel(viewModel);

        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarCreateGroup.toolbar));
    }
}
