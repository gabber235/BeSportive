package nl.tue.besportive;

import android.os.Bundle;
<<<<<<< HEAD
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
=======
>>>>>>> main

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import nl.tue.besportive.databinding.ActivityCreateGroupBinding;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateGroupBinding binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);

        CreateGroupViewModel viewModel = new ViewModelProvider(this).get(CreateGroupViewModel.class);
        binding.setViewModel(viewModel);

        setContentView(binding.getRoot());
<<<<<<< HEAD

        binding.createGroupButton.setOnClickListener(this::createGroup);

        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(CreateGroupActivity.this, JoinCreateGroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
=======
>>>>>>> main
    }
}