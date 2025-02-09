package com.example.list_details.models;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.list_details.NotesApp;
import com.example.list_details.db.Database;

import java.util.List;

public class NotesViewModel extends ViewModel  {

    private LiveData<List<Note>> notes;

    public NotesViewModel() {
        this.notes = NotesApp.db.getNotesDao().all();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void addNote(Note n) {
        NotesApp.db.getNotesDao().add(n);
    }
}
