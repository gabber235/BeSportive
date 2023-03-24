package nl.tue.besportive;

import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.tue.besportive.databinding.ActivityChallengesBinding;
import okhttp3.Challenge;

public class ChallengesActivity extends AppCompatActivity {

    private ActivityChallengesBinding binding;
    List<Challenges> challengesList = new ArrayList<Challenges>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "Challenges App";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fillChallengesList();
        Log.d(TAG, "onCreate:"+ challengesList.toString());
        Toast.makeText(this,"Challenges Count =" + challengesList.size(), Toast.LENGTH_LONG).show();

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