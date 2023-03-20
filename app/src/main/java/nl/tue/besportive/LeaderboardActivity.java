package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import nl.tue.besportive.databinding.ActivityLeaderboardBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private ActivityLeaderboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setting onclicklistener on memberButton
        binding.memberOverviewButton.setOnClickListener(this::memberOverview);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.leaderboard);

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
                    case R.id.leaderboard:
                        return true;
                    case R.id.feed:
                        startActivity(new Intent(getApplicationContext(),FeedActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //Recyclerview in order to make a list of members
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        // Hardcoding Data to be pulled
        List<Member> items = new ArrayList<Member>();
        items.add(new Member("John wick","john.wick@email.com",R.drawable.a, 10));
        items.add(new Member("Robert j","robert.j@email.com",R.drawable.b, 15));
        items.add(new Member("James Gunn","james.gunn@email.com",R.drawable.a, 100 ));
        items.add(new Member("Ricky tales","rickey.tales@email.com",R.drawable.b, 200));
        items.add(new Member("Micky mose","mickey.mouse@email.com",R.drawable.a, 10));
        items.add(new Member("Pick War","pick.war@email.com",R.drawable.b, 10));
        items.add(new Member("Leg piece","leg.piece@email.com",R.drawable.a, 10 ));
        items.add(new Member("Apple Mac","apple.mac@email.com",R.drawable.b, 10 ));
        items.add(new Member("John wick","john.wick@email.com",R.drawable.a, 10 ));
        items.add(new Member("Robert j","robert.j@email.com",R.drawable.b, 10));
        items.add(new Member("James Gunn","james.gunn@email.com",R.drawable.a, 10));
        items.add(new Member("Ricky tales","rickey.tales@email.com",R.drawable.b, 10));
        items.add(new Member("Micky mose","mickey.mouse@email.com",R.drawable.a, 10));
        items.add(new Member("Pick War","pick.war@email.com",R.drawable.b, 10));
        items.add(new Member("Leg piece","leg.piece@email.com",R.drawable.a, 10));
        items.add(new Member("Apple Mac","apple.mac@email.com",R.drawable.b, 10));




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MemberAdapter(getApplicationContext(),items));
        // This function is from this https://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/, this makes it easy to access a row in the list
        // and add a onclick handler
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Intent intent = new Intent(view.getContext(), MemberOverviewActivity.class);
                startActivity(intent);
                finish();
            }
        });
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