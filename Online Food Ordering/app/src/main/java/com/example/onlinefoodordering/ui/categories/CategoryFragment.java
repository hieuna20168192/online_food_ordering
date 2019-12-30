package com.example.onlinefoodordering.ui.categories;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ItemListAdapter;
import com.example.onlinefoodordering.adapter.ItemSelectedListener;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.home.HomeViewModel;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;
import com.example.onlinefoodordering.utils.Constants;
import com.example.onlinefoodordering.utils.ViewModelFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CategoryFragment extends DaggerFragment implements View.OnClickListener, ItemSelectedListener{

    private RecyclerView recyclerView;
    private ImageView categoryBg;
    private TextView textCategory;
    private ImageView imageCategory;

    @Inject
    ViewModelFactory viewModelFactory;
    private HomeViewModel homeViewModel;
    private OrdersViewModel ordersViewModel;


    AlertDialog.Builder descDialog;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.category_fragment, container, false);
        categoryBg = v.findViewById(R.id.imageCategoryBg);
        textCategory = v.findViewById(R.id.textCategory);
        imageCategory = v.findViewById(R.id.imageCategory);
        recyclerView = v.findViewById(R.id.recyclerView);
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        ordersViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrdersViewModel.class);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            textCategory.setText(getArguments().getString(Constants.EXTRA_DATA_DESC));
            Picasso.get()
                    .load(getArguments().getString(Constants.EXTRA_DATA_IMAGE))
                    .into(imageCategory);
            Picasso.get()
                    .load(getArguments().getString(Constants.EXTRA_DATA_IMAGE))
                    .into(categoryBg);
            descDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getArguments().getString(Constants.EXTRA_DATA_NAME))
                    .setMessage(getArguments().getString(Constants.EXTRA_DATA_DESC));
        }
        view.findViewById(R.id.cardCategory).setOnClickListener(this);

        // Set up recycler view
        ItemListAdapter adapter = new ItemListAdapter(homeViewModel.mealsByCategory(getArguments()
                .getString(Constants.EXTRA_DATA_TYPE_ID))
                , this);
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearVertical);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == v.findViewById(R.id.cardCategory).getId()) {
            descDialog.setPositiveButton("CLOSE", (dialog, which) -> dialog.dismiss());
            descDialog.show();
        }
    }

    @Override
    public void changeCartFromCategory(Meal meal) {
        ordersViewModel.updateCartList(meal);
    }
}
