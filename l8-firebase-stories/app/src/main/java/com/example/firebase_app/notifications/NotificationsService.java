package com.example.firebase_app.notifications;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NotificationsService extends FirebaseMessagingService {

    public static final String TAG = "NotificationsService";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.i(TAG, "New firebase token: " + token);

        // Збережемо токен для повідомлень в Firestore
        CollectionReference collection = FirebaseFirestore.getInstance().collection("subscribers");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            collection.document(user.getUid()).set(
                Map.of("user", user.getDisplayName(), "token", token)
            );
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.i(TAG, "Message received: " + message.getData());
    }
}
