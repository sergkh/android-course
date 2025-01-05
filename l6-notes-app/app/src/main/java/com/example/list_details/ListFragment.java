package com.example.list_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.list_details.databinding.FragmentListBinding;
import com.example.list_details.db.Database;
import com.example.list_details.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private Database db;
    private NotesViewAdapter adapter;
    private ExecutorService executor = Executors.newCachedThreadPool();

    private List<Note> notes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        db = Database.getInstance(getContext().getApplicationContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NotesViewAdapter(notes, note -> {
            Bundle args = new Bundle();
            args.putLong("id", note.getId());
            Navigation.findNavController(view).navigate(R.id.action_openDetails, args);
        });

        binding.rvBooks.setAdapter(adapter);

        binding.btnAddNote.setOnClickListener(v -> {
            addNoteAsync(new Note("New note", ""));
        });

        loadNotesAsync();
    }

    private void loadNotesAsync() {
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.submit(() -> {
            notes.clear();
            notes.addAll(db.getNotesDao().all());

            Log.i("ListFragment", "Loaded " + notes.size()  +" notes");

            handler.post(() -> adapter.notifyDataSetChanged());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void addNoteAsync(Note n) {
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.submit(() -> {
            long id = db.getNotesDao().add(n);
            n.setId(id);

            handler.post(() -> {
                notes.add(n);
                adapter.notifyItemInserted(-1);
            });
        });
    }
}