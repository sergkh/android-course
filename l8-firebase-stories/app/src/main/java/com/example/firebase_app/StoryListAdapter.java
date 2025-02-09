package com.example.firebase_app;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_app.databinding.StoryLayoutBinding;
import com.example.firebase_app.models.Story;

import java.util.List;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.StoryViewHolder> {

    private final List<Story> stories;

    public StoryListAdapter(List<Story> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(
            StoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        holder.showStory(stories.get(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        private StoryLayoutBinding binding;

        public StoryViewHolder(@NonNull StoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void showStory(Story s) {
            if (s.getImageUrl() != null) Glide.with(binding.getRoot()).load(Uri.parse(s.getImageUrl())).into(binding.imgStory);
            binding.tvAuthor.setText(s.getAuthor());
            binding.tvTitle.setText(s.getTitle());
        }
    }
}
