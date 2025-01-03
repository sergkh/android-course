package com.example.lection;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lection.databinding.ItemLayoutBinding;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListHolder> {

    static class ListHolder extends RecyclerView.ViewHolder {
        final ItemLayoutBinding binding;

        public ListHolder(ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private List<ListItem> items;

    public Adapter(List<ListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        var item = items.get(position);
        holder.binding.title.setText(item.getName());
        holder.binding.subtext.setText(String.valueOf(item.getSize()));
        holder.binding.checkBox.setChecked(item.isSelected());

        holder.binding.checkBox.setOnClickListener(v -> {
            item.setSelected(holder.binding.checkBox.isChecked());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
};