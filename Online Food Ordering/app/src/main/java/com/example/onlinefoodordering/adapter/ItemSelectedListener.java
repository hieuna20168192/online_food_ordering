package com.example.onlinefoodordering.adapter;
import com.example.onlinefoodordering.model.Meal;

public interface ItemSelectedListener {

    default void changeCart(Meal meal){}
    default void itemSelected(Meal meal){}
}
