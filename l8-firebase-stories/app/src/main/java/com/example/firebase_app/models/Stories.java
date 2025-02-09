package com.example.firebase_app.models;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Stories extends ViewModel {

    protected MutableLiveData<List<Story>> stories = new MutableLiveData(new ArrayList<>());

    public Stories() {}

    public LiveData<List<Story>> getStories() {
        return stories;
    }

    public void addStory(Story s) {
        List<Story> list = stories.getValue();
        list.add(s);
        stories.postValue(list);
    }
}
