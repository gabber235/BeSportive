package nl.tue.besportive;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.StartupTime;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import nl.tue.besportive.databinding.ActivityChallengesBinding;

public class ChallengesActivity extends AppCompatActivity {

    private ActivityChallengesBinding binding;
    List<Challenges> challengesList = new ArrayList<Challenges>();
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private RecycleViewAdapter.RecycleViewClickListener listener;

    FirebaseFirestore db;

    private static final String TAG = "Challenges App";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //fillChallengesList();
        Log.d(TAG, "onCreate:"+ challengesList.toString());
        Toast.makeText(this,"Challenges Count =" + challengesList.size(), Toast.LENGTH_LONG).show();

        //FIREBASE PART
        db = FirebaseFirestore.getInstance();
        challengesList = new ArrayList<Challenges>();



        setOnClickListener();
        RecyclerView recyclerView = findViewById(R.id.ly_challengeslist);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        recyclerView =  findViewById(R.id.ly_challengeslist);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new RecycleViewAdapter(challengesList, listener);
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

        EventChangeListener();




    }

    private void setOnClickListener() {
        listener = new RecycleViewAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent= new Intent(getApplicationContext(),ActiveChallengeActivity.class);
                intent.putExtra("name",challengesList.get(position).getName());
                startActivity(intent);
            }
        };
    }

    private void EventChangeListener() {

        db.collection("defaultChallenges").orderBy("difficulty", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Log.e("firestore error", error.getMessage());
                            return ;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){

                                challengesList.add(dc.getDocument().toObject(Challenges.class));
                            }
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                });
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
    public void goToActiveChallenges(View view, int position) {
        Intent intent = new Intent(this, ActiveChallengeActivity.class);
        intent.putExtra("name",challengesList.get(position).getName());

        startActivity(intent);
    }
}