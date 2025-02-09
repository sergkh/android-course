package com.example.firebase_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase_app.databinding.FragmentStoriesBinding;
import com.example.firebase_app.models.Stories;
import com.example.firebase_app.models.StoriesFirebase;


public class StoriesFragment extends Fragment {

    public StoriesFragment() {}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentStoriesBinding binding = FragmentStoriesBinding.inflate(getLayoutInflater(), container, false);

        Stories stories = new ViewModelProvider(getActivity()).get(StoriesFirebase.class);

        final StoryListAdapter adapter = new StoryListAdapter(stories.getStories().getValue());

        stories.getStories().observe(getActivity(), list -> {
            adapter.notifyDataSetChanged();
        });

        binding.storiesList.setAdapter(adapter);

        return binding.getRoot();
    }
}