package com.example.onlinefoodordering.di.module;

import com.example.onlinefoodordering.ui.categories.CategoryActivity;
import com.example.onlinefoodordering.ui.categories.CategoryFragment;
import com.example.onlinefoodordering.ui.detail.DetailActivity;
import com.example.onlinefoodordering.ui.login.LoginActivity;
import com.example.onlinefoodordering.ui.main.MainActivity;
import com.example.onlinefoodordering.ui.main.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract DetailActivity bindDetailActivity();

    @ContributesAndroidInjector
    abstract CategoryActivity bindCategoryActivity();

    @ContributesAndroidInjector
    abstract CategoryFragment provideCategoryFragment();

}