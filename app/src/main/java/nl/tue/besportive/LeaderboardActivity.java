package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import androidx.annotation.NonNull;

import nl.tue.besportive.databinding.ActivityLeaderboardBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private ActivityLeaderboardBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feed);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MemberAdapter memberAdapter = new MemberAdapter(getApplicationContext(),items);
        recyclerView.setAdapter(memberAdapter);

        //Fetching data from firestore
        String groupID = "TResVKvwgVKs7rLgOcmL";
        FirebaseFirestore.getInstance().collection("groups").document(groupID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Pull all data
                        Map<String, Object> groupMap = document.getData();
                        //System.out.println(groupMap);
                            for (Map.Entry<String, Object> entry : groupMap.entrySet()) {
                                //System.out.println(entry);
                                //System.out.println(entry.getKey());
                                // Get the members map
                                if (entry.getKey().equals("members")) {
                                    // Store the value in a seperate map
                                    Map<String, Object> membersMap = (Map<String, Object>) entry.getValue();
                                    //System.out.println(membersMap);
                                    //Log.d("TAG", "These are members");
                                    // loop through the members map to get the indivdual members
                                    for (Map.Entry<String, Object> member : membersMap.entrySet()) {
                                        //Log.d("TAG", "Memberinfo");
                                        //.out.println(member);
                                        //System.out.println(member.getValue());
                                        //System.out.println(member.getValue().getClass());
                                        Map<String, Object> memberAttributes = (Map<String, Object>) member.getValue();
                                        //System.out.println(memberAttributes.get("points").getClass());
                                        String name = (String) memberAttributes.get("name");
                                        //int points = (int) memberAttributes.get("points");
                                        String email = (String) memberAttributes.get("email");
                                        String photoUrl = (String) memberAttributes.get("photoUrl");
                                        items.add(new Member(name,email,R.drawable.a, 10));
                                        System.out.println(items);

                                    }
                                }
                                memberAdapter.notifyDataSetChanged();
                            }
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });






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