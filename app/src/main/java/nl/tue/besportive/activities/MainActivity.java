package nl.tue.besportive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.FirebaseStorage;

import nl.tue.besportive.BuildConfig;
import nl.tue.besportive.R;
import nl.tue.besportive.utils.Navigator;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestoreSettings developSettings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .setHost("10.0.2.2:8080")
            .setSslEnabled(false)
            .build();

    private FirebaseFirestoreSettings productionSettings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            FirebaseFunctions.getInstance().useEmulator("10.0.2.2", 5001);
            FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099);
            FirebaseStorage.getInstance().useEmulator("10.0.2.2", 9199);
            FirebaseFirestore.getInstance().setFirestoreSettings(developSettings);
        } else {
            FirebaseFirestore.getInstance().setFirestoreSettings(productionSettings);
        }

        Navigator.navigateToStartingPage(this);
    }
}
