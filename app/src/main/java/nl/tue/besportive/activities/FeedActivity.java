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

    private boolean isAdministrator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFeedBinding binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize variables here
        FeedViewModel viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        FeedAdapter adapter = new FeedAdapter(viewModel);
        binding.recyclerView.setAdapter(adapter);

        viewModel.getFeedItems().observe(this, adapter::setItems);

        viewModel.isAdministrator().observe(this, isAdministrator -> {
            this.isAdministrator = isAdministrator;
            invalidateOptionsMenu();
        });

        // Setup toolbar and bottom navigation
        setSupportActionBar(BarUtils.setupPrimaryToolbar(binding.toolbarFeed.toolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.feed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // use curly braces after if statement to follow the standard
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
