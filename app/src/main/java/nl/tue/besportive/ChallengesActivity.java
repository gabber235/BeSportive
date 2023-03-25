package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.tue.besportive.databinding.ActivityChallengesBinding;

public class ChallengesActivity extends AppCompatActivity {

    private ActivityChallengesBinding binding;
    List<Challenges> challengesList = new ArrayList<Challenges>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Button bt_gotoactivech;


    private static final String TAG = "Challenges App";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fillChallengesList();
        Log.d(TAG, "onCreate:"+ challengesList.toString());
        Toast.makeText(this,"Challenges Count =" + challengesList.size(), Toast.LENGTH_LONG).show();

        bt_gotoactivech = findViewById(R.id.bt_gotoactivech);
        bt_gotoactivech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChallengesActivity.this ,ActiveChallengeActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.ly_challengeslist);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        recyclerView =  findViewById(R.id.ly_challengeslist);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new RecycleViewAdapter(challengesList);
        recyclerView.setAdapter(mAdapter);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

// Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.challenges);

// Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.challenges:
                        return true;
                    case R.id.leaderboard:
                        startActivity(new Intent(getApplicationContext(),LeaderboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.feed:
                        startActivity(new Intent(getApplicationContext(),FeedActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }
    private void fillChallengesList() {
        Challenges c1  = new Challenges(0, "Run 5 km");
        Challenges c2  = new Challenges(1, "Run 10km");
        Challenges c3  = new Challenges(2, "100 push ups");
        challengesList.addAll(Arrays.asList( new Challenges[]{c1,c2,c3}));
    }
    private void leaderboard(View view) {
        startLeaderboardActivity();
    }

    private void activeChallenge(View view) {
        startActiveChallengeActivity();
    }

    private void feed(View view) {
        startFeedActivity();
    }

    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startActiveChallengeActivity() {
        Intent intent = new Intent(this, InviteMembersActivity.class);
        startActivity(intent);
        finish();
    }

    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}