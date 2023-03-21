package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityCreateGroupBinding;

public class CreateGroupActivity extends AppCompatActivity {
    private ActivityCreateGroupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        binding.setModel(new CreateGroupModel());
        setContentView(binding.getRoot());
    }


    public void startConfigureChallengesActivity() {
        Intent intent = new Intent(this, ConfigureChallengesActivity.class);
        startActivity(intent);
        finish();
    }

}