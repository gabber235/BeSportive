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

import java.util.Objects;

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

        checkIfUserIsStillLoggedIn();
    }

    private void checkIfUserIsStillLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Navigator.navigateToOnboardingActivity(this, true);
            return;
        }

        user.reload().addOnCompleteListener(this::onUserReloaded);
    }

    private void onUserReloaded(Task<Void> task) {
        if (task.isSuccessful()) {
            checkIfUserHasGroup(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()));
        } else {
            Navigator.navigateToOnboardingActivity(this, true);
        }
    }

    private void checkIfUserHasGroup(FirebaseUser user) {
        FirebaseFirestore.getInstance().collection("groups")
                .whereNotEqualTo("members." + user.getUid(), null)
                .get()
                .addOnCompleteListener(this::onGroupQueryComplete);
    }

    private void onGroupQueryComplete(Task<QuerySnapshot> task) {
        if (task.isSuccessful() && !task.getResult().isEmpty()) {
            Navigator.navigateToFeedActivity(this, true);
        } else {
            Navigator.navigateToJoinCreateGroupActivity(this, true);
        }
    }
}