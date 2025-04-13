package com.example.practice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practice.databinding.FragmentItemInfoBinding;
import com.example.practice.models.SubTasksListAdapter;
import com.example.practice.models.Task;
import com.example.practice.models.TasksViewModel;

public class ItemInfoFragment extends Fragment {
    private final String TAG = "ItemInfoFragment";

    private FragmentItemInfoBinding binding;
    private TasksViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemInfoBinding.inflate(getLayoutInflater(), container, false);

        viewModel = new ViewModelProvider(getActivity()).get(TasksViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Task task = viewModel.findTask(getArguments().getString("itemId"));

        // ініціалізація компонентів екрану з об'єкту Task
        Log.i(TAG, "Task subtasks " + task.getSubtasks().size());

        binding.subTasksList.setAdapter(new SubTasksListAdapter(task.getSubtasks()));
    }
}