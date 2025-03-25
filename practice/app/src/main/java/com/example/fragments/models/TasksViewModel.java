package com.example.fragments.models;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TasksViewModel extends ViewModel {

    private List<Task> tasks = new ArrayList<>();

    public TasksViewModel() {
        Log.i("T", "View model created:");

        // only for test
        tasks.add(
            new Task(
                UUID.randomUUID().toString(),
                "First task",
                "Some description",
                false,
                List.of(new Subtask("First subtask", false), new Subtask("Second subtask", false)),
                null,
                null)
        );

        tasks.add(
            new Task(
                UUID.randomUUID().toString(),
                "Second task",
                "Some description",
                false,
                List.of(new Subtask("First subtask", false), new Subtask("Second subtask", false)),
                null,
                null)
        );

    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task findTask(String taskId) {
        return tasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .get();
    }
}
