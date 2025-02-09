package com.example.firebase_app.models;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Stories extends ViewModel {

    protected List<Story> stories = new ArrayList<>();

    public Stories() {
        populateList();
    }

    public List<Story> getStories() {
        return stories;
    }

    public void addStory(Story s) {
        stories.add(s);
    }

    protected void populateList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        stories.addAll(List.of(
            new Story(
                    "Park walk",
                    "Me",
                    "https://ironwood-court.com/wp-content/uploads/2018/04/park.jpg"
            ),
            new Story(
                    "Autumn vibes",
                    "Me",
                    "https://w0.peakpx.com/wallpaper/890/138/HD-wallpaper-autumn-vibes-fall-vibes.jpg"
            ),
            new Story(
                    "Unstoppable",
                    "Me",
                    "https://highlandcanine.com/wp-content/uploads/2021/01/vizsla-running.jpg"
            )
        ));

    }
}
