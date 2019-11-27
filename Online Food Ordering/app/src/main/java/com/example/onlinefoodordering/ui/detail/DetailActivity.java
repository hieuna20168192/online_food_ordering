package com.example.onlinefoodordering.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ItemListAdapter;
import com.example.onlinefoodordering.model.Meal;

public class DetailActivity extends AppCompatActivity {

    // Recycler View
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        recyclerView = findViewById(R.id.recycler_view_related_product);
        ItemListAdapter adapter = new ItemListAdapter(Meal.sMealList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
