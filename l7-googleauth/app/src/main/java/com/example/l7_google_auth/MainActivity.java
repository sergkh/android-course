package com.example.l7_google_auth;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.CreatePublicKeyCredentialRequest;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CredentialOption;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.l7_google_auth.databinding.ActivityMainBinding;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignin.setOnClickListener(v -> {
            startAuth();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startAuth() {
        CredentialManager manager = CredentialManager.create(this);

        if (getString(R.string.google_client_id).isEmpty()) {
            throw new RuntimeException("Set up the google_client_id resource string");
        }

        CredentialOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(
                        getString(R.string.google_client_id)
                )
                .setAutoSelectEnabled(true)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .setPreferImmediatelyAvailableCredentials(false)
                .build();

        CredentialManagerCallback<GetCredentialResponse, GetCredentialException> callback = new CredentialManagerCallback<>() {
            @Override
            public void onResult(GetCredentialResponse resp) {
                Log.i(TAG, "Obtained credential: " +  resp.getCredential());

                if (resp.getCredential().getType() == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    GoogleIdTokenCredential cred = GoogleIdTokenCredential.createFrom(resp.getCredential().getData());

                    Log.i(TAG, "Google ID token: " +  cred.getIdToken());
                }

                Toast.makeText(MainActivity.this, "Authenticated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull GetCredentialException e) {
                Log.w(TAG, "Failed to authenticate: " + e.getMessage());
                Toast.makeText(MainActivity.this, "Failed to authenticate", Toast.LENGTH_SHORT).show();
            }
        };

        manager.getCredentialAsync(this, request, new CancellationSignal(), ContextCompat.getMainExecutor(this), callback);
    }
}