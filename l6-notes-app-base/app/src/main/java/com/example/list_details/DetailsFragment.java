package com.example.list_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.list_details.databinding.FragmentDetailsBinding;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        binding.editTitle.setText("Note title");
        binding.editNoteContent.setText("Note text");

        return binding.getRoot();
    }
}