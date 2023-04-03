package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.tue.besportive.Group.Member;
import nl.tue.besportive.databinding.ActivityLeaderboardBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private ActivityLeaderboardBinding binding;
    public Map<String, Member> membersList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feed);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Member> items = new ArrayList<>();
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.leaderboard);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(), ChallengesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.leaderboard:
                        return true;
                    case R.id.feed:
                        startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        //Recyclerview in order to make a list of members
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        // Hardcoding Data to be pulled
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MemberAdapter memberAdapter = new MemberAdapter(getApplicationContext(), items);
        recyclerView.setAdapter(memberAdapter);
        // Bindings dont work yet dont know why;
        // binding.setViewModel(viewModel);
        // binding.setLifecycleOwner(this);
        LeaderboardViewModel viewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        viewModel.getMembersFromViewModel().observe(this, members -> {
            memberAdapter.setMembers(viewModel.getMembersFromViewModel().getValue());
        });


        // This function is from this https://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/, this makes it easy to access a row in the list
        // and add a onclick handler
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                System.out.println(viewModel.getGroup().getValue().getId());
                Intent intent = new Intent(view.getContext(), MemberOverviewActivity.class);
                intent.putExtra("userId", memberAdapter.getMember(position).getId());
                intent.putExtra("groupId", viewModel.getGroup().getValue().getId());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    public void memberOverview(View view) {
        startMemberOverviewActivity();
    }

    private void challengesOverview(View view) {
        startChallengesOverviewActivity();
    }

    private void feed(View view) {
        startFeedActivity();
    }

    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMemberOverviewActivity() {
        Intent intent = new Intent(this, MemberOverviewActivity.class);
        startActivity(intent);
        finish();
    }

    private void startChallengesOverviewActivity() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivity(intent);
        finish();
    }
}