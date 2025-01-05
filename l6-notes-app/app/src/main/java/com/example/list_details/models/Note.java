package com.example.list_details.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String content;
    private long created;

    public Note(String title, String content, long created) {
        this.title = title;
        this.content = content;
        this.created = created;
    }

    @Ignore
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.created = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
