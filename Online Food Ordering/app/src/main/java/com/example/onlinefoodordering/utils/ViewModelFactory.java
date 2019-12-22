package com.example.onlinefoodordering.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * ViewModelFactory is a factory that extends ViewModelProvider.Factory
 * in order to provide ViewModel instances to consumer fragment classes.
 * We have injected that class with the ViewModelModule.
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    /**
     * Uses Class Object taken as a key parameter in the ViewModelFactory.create()
     * to retrieve a provider for that ViewModel.
     */
    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);
        // If our Providers map hasn't got that specific key, we will check if there is
        // a subclass of the ViewModel we must instantiate:
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        // If all previous attempts of getting a valid Provider from the Map will fail,
        // We throw an exception
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        // Finally, we can let Dagger creating our ViewModel by invoking the get()
        // method on the Provider object, as said before, and casting it to our final
        // type:
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}