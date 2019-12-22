package com.example.onlinefoodordering.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ItemListAdapter;
import com.example.onlinefoodordering.adapter.ItemSelectedListener;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity implements ItemSelectedListener {

    // Recycler View
    RecyclerView recyclerView;

    @Inject
    ViewModelFactory factory;
    private OrdersViewModel ordersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ordersViewModel = ViewModelProviders.of(this, factory).get(OrdersViewModel.class);

        recyclerView = findViewById(R.id.recycler_view_related_product);
        ItemListAdapter adapter = new ItemListAdapter(ordersViewModel, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
