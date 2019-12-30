package com.example.onlinefoodordering.firebase.firestore;

import com.example.onlinefoodordering.firebase.Callback;


public interface FirestoreServices {

    void retrieveMeals(Callback callback);

    void retrieveTypes(Callback callback);

}
