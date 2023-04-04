package nl.tue.besportive.activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.FeedAdapter;
import nl.tue.besportive.databinding.ActivityFeedBinding;
import nl.tue.besportive.models.FeedViewModel;
import nl.tue.besportive.utils.BarUtils;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFeedBinding binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FeedViewModel viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        FeedAdapter adapter = new FeedAdapter(viewModel);
        binding.recyclerView.setAdapter(adapter);

        viewModel.getFinishedCompletedChallenges().observe(this, adapter::setItems);

        // Setup toolbar and bottom navigation
        setSupportActionBar(BarUtils.setupPrimaryToolbar(binding.toolbarFeed.toolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.feed);
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

