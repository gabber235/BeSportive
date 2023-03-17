package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            activityIntent = new Intent(this, OnboardingActivity.class);
        } else {
            activityIntent = new Intent(this, JoinCreateGroupActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}