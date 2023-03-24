package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityInviteMembersBinding;

public class InviteMembersActivity extends AppCompatActivity {
    private ActivityInviteMembersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInviteMembersBinding.inflate(getLayoutInflater());

        InviteMembersViewModel viewModel = new ViewModelProvider(this).get(InviteMembersViewModel.class);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());

        binding.doneButton.setOnClickListener(this::feed);
    }
}