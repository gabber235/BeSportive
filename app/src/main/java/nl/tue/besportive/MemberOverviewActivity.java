package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityMemberOverviewBinding;

public class MemberOverviewActivity extends AppCompatActivity {
    private ActivityMemberOverviewBinding binding;
    private String userId;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Recyclerview in order to make a list of members
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        // Hardcoding Data to be pulled
        List<CompletedChallenges> items = new ArrayList<CompletedChallenges>();
        // Get info from leaderboard activity
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String groupId = intent.getStringExtra("groupId");
        System.out.println(userId);
        System.out.println(groupId);
        System.out.println("memberOverview");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CompletedChallengesAdapter completedChallengesAdapter = new CompletedChallengesAdapter(getApplicationContext(),items);
        recyclerView.setAdapter(completedChallengesAdapter);
        CompletedChallengesViewModel viewModel = new ViewModelProvider(this).get(CompletedChallengesViewModel.class);
        new Handler().postDelayed(() -> {
            System.out.println(viewModel.getCompletedChallenges(groupId, userId).getValue());
            viewModel.getCompletedChallenges(groupId, userId).observe(this, completedChallenges -> {
                completedChallengesAdapter.setCompletedChallenges(viewModel.getCompletedChallenges("TResVKvwgVKs7rLgOcmL", "1FnwF3IQkN9aho4WnMwTa4FCHF2i").getValue());
            });
        }, 1000); // 2-second delay

        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_member_overview);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    // This creates a switch case in order to go back to the feed page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(MemberOverviewActivity.this, LeaderboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void leaderboard(View view) {
        startLeaderboardActivity();
    }
    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}