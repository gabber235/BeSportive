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

    private boolean isAdministrator = false;

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

        viewModel.isAdministrator().observe(this, isAdministrator -> {
            this.isAdministrator = isAdministrator;
            invalidateOptionsMenu();
        });
        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupPrimaryToolbar(binding.toolbarLeaderboard.toolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.leaderboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isAdministrator) {
            getMenuInflater().inflate(R.menu.admin_settings_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.member_setttings_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return BarUtils.selectToolbarMenuItem(this, item);
    }


}