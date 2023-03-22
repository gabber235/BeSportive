package nl.tue.besportive;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
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
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            FirebaseFunctions.getInstance().useEmulator("10.0.2.2", 5001);
            FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099);
            FirebaseFirestore.getInstance().setFirestoreSettings(developSettings);
        } else {
            FirebaseFirestore.getInstance().setFirestoreSettings(productionSettings);
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Navigator.navigateToOnboardingActivity(this);
            finish();
            return;
        }

        FirebaseFirestore.getInstance().collection("groups")
                .whereNotEqualTo("members." + user.getUid(), null)
                .get()
                .addOnCompleteListener(this::onGroupQueryComplete);
    }

    private void onGroupQueryComplete(Task<QuerySnapshot> task) {
        if (task.isSuccessful() && !task.getResult().isEmpty()) {
            Navigator.navigateToFeedActivity(this);
        } else {
            Navigator.navigateToJoinCreateGroupActivity(this);
        }
        finish();
    }
}