package com.example.onlinefoodordering.firebase.auth;

import android.net.Uri;

import com.example.onlinefoodordering.firebase.Callback;

public interface AuthServices {

    void login(String email, String password);

    void register(Uri avatar, String userName, String email, String password);

    void getCurrentUser(Callback callback);
}
