package nl.tue.besportive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import nl.tue.besportive.R;
import nl.tue.besportive.databinding.ActivityOnboardingBinding;
import nl.tue.besportive.utils.Navigator;

public class OnboardingActivity extends AppCompatActivity {
    private ActivityOnboardingBinding binding;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startButton.setOnClickListener(v -> startAuthenticationFlow());
    }

    /**
     * Starts the FirebaseUI authentication flow for email and Google sign in.
     */
    public void startAuthenticationFlow() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_BeSportive)
                .setLogo(R.drawable.login_cartoon)
                .build();
        signInLauncher.launch(signInIntent);
    }

    /**
     * Handles the result of the FirebaseUI authentication flow.
     *
     * @param result The result of the authentication flow.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        assert response != null;
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            Toast.makeText(this, "Successfully Signed in", Toast.LENGTH_SHORT).show();
            Navigator.navigateToStartingPage(this);
        } else {
            Log.e("OnboardingActivity", "Sign in failed: " + response.getError());
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
        }
    }
}
