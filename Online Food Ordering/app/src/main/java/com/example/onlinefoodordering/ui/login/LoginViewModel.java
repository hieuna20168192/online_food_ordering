package com.example.onlinefoodordering.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinefoodordering.firebase.auth.AuthManages;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private AuthManages authManages;

    @Inject
    public LoginViewModel(AuthManages authManages) {
        this.authManages = authManages;
    }

    public AuthManages getAuthManages() {
        return authManages;
    }

    private MutableLiveData<Boolean> status = new MutableLiveData<>();

    LiveData<Boolean> getStatus() {
        return status;
    }

}
