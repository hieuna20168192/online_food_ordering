package com.example.onlinefoodordering.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Meal {

    @SerializedName("id")
    private String idMeal;
    @SerializedName("name")
    private String mealName;
    @SerializedName("thumb")
    private String mealThumb;
    @SerializedName("cost")
    private Long cost;
    @SerializedName("description")
    private String description;
    @SerializedName("minOrder")
    private Long minOrder;

    private int quantity = 0;

    public String getId() {
        return idMeal;
    }

    public void setId(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getName() {
        return mealName;
    }

    public void setName(String mealName) {
        this.mealName = mealName;
    }

    public String getThumb() {
        return mealThumb;
    }

    public void setThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Long minOrder) {
        this.minOrder = minOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Meal(String idMeal, String mealName, String mealThumb) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.mealThumb = mealThumb;
    }

    public Meal() {
    }

    public static ArrayList<Meal> sMealList = new ArrayList<>();
    static {
        sMealList.add(new Meal("01", "Kalua pig", "https://farm8.staticflickr.com/7097/6871848410_ed492b1f84_z.jpg"));
        sMealList.add(new Meal("02", "Lomi Salmon (lomi-lomi salmon)", "https://farm7.staticflickr.com/6120/6871848798_c1bbd0f491_z.jpg"));
        sMealList.add(new Meal("03", "Laulau", "https://farm8.staticflickr.com/7048/7017957513_855f38c366_z.jpg"));
        sMealList.add(new Meal("04", "Poi", "https://farm7.staticflickr.com/6044/6871849292_cf27ea7dee_z.jpg"));
        sMealList.add(new Meal("05", "Bar Leather Apron", "https://thumbor.thedailymeal.com/S2MC63g-KpvQ0kDCGkQ4HrTDfss=/774x516/filters:format(webp)/https://www.thedailymeal.com/sites/default/files/slideshows/1862316/2128748/3_Hawaii_Bar_LEather_SlideE_Dit_.jpg"));
    }
}
