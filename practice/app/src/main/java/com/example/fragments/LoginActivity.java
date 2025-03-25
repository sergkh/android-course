package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fragments.databinding.ActivityLoginBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                IdpResponse response = result.getIdpResponse();
                if (result.getResultCode() == RESULT_OK) {
                    Log.i("MainActivity", "User logged in");
                    gotoMainScreen();
                } else {
                    Log.w("MainActivity", "Sign in failed: " + response.getError().getErrorCode());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            gotoMainScreen();
        }

        binding.btnLoginGoogle.setOnClickListener(v -> {
            startSignIn(new AuthUI.IdpConfig.GoogleBuilder().build());
        });

        binding.btnLoginWithEmail.setOnClickListener(v -> {
            startSignIn(new AuthUI.IdpConfig.EmailBuilder().build());
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startSignIn(AuthUI.IdpConfig provider) {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(provider))
                .build();

        signInLauncher.launch(signInIntent);
    }

    private void gotoMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }
}