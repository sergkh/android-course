package com.example.list_details;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.list_details.databinding.ListItemBinding;
import com.example.list_details.models.Book;

import java.util.List;
import java.util.function.Function;

public class BookViewAdapter extends RecyclerView.Adapter<BookViewAdapter.ViewHolder> {

    private List<Book> books;
    private Function<Integer, Integer> onClick;

    public BookViewAdapter(List<Book> books, Function<Integer, Integer> onClick) {
        this.books = books;
        this.onClick = onClick;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book b = books.get(position);
        holder.author.setText(b.getAuthor());
        holder.title.setText(b.getName());
        holder.itemView.setOnClickListener(v -> this.onClick.apply(position));
    }

    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView author;
        final TextView title;

        ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            author = binding.tvAuthor;
            title = binding.tvTitle;
        }
    }
}
