package nl.tue.besportive;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.databinding.ActivityFeedBinding;

public class FeedActivity extends AppCompatActivity {
    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // assigning ID of the toolbar to a variable
    //    Toolbar toolbar = findViewById(R.id.toolbar);

        // using toolbar as ActionBar
        //  setSupportActionBar(toolbar);

        // Initialize and assign variable
        AppBarLayout appBarLayout=findViewById(R.id.settings_menu);

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.settings_menu, menu);
            return true;

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()) {
                case R.id.create_group:
                    startActivity(new Intent(getApplicationContext(),CreateGroupActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.configure_challenges:
                    startActivity(new Intent(getApplicationContext(),ConfigureChallengesActivity.class));
                    overridePendingTransition(0,0);
                    return true;

            }
        }



      //  @Override
      //  public boolean onCreateOptionsMenu(Menu menu) {
    //        MenuInflater inflater = getMenuInflater();
    //        inflater.inflate(R.menu.settings_menu, menu);
    //        return true;
    //    }

     //   Override
     //   public boolean onOptionsItemSelected(MenuItem item) {

     //       switch (item.getItemId()) {
     //           case R.id.profile_button:
     //               startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
     //               overridePendingTransition(0,0);
     //               return true;
     //       }
     //       return false;
     //   }

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.feed);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(),ChallengesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.feed:
                        return true;
                    case R.id.leaderboard:
                        startActivity(new Intent(getApplicationContext(),LeaderboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}


