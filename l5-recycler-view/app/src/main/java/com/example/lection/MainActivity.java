package com.example.lection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



class ListHolder extends RecyclerView.ViewHolder {
    public ListHolder(@NonNull View itemView) {
        super(itemView);
    }
}

public class MainActivity extends AppCompatActivity {
    public static record ListItem(String name, int size, boolean selected) {}

    private List<ListItem> items = new ArrayList<>(List.of(
            new ListItem("First", 10, false),
            new ListItem("Second", 10, false),
            new ListItem("Third", 10, true)
    ));

    private RecyclerView.Adapter<ListHolder> adapter = new RecyclerView.Adapter<ListHolder>() {
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
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(item.name);
            ((TextView) holder.itemView.findViewById(R.id.subtext)).setText(String.valueOf(item.size));
            ((CheckBox) holder.itemView.findViewById(R.id.checkBox)).setChecked(item.selected);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView view = findViewById(R.id.recyclerView);

        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));

        var fab = ((FloatingActionButton) findViewById(R.id.floatingActionButton));
        fab.setOnClickListener(evt -> {
            items.add(new ListItem("New item", 5, false));
            adapter.notifyItemInserted(items.size()-1);
        });
    }
}