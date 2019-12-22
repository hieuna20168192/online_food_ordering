package com.example.onlinefoodordering.ui.categories;

import androidx.lifecycle.ViewModelProviders;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ItemListAdapter;
import com.example.onlinefoodordering.adapter.ItemSelectedListener;
import com.example.onlinefoodordering.adapter.MealsListAdapter;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CategoryFragment extends DaggerFragment implements ItemSelectedListener {

    private CategoryViewModel mViewModel;

    private RecyclerView recyclerView;

    @Inject
    ViewModelFactory factory;
    private OrdersViewModel ordersViewModel;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.category_fragment, container, false);

        // Set ViewModel
        ordersViewModel = ViewModelProviders.of(this, factory).get(OrdersViewModel.class);

        ItemListAdapter adapter = new ItemListAdapter(ordersViewModel, this ,this);
        recyclerView = v.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }


}
