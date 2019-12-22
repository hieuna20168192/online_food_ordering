package com.example.onlinefoodordering.di.module;

import com.example.onlinefoodordering.firebase.auth.AuthManages;
import com.example.onlinefoodordering.firebase.auth.AuthServices;
import com.example.onlinefoodordering.firebase.firestore.FirestoreManages;
import com.example.onlinefoodordering.firebase.firestore.FirestoreServices;
import com.example.onlinefoodordering.model.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

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
    FirebaseFirestore providesFirestore() {
        FirebaseFirestore.setLoggingEnabled(true);
        return FirebaseFirestore.getInstance();
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

    @Provides
    @JvmStatic
    FirestoreServices firestoreServices(FirestoreManages firestoreManages) {
        return firestoreManages;
    }

}
