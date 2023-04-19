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

        // Inflate layout and set it as the activity content
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up ViewModel and observe changes to it
        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Set up toolbar as ActionBar
        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarProfilePage.toolbar));
    }
}
