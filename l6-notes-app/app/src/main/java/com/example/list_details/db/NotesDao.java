package com.example.list_details.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.list_details.models.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert()
    long add(Note n);

    @Update
    void update(Note n);

    @Delete
    void delete(Note n);

    @Query("SELECT * FROM note where id=:id")
    Note get(long id);

    @Query("SELECT * FROM note")
    List<Note> all();
}
