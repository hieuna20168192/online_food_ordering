package com.example.onlinefoodordering.di.component;

import android.app.Application;

import com.example.onlinefoodordering.base.BaseApplication;
import com.example.onlinefoodordering.di.module.ActivityBindingModule;
import com.example.onlinefoodordering.di.module.ContextModule;
import com.example.onlinefoodordering.di.module.FirebaseModule;
import com.example.onlinefoodordering.di.module.ViewModelModule;
import com.example.onlinefoodordering.ui.home.HomeViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ContextModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        ViewModelModule.class,
        FirebaseModule.class
})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
