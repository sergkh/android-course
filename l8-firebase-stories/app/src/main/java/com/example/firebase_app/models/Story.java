package com.example.firebase_app.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

public class Story {
    private String title;
    private String author;
    private String imageUrl;

    public Story() {
        this(null, null, null);
    }

    public Story(String title, String author, String imageUrl) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

    @Override
    public String toString() {
        return "Story{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
