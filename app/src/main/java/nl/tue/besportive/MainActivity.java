package nl.tue.besportive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.functions.FirebaseFunctions;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestoreSettings developSettings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .setHost("10.0.2.2:8080")
            .setSslEnabled(false)
            .build();

    FirebaseFirestoreSettings productionSettings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            FirebaseFunctions.getInstance().useEmulator("10.0.2.2", 5001);
            FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099);
            FirebaseFirestore.getInstance().setFirestoreSettings(developSettings);
        } else {
            FirebaseFirestore.getInstance().setFirestoreSettings(productionSettings);
        }

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