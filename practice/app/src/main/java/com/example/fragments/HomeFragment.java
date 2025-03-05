package com.example.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragments.databinding.FragmentHomeBinding;
import com.example.fragments.models.Task;
import com.example.fragments.models.TasksListAdapter;
import com.example.fragments.models.TasksViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TasksListAdapter listAdapter;
    private TasksViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        viewModel = new ViewModelProvider(getActivity()).get(TasksViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listAdapter = new TasksListAdapter(viewModel.getTasks());

        binding.fbCreate.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_createItemFragment);
        });

        binding.tasksList.setAdapter(listAdapter);

//        binding.btnTemp.setOnClickListener(v -> {
//            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_itemInfoFragment);
//        });
    }
}