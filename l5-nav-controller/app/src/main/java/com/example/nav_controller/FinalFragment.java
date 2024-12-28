package com.example.nav_controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_final, container, false);

        ((Button) view.findViewById(R.id.btn_final)).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_finalFragment_to_mainFragment);
        });

        return view;
    }
}