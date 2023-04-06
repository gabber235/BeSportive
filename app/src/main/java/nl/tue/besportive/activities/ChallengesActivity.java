package nl.tue.besportive.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.ChallengesAdapter;
import nl.tue.besportive.databinding.ActivityChallengesBinding;
import nl.tue.besportive.models.ChallengesViewModel;
import nl.tue.besportive.utils.BarUtils;

public class ChallengesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChallengesBinding binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ChallengesViewModel viewModel = new ViewModelProvider(this).get(ChallengesViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        ChallengesAdapter adapter = new ChallengesAdapter(viewModel);
        binding.challengesList.setAdapter(adapter);

        viewModel.getChallenges().observe(this, adapter::setItems);

        setSupportActionBar(BarUtils.setupPrimaryToolbar(binding.toolbarChallenges.toolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.challenges);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return BarUtils.selectToolbarMenuItem(this, item);
    }
}