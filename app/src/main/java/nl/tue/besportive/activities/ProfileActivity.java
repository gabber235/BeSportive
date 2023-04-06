package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityProfileBinding;
import nl.tue.besportive.models.ProfileViewModel;
import nl.tue.besportive.utils.BarUtils;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set viewModel
        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // using toolbar as ActionBar
        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarProfilePage.toolbar));
    }
}