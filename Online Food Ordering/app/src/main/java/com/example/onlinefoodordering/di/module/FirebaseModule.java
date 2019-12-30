package com.example.onlinefoodordering.di.module;

import com.example.onlinefoodordering.firebase.auth.AuthManages;
import com.example.onlinefoodordering.firebase.auth.AuthServices;
import com.example.onlinefoodordering.firebase.firestore.FirestoreManages;
import com.example.onlinefoodordering.firebase.firestore.FirestoreServices;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.model.User;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import durdinapps.rxfirebase2.RxFirebaseStorage;
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
    FirebaseStorage providesFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Singleton
    @Provides
    FirebaseUser provideFirebaseUser(FirebaseAuth firebaseAuth) {
        return firebaseAuth.getCurrentUser();
    }

    @Singleton
    @Provides
    public User provideUserProfile(FirebaseUser user) {
        User userProfile = new User();
        userProfile.setUid(user.getUid());
        userProfile.setEmail(user.getEmail());
        userProfile.setUserName(user.getDisplayName());
        userProfile.setProfileThumb(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
        return userProfile;
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
