package com.example.fragments.models;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.R;
import com.example.fragments.databinding.TaskLayoutBinding;

import java.util.List;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TaskViewHolder> {
    private List<Task> tasks;

    public TasksListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
            TaskLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.setTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TaskLayoutBinding binding;

        public TaskViewHolder(@NonNull TaskLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setTask(Task t) {
            binding.taskName.setText(t.getTitle());

            binding.tvSubtasks.setText(
                t.getSubtasks().isEmpty() ? "" : "" + t.countCompletedSubtasks() + "/" + t.getSubtasks().size()
            );

            binding.btnTaskDetails.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putString("taskId", t.getId());

                Navigation.findNavController(binding.getRoot()).navigate(
                    R.id.action_homeFragment_to_itemInfoFragment, args
                );
            });

            binding.btnTaskStatus.setActivated(t.isCompleted());

            // натиснення на назву задачі чи на кнопку статусу змінює його статус
            binding.btnTaskStatus.setOnClickListener(v -> toggleTaskStatus(t));
            binding.taskName.setOnClickListener(v -> toggleTaskStatus(t));
        }

        private void toggleTaskStatus(Task t) {
            t.setCompleted(!t.isCompleted());
            binding.btnTaskStatus.setActivated(t.isCompleted());
        }
    }
}
