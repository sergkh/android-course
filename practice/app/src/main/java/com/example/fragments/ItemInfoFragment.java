package com.example.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragments.databinding.FragmentCreateItemBinding;
import com.example.fragments.databinding.FragmentItemInfoBinding;

public class ItemInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentItemInfoBinding binding = FragmentItemInfoBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
}