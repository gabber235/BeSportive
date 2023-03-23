package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityProfileBinding;
import nl.tue.besportive.models.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        
        setContentView(binding.getRoot());
//returnButton?????
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

}




