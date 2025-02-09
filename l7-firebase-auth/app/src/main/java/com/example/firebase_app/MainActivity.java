package com.example.firebase_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.firebase_app.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> signInLauncher = createSignInLauncher();
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
        new AuthUI.IdpConfig.EmailBuilder().build(),
        new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startSignIn();

        binding.btnLogout.setOnClickListener(v -> {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(r -> {
                    startSignIn();
                });
        });
    }

    private ActivityResultLauncher<Intent> createSignInLauncher() {
        return registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> {
                    IdpResponse response = result.getIdpResponse();
                    if (result.getResultCode() == RESULT_OK) {
                        Log.i(TAG, "User logged in");
                        initUser();
                    } else {
                        Log.w(TAG, "Sign in failed: " + response.getError().getErrorCode());
                    }
                }
        );

    }

    private void startSignIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();

            signInLauncher.launch(signInIntent);

            binding.tvUser.setText("");
            binding.ivAvatar.setImageURI(null);
        } else {
            initUser();
        }
    }

    private void initUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.tvUser.setText(user.getDisplayName());
        Glide.with(this).load(user.getPhotoUrl()).into(binding.ivAvatar);
    }
}