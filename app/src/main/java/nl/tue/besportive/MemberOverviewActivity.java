package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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