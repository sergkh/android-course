package com.example.list_details;

import android.app.Application;

import com.example.list_details.db.Database;

public class NotesApp extends Application {
    public static Database db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = Database.getInstance(this);
    }
}
