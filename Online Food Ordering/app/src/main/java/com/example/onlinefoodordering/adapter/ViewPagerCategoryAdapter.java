package com.example.onlinefoodordering.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.ui.categories.CategoryFragment;

import java.util.List;

public class ViewPagerCategoryAdapter extends FragmentPagerAdapter {

    private List<Category> categories;

    public ViewPagerCategoryAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        this.categories = categories;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return categories.size();
    }
}
