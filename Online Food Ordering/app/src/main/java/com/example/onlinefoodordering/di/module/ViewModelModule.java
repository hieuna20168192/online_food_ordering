package com.example.onlinefoodordering.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinefoodordering.di.util.ViewModelKey;
import com.example.onlinefoodordering.ui.home.HomeViewModel;
import com.example.onlinefoodordering.ui.login.LoginViewModel;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;
import com.example.onlinefoodordering.ui.register.RegisterViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel (LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel bindRegisterViewModel (RegisterViewModel registerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel (HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel.class)
    abstract ViewModel bindOrdersViewModel (OrdersViewModel ordersViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
