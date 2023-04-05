package nl.tue.besportive.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.LeaderboardAdapter;
import nl.tue.besportive.databinding.ActivityLeaderboardBinding;
import nl.tue.besportive.models.LeaderboardViewModel;
import nl.tue.besportive.utils.BarUtils;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLeaderboardBinding binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());

        LeaderboardViewModel viewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        LeaderboardAdapter adapter = new LeaderboardAdapter(viewModel);
        binding.leaderboardList.setAdapter(adapter);

        viewModel.getMembers().observe(this, adapter::setItems);

        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupPrimaryToolbar(binding.toolbarLeaderboard.toolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.leaderboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return BarUtils.selectToolbarMenuItem(this, item);
    }
}