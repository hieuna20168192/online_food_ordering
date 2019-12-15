package com.example.onlinefoodordering.ui.home;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.MealsListAdapter;
import com.example.onlinefoodordering.adapter.ViewPagerHeaderAdapter;
import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.categories.CategoryActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    // Recycler View
    private RecyclerView recyclerMeals;

    // View Pager Meal
    private ViewPager viewPagerCategory;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        // Navigate RecyclerView
        recyclerMeals = view.findViewById(R.id.recyclerMeals);

        MealsListAdapter mealsListAdapter = new MealsListAdapter(Meal.sMealList, getContext());
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerMeals.setLayoutManager(linearVertical);
        recyclerMeals.setAdapter(mealsListAdapter);

        // Set Header View Pager Which Display all Categories of meals
        viewPagerCategory = view.findViewById(R.id.viewPagerHeader);
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(Category.sCategoriesList, getContext());
        viewPagerCategory.setAdapter(headerAdapter);
        viewPagerCategory.setPadding(0, 0, 0, 0);
        viewPagerCategory.setPageMargin(50);
        headerAdapter.notifyDataSetChanged();
        headerAdapter.setOnItemClickListener(new ViewPagerHeaderAdapter.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
