package com.example.onlinefoodordering.firebase;

import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.model.User;

import java.util.List;

public interface Callback {
    default void onCallback(List<Meal> meals){}
    default void onCategoryCallback(List<Category> categories){}
    default void onGetCurrentUser(User currentUser){}
}
