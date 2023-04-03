package nl.tue.besportive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.tue.besportive.databinding.ActivityChallengesBinding;

public class ChallengesActivity extends AppCompatActivity {

    private static final String TAG = "Challenges App";
    List<Challenge> challengesList = new ArrayList<Challenge>();
    RecyclerView recyclerView;
    FirebaseFirestore db;
    private ActivityChallengesBinding binding;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleViewAdapter.RecycleViewClickListener listener;
    private GroupRepository groupRepository;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //fillChallengesList();

        //FIREBASE PART
        db = FirebaseFirestore.getInstance();
        challengesList = new ArrayList<>();
        groupRepository = new GroupRepository();
        groupRepository.getLiveGroup(); // Start listening for changes

        setOnClickListener();

        RecyclerView recyclerView = findViewById(R.id.ly_challengeslist);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        recyclerView = findViewById(R.id.ly_challengeslist);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new RecycleViewAdapter(challengesList, listener);
        recyclerView.setAdapter(mAdapter);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

// Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.challenges);

// Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.challenges:
                        return true;
                    case R.id.leaderboard:
                        startActivity(new Intent(getApplicationContext(), LeaderboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.feed:
                        startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                        overridePendingTransition(0, 0);
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
                Challenge challenge = challengesList.get(position);
                String challengeId = challenge.getId();

                Navigator.navigateToStartChallengeActivity(ChallengesActivity.this, getGroupId(), challengeId);
            }
        }; // fetch the ids rather than
    }

    private void EventChangeListener() {
        LiveData<QuerySnapshot> challengesSnapshot = Transformations.switchMap(groupRepository.getLiveGroup(),
                group -> new FirebaseQueryLiveData(db.collection("groups/" + group.getId() + "/challenges").orderBy("difficulty", Query.Direction.ASCENDING)));

        LiveData<List<Challenge>> challenges = Transformations.map(challengesSnapshot, queryDocumentSnapshots -> {
            List<Challenge> challengesList = queryDocumentSnapshots.toObjects(Challenge.class);
            // Firebase toObject() does not set the id, so we have to do it manually
            for (int i = 0; i < challengesList.size(); i++) {
                challengesList.get(i).setId(queryDocumentSnapshots.getDocuments().get(i).getId());
            }
            return challengesList;
        });

        challenges.observe(this, documentSnapshots -> {
            challengesList.clear();
            challengesList.addAll(documentSnapshots);

            mAdapter.notifyDataSetChanged();
        });
    }

    private String getGroupId() {
        return Objects.requireNonNull(groupRepository.getLiveGroup().getValue()).getId();
    }
}