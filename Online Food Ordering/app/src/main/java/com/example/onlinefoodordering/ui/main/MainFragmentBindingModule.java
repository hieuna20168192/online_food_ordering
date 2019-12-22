package com.example.onlinefoodordering.ui.main;

import com.example.onlinefoodordering.ui.categories.CategoryFragment;
import com.example.onlinefoodordering.ui.favorite.FavoriteFragment;
import com.example.onlinefoodordering.ui.home.HomeFragment;
import com.example.onlinefoodordering.ui.orders.OrdersFragment;
import com.example.onlinefoodordering.ui.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract HomeFragment provideHomeFragment();

    @ContributesAndroidInjector
    abstract OrdersFragment provideOrderFragment();

    @ContributesAndroidInjector
    abstract FavoriteFragment provideFavoriteFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment provideProfileFragment();


}
