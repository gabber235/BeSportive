package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.adapters.MemberOverviewAdapter;
import nl.tue.besportive.databinding.ActivityMemberOverviewBinding;
import nl.tue.besportive.models.MemberOverviewViewModel;
import nl.tue.besportive.utils.BarUtils;

public class MemberOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMemberOverviewBinding binding = ActivityMemberOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        MemberOverviewViewModel viewModel = new ViewModelProvider(this, new MemberOverviewViewModel.MemberOverviewViewModelFactory(userId)).get(MemberOverviewViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        MemberOverviewAdapter adapter = new MemberOverviewAdapter(viewModel);
        binding.completedChallenges.setAdapter(adapter);

        viewModel.getFeedItems().observe(this, adapter::setItems);


        setSupportActionBar(BarUtils.setupBackToolbar(binding.toolbarMemberOverview.toolbar));
    }
}