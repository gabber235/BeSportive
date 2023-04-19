package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityInviteMembersBinding;
import nl.tue.besportive.models.InviteMembersViewModel;
import nl.tue.besportive.utils.BarUtils;

public class InviteMembersActivity extends AppCompatActivity {
    private ActivityInviteMembersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInviteMembersBinding.inflate(getLayoutInflater());

        // initialize variables here
        boolean inCreateGroupFlow = getIntent().getBooleanExtra("inCreateGroupFlow", false);

        InviteMembersViewModel viewModel = new ViewModelProvider(this,
                new InviteMembersViewModel.InviteMembersViewModelFactory(inCreateGroupFlow)).get(InviteMembersViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());

        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarInvite.toolbar));
    }
}
