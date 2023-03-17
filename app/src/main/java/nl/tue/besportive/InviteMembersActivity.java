package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nl.tue.besportive.databinding.ActivityInviteMembersBinding;

public class InviteMembersActivity extends AppCompatActivity {
    private ActivityInviteMembersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInviteMembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}