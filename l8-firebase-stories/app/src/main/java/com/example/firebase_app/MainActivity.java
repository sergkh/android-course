package com.example.firebase_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.firebase_app.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> signInLauncher = createSignInLauncher();
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    private final ActivityResultLauncher<String> notificationsPermissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                Toast.makeText(
                        this,
                        "Some functionality won't work without notifications",
                        Toast.LENGTH_LONG)
                    .show();
            }
        });

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
        setSupportActionBar(binding.toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.storiesFragment).build();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        binding.createStoryButton.setOnClickListener(v -> {
            Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_storiesFragment_to_createStoryFragment);
        });

        requestNotificationsPermission();

        fetchFcmToken();
    }

    @NonNull
    private void fetchFcmToken() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    Log.d(TAG, "Token " + task.getResult());

                    // Збережемо токен для повідомлень в Firestore
                    CollectionReference collection = FirebaseFirestore.getInstance().collection("subscribers");

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        collection.document(user.getUid()).set(
                            Map.of("user", user.getDisplayName(), "token", task.getResult())
                        );
                    }
                }
            });
    }

    private void requestNotificationsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Notifications posting allowed");
        } else {
            notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
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

            binding.toolbar.setTitle("");
        } else {
            initUser();
        }
    }

    private void initUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.toolbar.setTitle(user.getDisplayName());
    }
}