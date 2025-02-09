package com.example.firebase_app.notifications;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationsService extends FirebaseMessagingService {

    public static final String TAG = "NotificationsService";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.i(TAG, "New firebase token: " + token);
        // зберегти токен на сервері асоційованим з користувачем
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.i(TAG, "Message received: " + message.getData());
    }
}
