package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityMemberOverviewBinding;

public class MemberOverviewActivity extends AppCompatActivity {
    private ActivityMemberOverviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}