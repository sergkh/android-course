package com.example.lection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ListHolder extends RecyclerView.ViewHolder {
    public ListHolder(@NonNull View itemView) {
        super(itemView);
    }
}

public class Adapter extends RecyclerView.Adapter<ListHolder> {
    private List<ListItem> items;

    public Adapter(List<ListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        var item = items.get(position);
        ((TextView) holder.itemView.findViewById(R.id.title)).setText(item.getName());
        ((TextView) holder.itemView.findViewById(R.id.subtext)).setText(String.valueOf(item.getSize()));
        ((CheckBox) holder.itemView.findViewById(R.id.checkBox)).setChecked(item.isSelected());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
};