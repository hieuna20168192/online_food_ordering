package com.example.onlinefoodordering.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.detail.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.ViewHolder>{

    private List<Meal> meals;
    private Context context;

    public MealsListAdapter(List<Meal> meals, Context context) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_meal,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String strMealThumb = meals.get(i).getMealThumb();
        Picasso.get().load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top).into(holder.mealThumb);
        //Glide.with(context).load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top).into(holder.mealThumb);
        String strMealName = meals.get(i).getMealName();
        holder.mealName.setText(strMealName);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView mealThumb;
        TextView mealName;
        ImageView cartHome;
        TextView cancel;

        // Dialog cartHome
        private Dialog dialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealThumb = itemView.findViewById(R.id.mealThumb);
            mealName = itemView.findViewById(R.id.mealName);
            cartHome = itemView.findViewById(R.id.cart_home);

            // Set Dialog
            dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.popup_item);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            cancel = dialog.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
            cartHome.setOnClickListener(this);
            mealThumb.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == mealThumb.getId()) {
                //Toast.makeText(itemView.getContext(), "Clicked Item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                v.getContext().startActivity(intent);
            }
            if (v.getId() == cartHome.getId()){
                dialog.show();
            }
            if (v.getId() == cancel.getId()) {
                dialog.dismiss();
            }
        }
    }
}
