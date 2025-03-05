package com.example.fragments.models;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.R;
import com.example.fragments.databinding.SubTaskLayoutBinding;
import com.example.fragments.databinding.TaskLayoutBinding;

import java.util.List;

public class SubTasksListAdapter extends RecyclerView.Adapter<SubTasksListAdapter.SubTaskViewHolder> {
    private List<Subtask> subtasks;

    public SubTasksListAdapter(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubTaskViewHolder(
            SubTaskLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {
        holder.setTask(subtasks.get(position));
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    static class SubTaskViewHolder extends RecyclerView.ViewHolder {
        private SubTaskLayoutBinding binding;

        public SubTaskViewHolder(@NonNull SubTaskLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setTask(Subtask t) {
            binding.subtaskName.setText(t.getTitle());

            binding.btnSubtaskStatus.setSelected(t.isCompleted());
        }
    }
}
