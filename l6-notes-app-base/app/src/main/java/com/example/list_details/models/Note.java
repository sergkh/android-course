package com.example.list_details.models;

public class Note {
    private String title;
    private String content;
    private long created;

    public Note(String title, String content, long created) {
        this.title = title;
        this.content = content;
        this.created = created;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.created = System.currentTimeMillis();
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
