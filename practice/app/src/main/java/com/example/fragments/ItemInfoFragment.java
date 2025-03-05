package com.example.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragments.databinding.FragmentCreateItemBinding;
import com.example.fragments.databinding.FragmentItemInfoBinding;
import com.example.fragments.models.SubTasksListAdapter;
import com.example.fragments.models.Task;
import com.example.fragments.models.TasksViewModel;

public class ItemInfoFragment extends Fragment {

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
        Task task = viewModel.findTask(getArguments().getString("taskId"));

        binding.subTasksList.setAdapter(new SubTasksListAdapter(task.getSubtasks()));
    }
}