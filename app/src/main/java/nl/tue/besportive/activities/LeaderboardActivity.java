package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.MemberAdapter;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.databinding.ActivityLeaderboardBinding;
import nl.tue.besportive.models.LeaderboardViewModel;
import nl.tue.besportive.utils.BarUtils;
import nl.tue.besportive.utils.ItemClickSupport;

public class LeaderboardActivity extends AppCompatActivity {
    public Map<String, Member> membersList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nl.tue.besportive.databinding.ActivityLeaderboardBinding binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupToolbar(binding.toolbarFeed.feedToolbar));
        BarUtils.setupBottomNavigation(this, binding.bottomNavigation, R.id.leaderboard);

        List<Member> items = new ArrayList<>();
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
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return BarUtils.selectToolbarMenuItem(this, item);
    }
}