package nl.tue.besportive;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;
import android.content.Intent;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feed);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(""); //hide title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.img); //Replace back button with profile image.


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
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.settings_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            switch (item.getItemId()) {
                case R.id.invite_members:
                    startActivity(new Intent(getApplicationContext(), InviteMembersActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.configure_challenges:
                    startActivity(new Intent(getApplicationContext(), ConfigureChallengesActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return super.onOptionsItemSelected(item);

        }



}


