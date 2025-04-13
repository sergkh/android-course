package com.example.practice.models;

public class Subtask {
    private String title;
    private boolean completed;

    public Subtask(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public Subtask() {
    }

    public Subtask(boolean completed, String title) {
        this.completed = completed;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
