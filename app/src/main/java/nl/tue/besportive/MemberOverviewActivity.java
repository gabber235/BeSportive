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
        List<Member> items = new ArrayList<Member>();
        items.add(new Member("John wick","john.wick@email.com",R.drawable.a));
        items.add(new Member("Robert j","robert.j@email.com",R.drawable.b));
        items.add(new Member("James Gunn","james.gunn@email.com",R.drawable.a));
        items.add(new Member("Ricky tales","rickey.tales@email.com",R.drawable.b));
        items.add(new Member("Micky mose","mickey.mouse@email.com",R.drawable.a));
        items.add(new Member("Pick War","pick.war@email.com",R.drawable.b));
        items.add(new Member("Leg piece","leg.piece@email.com",R.drawable.a));
        items.add(new Member("Apple Mac","apple.mac@email.com",R.drawable.b));
        items.add(new Member("John wick","john.wick@email.com",R.drawable.a));
        items.add(new Member("Robert j","robert.j@email.com",R.drawable.b));
        items.add(new Member("James Gunn","james.gunn@email.com",R.drawable.a));
        items.add(new Member("Ricky tales","rickey.tales@email.com",R.drawable.b));
        items.add(new Member("Micky mose","mickey.mouse@email.com",R.drawable.a));
        items.add(new Member("Pick War","pick.war@email.com",R.drawable.b));
        items.add(new Member("Leg piece","leg.piece@email.com",R.drawable.a));
        items.add(new Member("Apple Mac","apple.mac@email.com",R.drawable.b));




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MemberAdapter(getApplicationContext(),items));
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