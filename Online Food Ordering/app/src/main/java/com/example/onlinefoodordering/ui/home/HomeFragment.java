package com.example.onlinefoodordering.ui.home;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ItemSelectedListener;
import com.example.onlinefoodordering.adapter.MealsListAdapter;
import com.example.onlinefoodordering.adapter.ViewPagerHeaderAdapter;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.categories.CategoryActivity;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import javax.inject.Inject;
import dagger.android.support.DaggerFragment;
public class HomeFragment extends DaggerFragment implements ItemSelectedListener {

    // Recycler View
    private RecyclerView recyclerMeals;
    private MealsListAdapter mealsListAdapter;

    // View Pager Meal
    private ViewPager viewPagerCategory;

    // Search TextView
    private EditText searchHomeView;

    private TextView errorTextView;
    private View loadingShimmer;
    private View loadingCategoryShimmer;

    private TextView viewAll;

    @Inject
    ViewModelFactory viewModelFactory;
    private HomeViewModel homeViewModel;
    private OrdersViewModel ordersViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        ordersViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrdersViewModel.class);

        // Navigate RecyclerView
        Log.d("onCallback", "run1");
        // Search Home View
        searchHomeView = view.findViewById(R.id.search_home_view);
        errorTextView = view.findViewById(R.id.tv_error);
        loadingShimmer = view.findViewById(R.id.shimmer_meal);
        loadingCategoryShimmer = view.findViewById(R.id.shimmer_category);
        viewAll = view.findViewById(R.id.viewAll);

        recyclerMeals = view.findViewById(R.id.recyclerMeals);
        mealsListAdapter = new MealsListAdapter(homeViewModel, this, this);
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerMeals.setLayoutManager(linearVertical);
        recyclerMeals.setAdapter(mealsListAdapter);
        observableViewModel();

        // Set Header View Pager Which Display all Categories of meals
        viewPagerCategory = view.findViewById(R.id.viewPagerHeader);
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(homeViewModel, this);
        viewPagerCategory.setAdapter(headerAdapter);
        viewPagerCategory.setPadding(0, 0, 0, 0);
        viewPagerCategory.setPageMargin(50);

//        observableViewModel();

        headerAdapter.setOnItemClickListener(new ViewPagerHeaderAdapter.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });



        searchHomeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                homeViewModel.inputString(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAnimation();
                homeViewModel.fetchRepos();
            }
        });

        return view;
    }

    private void observableViewModel() {
        homeViewModel.getRepos().observe(this, repos -> {
            if (repos != null) recyclerMeals.setVisibility(View.VISIBLE);
        });

        homeViewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                recyclerMeals.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        homeViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingShimmer.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    recyclerMeals.setVisibility(View.GONE);
                }
            }
        });

        homeViewModel.getNoResult().observe(this, noResult -> {
            if (noResult != null) if (noResult) {
                recyclerMeals.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText("No result for "+ searchHomeView.getText());
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
                mealsListAdapter.setList(homeViewModel.getRepos().getValue());
                recyclerMeals.setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getTypes().observe(this, types -> {
            if (types != null) viewPagerCategory.setVisibility(View.VISIBLE);
        });

        homeViewModel.getTypeLoading().observe(this, typeLoading -> {
            if (typeLoading != null) {
                loadingCategoryShimmer.setVisibility(typeLoading ? View.VISIBLE : View.GONE);
                if (typeLoading) {
                    viewPagerCategory.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void itemSelected(Meal meal) {
        ordersViewModel.updateCartList(meal);
    }

    private void runAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        a.reset();
        viewAll.clearAnimation();
        viewAll.startAnimation(a);
    }

}
