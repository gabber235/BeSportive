package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityMemberOverviewBinding;

public class MemberOverviewActivity extends AppCompatActivity {
    private ActivityMemberOverviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Recyclerview in order to make a list of members
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        // Hardcoding Data to be pulled
        List<CompletedChallengesClass> items = new ArrayList<CompletedChallengesClass>();
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));
        items.add(new CompletedChallengesClass("John Wick", "Run 5km challenge", R.drawable.a, "18:17", "Medium"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CompletedChallengesAdapter(getApplicationContext(),items));

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