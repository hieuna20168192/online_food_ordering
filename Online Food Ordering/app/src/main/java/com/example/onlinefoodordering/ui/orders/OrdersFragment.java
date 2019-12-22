package com.example.onlinefoodordering.ui.orders;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ItemListAdapter;
import com.example.onlinefoodordering.adapter.ItemSelectedListener;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.home.HomeFragment;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class OrdersFragment extends DaggerFragment implements ItemSelectedListener{

    private OrdersViewModel mViewModel;

    // Recycler View
    RecyclerView recyclerView;

    TextView orderCartTotal;
    TextView orderDiscount;
    TextView orderTax;
    TextView orderSubTotal;
    AppCompatButton btnCheckout;
    View cartEmptyView, frame;

    @Inject
    ViewModelFactory viewModelFactory;
    private OrdersViewModel ordersViewModel;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.orders_fragment, container, false);

        ordersViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrdersViewModel.class);

        cartEmptyView = v.findViewById(R.id.cart_empty_view);
        frame = v.findViewById(R.id.headerView);

        orderCartTotal = v.findViewById(R.id.order_cart_total);
        orderDiscount = v.findViewById(R.id.order_discount);
        orderSubTotal = v.findViewById(R.id.order_sub_total);
        orderTax = v.findViewById(R.id.order_tax);
        btnCheckout = v.findViewById(R.id.btn_check_out);

        recyclerView = v.findViewById(R.id.recyclerItems);
        ItemListAdapter adapter = new ItemListAdapter(ordersViewModel, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        observableViewModel();

        return v;
    }

    private void observableViewModel() {
        ordersViewModel.getOrderList().observe(this, cart -> {
            if (cart != null) {

            }
        });

        ordersViewModel.getGrandTotal().observe(this, total -> {
            if (total != null) {
                orderCartTotal.setText(ordersViewModel.getGrandTotal().getValue() + "$");
                Double subTotal = ordersViewModel.getGrandTotal().getValue() * (1 - Double.parseDouble(orderDiscount.getText().toString().replace("%", "")) / 100
                        + Double.parseDouble(orderTax.getText().toString().replace("%", "")) / 100);
                orderSubTotal.setText(Math.floor(subTotal * 100) / 100 + "$");
            }
        });

        ordersViewModel.getIsEmpty().observe(this, isEmpty -> {

            if (isEmpty != null)
                if (isEmpty == true) {
                    frame.setVisibility(View.GONE);
                    cartEmptyView.setVisibility(View.VISIBLE);
                } else {
                    frame.setVisibility(View.VISIBLE);
                    cartEmptyView.setVisibility(View.GONE);
                }
        });
    }

    @Override
    public void changeCart(Meal meal) {
        ordersViewModel.updateCartList(meal);
    }
}
