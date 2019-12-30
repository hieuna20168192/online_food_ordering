package com.example.onlinefoodordering.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ContextModule {
    @Binds
    abstract Context provideContext(Application application);
}
