package com.example.onlinefoodordering.model;


import java.util.ArrayList;

public class Meal {

    private String idMeal;
    private String mealName;
    private String mealThumb;

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public void setMealThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }

    public Meal(String idMeal, String mealName, String mealThumb) {
        this.idMeal = idMeal;
        this.mealName = mealName;
        this.mealThumb = mealThumb;
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
