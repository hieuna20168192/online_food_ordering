package com.example.onlinefoodordering.ui.register;

import androidx.lifecycle.ViewModel;

import com.example.onlinefoodordering.firebase.auth.AuthManages;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RegisterViewModel extends ViewModel {

    private AuthManages authManages;

    @Inject
    public RegisterViewModel(AuthManages authManages) {
        this.authManages = authManages;
    }

    public AuthManages getAuthManages() {
        return authManages;
    }
}
