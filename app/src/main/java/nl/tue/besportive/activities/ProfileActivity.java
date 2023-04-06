package nl.tue.besportive.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nl.tue.besportive.databinding.ActivityProfileBinding;
import nl.tue.besportive.models.ProfilePageViewModel;
import nl.tue.besportive.utils.BarUtils;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set viewModel
        ProfilePageViewModel viewModel = new ViewModelProvider(this).get(ProfilePageViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // using toolbar as ActionBar
        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarProfilePage.toolbar));
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

    private String generateAvatarUrl() {
        String baseUrl = "https://api.multiavatar.com/";
        UUID uuid = UUID.randomUUID();
        String generatedChars = uuid.toString().replace("-", "");
        return baseUrl + generatedChars + ".png";
    }
}