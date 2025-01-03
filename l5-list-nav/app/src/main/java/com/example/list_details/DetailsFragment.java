package com.example.list_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.list_details.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        binding.tvDetailsAuthor.setText(getArguments().getString("author"));
        binding.tvDetailsTitle.setText(getArguments().getString("title"));
        binding.tvDetails.setText(getArguments().getString("details"));

        return binding.getRoot();
    }
}