package com.example.onlinefoodordering.di.module;

import com.example.onlinefoodordering.firebase.auth.AuthManages;
import com.example.onlinefoodordering.firebase.auth.AuthServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import kotlin.jvm.JvmStatic;

@Module
public class FirebaseModule {

    @Singleton
    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    FirebaseUser provideFirebaseUser(FirebaseAuth firebaseAuth) {
        return firebaseAuth.getCurrentUser();
    }

    @Provides
    @JvmStatic
    AuthServices authServices(AuthManages authManages){
        return authManages;
    }
}
