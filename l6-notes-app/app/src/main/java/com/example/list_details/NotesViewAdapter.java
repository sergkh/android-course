package com.example.list_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.list_details.databinding.ListItemBinding;
import com.example.list_details.models.Note;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class NotesViewAdapter extends RecyclerView.Adapter<NotesViewAdapter.ViewHolder> {

    private List<Note> notes;
    private Consumer<Note> onClick;

    public NotesViewAdapter(List<Note> notes, Consumer<Note> onClick) {
        this.notes = notes;
        this.onClick = onClick;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note n = notes.get(position);
        holder.show(n);
        holder.itemView.setOnClickListener(v -> this.onClick.accept(n));
    }

    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ListItemBinding binding;
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM uuuu");

        ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void show(Note n) {
            this.binding.tvTitle.setText(n.getTitle());

            ZonedDateTime dt = Instant.ofEpochMilli(n.getCreated()).atZone(ZoneOffset.systemDefault());

            this.binding.tvCreated.setText(dt.format(format));
        }
    }
}
