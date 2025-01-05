package com.example.list_details.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.list_details.models.Note;

@androidx.room.Database(entities = {Note.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract NotesDao getNotesDao();

    public static Database getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx, Database.class, "notes").build();
        }
        return instance;
    }
}
