package com.example.onlinefoodordering.adapter;


import android.content.Context;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.firebase.firestore.FirestoreServices;
import com.example.onlinefoodordering.model.Meal;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder>{

    private List<Meal> meals;
    private Context context;

    public ItemListAdapter(List<Meal> meals, Context context) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_item,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String strMealThumb = meals.get(i).getThumb();
        Glide.with(context).load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top).into(holder.mealThumb);
        String strMealName = meals.get(i).getName();
        holder.mealName.setText(strMealName);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealThumb;
        TextView mealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealThumb = itemView.findViewById(R.id.item_meal_thumb);
            mealName = itemView.findViewById(R.id.item_title_meal);
        }
    }
}
