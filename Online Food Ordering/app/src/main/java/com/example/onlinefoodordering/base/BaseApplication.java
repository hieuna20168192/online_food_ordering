package com.example.onlinefoodordering.base;

import android.util.Log;

import com.example.onlinefoodordering.di.component.ApplicationComponent;
import com.example.onlinefoodordering.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BaseApplication", "Is injecting components into BaseActivity");
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }
}
