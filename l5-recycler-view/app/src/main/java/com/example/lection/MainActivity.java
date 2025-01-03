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


public class MainActivity extends AppCompatActivity {

    private List<ListItem> items = new ArrayList<>(List.of(
            new ListItem("First", 10, false),
            new ListItem("Second", 10, false),
            new ListItem("Third", 10, true)
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        RecyclerView view = findViewById(R.id.recyclerView);

        Adapter adapter = new Adapter(items);
        view.setAdapter(adapter);

        var fab = ((FloatingActionButton) findViewById(R.id.floatingActionButton));
        fab.setOnClickListener(evt -> {
            items.add(new ListItem("New item", 5, false));
            adapter.notifyItemInserted(items.size()-1);
        });
    }
}