package com.example.practice.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TasksViewModel extends ViewModel {

    public static final String TAG = "TasksViewModel";
    private MutableLiveData<List<Task>> tasks = new MutableLiveData(new ArrayList<>());

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collection;

    public TasksViewModel() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        collection = db.collection("users/" + userId + "/tasks");
        load();
    }

    private void load() {
        collection.get().addOnSuccessListener(snapshot -> {
            List<Task> remoteTasks = snapshot.getDocuments()
                    .stream()
                    .map(d -> d.toObject(Task.class))
                    .collect(Collectors.toList());

           tasks.setValue(remoteTasks);
        });
    }

    public void addTask(Task t) {
        collection.add(t).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "Task created");
                this.load();
            } else {
                Log.w(TAG, "Failed to create task", task.getException());
            }
        });
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public Task findTask(String taskId) {
        return tasks.getValue().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .get();
    }
}
