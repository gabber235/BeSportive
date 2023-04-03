package nl.tue.besportive.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.RecycleViewAdapter;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.databinding.ActivityChallengesBinding;
import nl.tue.besportive.repositories.GroupRepository;
import nl.tue.besportive.utils.BarUtils;
import nl.tue.besportive.utils.FirebaseQueryLiveData;
import nl.tue.besportive.utils.Navigator;

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

        setSupportActionBar(BarUtils.setupToolbar(binding.toolbarFeed.feedToolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.challenges);

        EventChangeListener();
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Challenge challenge = challengesList.get(position);
            String challengeId = challenge.getId();

            Navigator.navigateToStartChallengeActivity(ChallengesActivity.this, getGroupId(), challengeId);
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