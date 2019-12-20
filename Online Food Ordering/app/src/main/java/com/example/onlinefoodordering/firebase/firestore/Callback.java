package com.example.onlinefoodordering.firebase.firestore;

import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.model.Meal;

import java.util.List;

public interface Callback {
    default void onCallback(List<Meal> meals){}
    default void onCategoryCallback(List<Category> categories){}
}
