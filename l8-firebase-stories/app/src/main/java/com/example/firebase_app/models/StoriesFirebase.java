package com.example.firebase_app.models;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

public class StoriesFirebase extends Stories {
    public static final String TAG = "StoriesFirebase";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collection = db.collection("stories");

    public StoriesFirebase() {
        populateList();
    }

    public void addStory(Story s) {
        collection.add(s).addOnCompleteListener(r -> {
            Log.i(TAG, "Added a story");
        }).addOnFailureListener(err -> {
            Log.w(TAG, "Failed to add story", err);
        });
    }

    protected void populateList() {
        collection.addSnapshotListener((snapshot, err) -> {
            if (err != null) {
                Log.w(TAG, "Failed to get list", err);
                return ;
            }

            List<Story> list = stories.getValue();
            list.clear();
            list.addAll(
                snapshot.getDocuments().stream().map(d -> d.toObject(Story.class)).collect(Collectors.toList())
            );

            stories.postValue(list);

            Log.i(TAG, "Obtained " + list.size() + " stories");
        });
    }
}
