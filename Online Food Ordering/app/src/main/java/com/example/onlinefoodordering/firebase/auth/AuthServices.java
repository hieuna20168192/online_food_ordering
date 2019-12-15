package com.example.onlinefoodordering.firebase.auth;

public interface AuthServices {

    void login(String email, String password);

    void register(String avatar, String userName, String email, String password);

}
