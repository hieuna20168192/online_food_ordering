package com.example.onlinefoodordering.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.ui.categories.CategoryFragment;
import com.example.onlinefoodordering.utils.Constants;

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
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_DATA_NAME, categories.get(position).getTypeName());
        args.putString(Constants.EXTRA_DATA_DESC, categories.get(position).getTypeDescription());
        args.putString(Constants.EXTRA_DATA_IMAGE, categories.get(position).getTypeThumb());
        args.putString(Constants.EXTRA_DATA_TYPE_ID, categories.get(position).getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getTypeName();
    }
}
