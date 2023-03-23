package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private nl.tue.besportive.databinding.ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = nl.tue.besportive.databinding.ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.returnButton.setOnClickListener(this::feed);

    }

    public void feed(View view) {
        startFeedActivity();
    }
//    I used public and you used private .....???
    public void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }

    private void feed(View view) {
        startFeedActivity();
    }
    private void createJoinGroup(View view) {
        startCreateJoinGroupActivity();
    }
    private void startFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
    private void startCreateJoinGroupActivity() {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
        finish();
    }

}




