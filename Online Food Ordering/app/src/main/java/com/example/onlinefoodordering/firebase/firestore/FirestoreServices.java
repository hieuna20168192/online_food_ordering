package com.example.onlinefoodordering.firebase.firestore;

import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.model.Meal;

import java.util.List;



public interface FirestoreServices {

    void retrieveMeals(Callback callback);

    void retrieveTypes(Callback callback);

}
