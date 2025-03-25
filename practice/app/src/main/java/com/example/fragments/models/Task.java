package com.example.fragments.models;

import android.location.Location;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.time.LocalDate;
import java.util.List;

public class Task extends BaseObservable {
    private String id;
    private String title;
    private String description;
    private boolean completed;
    private List<Subtask> subtasks;
    private LocalDate reminder;
    private String remindAt;

    public Task(String id, String title, String description, boolean completed, List<Subtask> subtasks, LocalDate reminder, String remindAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.subtasks = subtasks;
        this.reminder = reminder;
        this.remindAt = remindAt;
    }

    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Bindable
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Bindable
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getReminder() {
        return reminder;
    }

    public void setReminder(LocalDate reminder) {
        this.reminder = reminder;
    }

    public String getRemindAt() {
        return remindAt;
    }

    public void setRemindAt(String remindAt) {
        this.remindAt = remindAt;
    }

    @Bindable
    public int getCompletedSubtasksCount() {
        return (int) subtasks.stream().filter(t -> t.isCompleted()).count();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", subtasks=" + subtasks +
                ", reminder=" + reminder +
                ", remindAt='" + remindAt + '\'' +
                '}';
    }
}
