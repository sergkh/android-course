package com.example.list_details;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.list_details.databinding.ListItemBinding;
import com.example.list_details.models.Book;

import java.util.List;

public class BookViewAdapter extends RecyclerView.Adapter<BookViewAdapter.ViewHolder> {

    private List<Book> books;

    public BookViewAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book b = books.get(position);
        holder.author.setText(b.getAuthor());
        holder.title.setText(b.getName());
    }

    @Override
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
