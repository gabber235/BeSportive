package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.adapters.ConfigureChallengesAdapter.DefaultChallengesAdapter;
import nl.tue.besportive.adapters.ConfigureChallengesAdapter.GroupChallengesAdapter;
import nl.tue.besportive.databinding.ActivityConfigureChallengesBinding;
import nl.tue.besportive.models.ConfigureChallengesViewModel;
import nl.tue.besportive.utils.BarUtils;

public class ConfigureChallengesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityConfigureChallengesBinding binding = ActivityConfigureChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean inCreateGroupFlow = getIntent().getBooleanExtra("inCreateGroupFlow", false);

        // initialize variables here
        ConfigureChallengesViewModel viewModel = new ViewModelProvider(this,
                new ConfigureChallengesViewModel.
                        ConfigureChallengesViewModelFactory(inCreateGroupFlow)).get(ConfigureChallengesViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        GroupChallengesAdapter groupAdapter = new GroupChallengesAdapter(viewModel);
        DefaultChallengesAdapter defaultAdapter = new DefaultChallengesAdapter(viewModel);

        // Set the adapters for the RecyclerViews
        binding.groupChallengesRecyclerview.setAdapter(groupAdapter);
        binding.defaultChallengesRecyclerview.setAdapter(defaultAdapter);

        // Observe the LiveData in the ViewModel
        viewModel.getChallenges().observe(this, groupAdapter::setItems);
        viewModel.getDefaultChallenges().observe(this, defaultAdapter::setItems);

        setSupportActionBar(BarUtils.setupBackToolbar(binding.configureChallengesToolbar.toolbar));
    }
}
