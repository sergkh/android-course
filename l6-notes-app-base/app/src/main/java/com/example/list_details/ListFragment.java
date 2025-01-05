package com.example.list_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.list_details.databinding.FragmentListBinding;
import com.example.list_details.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private NotesViewAdapter adapter;
    private ExecutorService executor = Executors.newCachedThreadPool();

    private List<Note> notes = new ArrayList<>(List.of(
            new Note("First note", "")
    ));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NotesViewAdapter(notes, note -> {
            Navigation.findNavController(view).navigate(R.id.action_openDetails);
        });

        binding.rvBooks.setAdapter(adapter);

        binding.btnAddNote.setOnClickListener(v -> {
            addNote(new Note("New note", ""));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void addNote(Note n) {
        notes.add(n);
        adapter.notifyItemInserted(-1);
    }
}