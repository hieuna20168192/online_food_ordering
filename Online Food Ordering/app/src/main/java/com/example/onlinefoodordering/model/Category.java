package com.example.onlinefoodordering.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    @SerializedName("id")
    private String idCategory;
    @SerializedName("typeName")
    private String CategoryName;
    @SerializedName("typeThumb")
    private String CategoryThumb;
    @SerializedName("typeDescription")
    private String description;

    public Category(String idCategory, String categoryName, String categoryThumb, String description) {
        this.idCategory = idCategory;
        CategoryName = categoryName;
        CategoryThumb = categoryThumb;
        this.description = description;
    }

    public Category() {
    }

    public String getId() {
        return idCategory;
    }

    public void setId(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getTypeName() {
        return CategoryName;
    }

    public void setTypeName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getTypeThumb() {
        return CategoryThumb;
    }

    public void setTypeThumb(String categoryThumb) {
        CategoryThumb = categoryThumb;
    }

    public String getTypeDescription() {
        return description;
    }

    public void setTypeDescription(String description) {
        this.description = description;
    }

    public static ArrayList<Category> sCategoriesList = new ArrayList<>();
    static {
        sCategoriesList.add(new Category("c01", "Breakfast", "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/vegan_fried_breakfast_34182_16x9.jpg", ""));
        sCategoriesList.add(new Category("c02", "Dinner", "https://www.budgetbytes.com/wp-content/uploads/2018/01/Sheet-Pan-BBQ-Meatloaf-Dinner-plate.jpg", "Dinner usually refers to a significant and important meal of the day, which can be the noon or the evening meal. However, the term dinner can have many different meanings depending on the culture; it may mean a meal of any size eaten at any time of the day.[21][22] Historically, it referred to the first large meal of the day, eaten around noon, and is still sometimes used for a noon-time meal, particularly if it is a large or main meal"));
        sCategoriesList.add(new Category("c03", "Elevenses", "https://ksr-ugc.imgix.net/assets/011/605/764/340fdeb3a462cf2dd2bdee9b748a2170_original.jpg?ixlib=rb-2.1.0&crop=faces&w=1552&h=873&fit=crop&v=1463685252&auto=format&frame=1&q=92&s=a8725f707021108f4e53021b93b991eb", "Elevenses is a short break taken at around 11:00 a.m. to consume a drink or snack. The names and details vary among countries."));
        sCategoriesList.add(new Category("c04", "Brunch", "https://assets3.thrillist.com/v1/image/2821172/size/gn-gift_guide_variable_c.jpg", "Brunch is a combination of breakfast and lunch, and regularly has some form of alcoholic drink (most usually champagne or a cocktail) served with it.[1][2][3] The word is a portmanteau of breakfast and lunch.[4] Brunch originated in England in the late 19th century and became popular in the United States in the 1930s"));
        sCategoriesList.add(new Category("c05", "Lunch", "https://simply-delicious-food.com/wp-content/uploads/2018/07/mexican-lunch-bowls-3.jpg", "Lunch, the abbreviation for luncheon, is a light meal typically eaten at midday.[19] The origin of the words lunch and luncheon relate to a small snack originally eaten at any time of the day or night. During the 20th century the meaning gradually narrowed to a small or mid-sized meal eaten at midday. Lunch is commonly the second meal of the day after breakfast. The meal varies in size depending on the culture, and significant variations exist in different areas of the world."));
        sCategoriesList.add(new Category("c06", "Drink", "https://www.maxim.com/.image/t_share/MTU5OTc5NjM0NDA0ODk0MjQw/gettyimages-114847590.jpg", "Lunch, the abbreviation for luncheon, is a light meal typically eaten at midday.[19] The origin of the words lunch and luncheon relate to a small snack originally eaten at any time of the day or night. During the 20th century the meaning gradually narrowed to a small or mid-sized meal eaten at midday. Lunch is commonly the second meal of the day after breakfast. The meal varies in size depending on the culture, and significant variations exist in different areas of the world."));

    }
}
