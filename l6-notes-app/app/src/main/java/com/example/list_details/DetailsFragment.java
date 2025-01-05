package com.example.list_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.list_details.databinding.FragmentDetailsBinding;
import com.example.list_details.db.Database;
import com.example.list_details.models.Note;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    private ExecutorService executor = Executors.newCachedThreadPool();

    private Note note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        long id = getArguments().getLong("id");
        loadNoteAsync(id);

        binding.editTitle.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {
                if (note != null) {
                    note.setTitle(s.toString());
                    saveAsync();
                }
            }
        });

        binding.editNoteContent.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {
                if (note != null) {
                    note.setContent(s.toString());
                    saveAsync();
                }
            }
        });

        return binding.getRoot();
    }

    void loadNoteAsync(long id) {
        Handler h = new Handler(Looper.getMainLooper());

        executor.submit(() -> {
            note = Database.getInstance(getContext().getApplicationContext()).getNotesDao().get(id);
            h.post(() -> applyNote(note));
        });
    }

    void saveAsync() {
        executor.submit(() -> {
            Database.getInstance(getContext().getApplicationContext()).getNotesDao().update(note);
        });
    }

    void applyNote(Note n) {
        binding.editTitle.setText(n.getTitle());
        binding.editNoteContent.setText(n.getContent());
    }
}