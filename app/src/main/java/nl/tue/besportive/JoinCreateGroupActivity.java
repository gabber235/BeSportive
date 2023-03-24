package nl.tue.besportive;

import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import androidx.appcompat.widget.Toolbar;
=======
>>>>>>> main

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityJoinCreateGroupBinding;

public class JoinCreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJoinCreateGroupBinding binding = ActivityJoinCreateGroupBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        JoinCreateGroupViewModel viewModel = new ViewModelProvider(this).get(JoinCreateGroupViewModel.class);
        binding.setViewModel(viewModel);

        setContentView(binding.getRoot());
<<<<<<< HEAD

        binding.createGroupButton.setOnClickListener(this::createGroup);
        binding.joinButton.setOnClickListener(this::joinGroup);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle("MyTitle");
    }

    public void createGroup(View view) {
        startCreateGroupActivity();
    }

    public void joinGroup(View view) {
        startFeedActivity();
    }

    public void startCreateGroupActivity() {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
        finish();
    }

    public void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
=======
>>>>>>> main
    }
}