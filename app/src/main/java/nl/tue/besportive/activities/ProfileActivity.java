package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nl.tue.besportive.R;
import nl.tue.besportive.databinding.ActivityProfileBinding;
import nl.tue.besportive.models.ProfilePageViewModel;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private ProfilePageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set viewModel
        viewModel = new ViewModelProvider(this).get(ProfilePageViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile_page);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Generate Picture
        binding.generateImageButton.setOnClickListener(this::generateNewPhoto);
    }

    private void generateNewPhoto(View view) {
        String newPhotoUrl = generateAvatarUrl();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(uid);

        Map<String, Object> updates = new HashMap<>();
        updates.put("photoUrl", newPhotoUrl);
        userRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ProfileActivity", "PhotoUrl updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ProfileActivity", "Error updating photoUrl", e);
                    }
                });
    }

    // This creates a switch case in order to go back to the feed page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private String generateAvatarUrl() {
        String baseUrl = "https://api.multiavatar.com/";
        UUID uuid = UUID.randomUUID();
        String generatedChars = uuid.toString().replace("-", "");
        return baseUrl + generatedChars + ".png";
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