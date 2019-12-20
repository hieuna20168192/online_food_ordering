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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.detail.DetailActivity;
import com.example.onlinefoodordering.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.ViewHolder>{

    private List<Meal> meals = new ArrayList<>();
    private ItemSelectedListener itemSelectedListener;

    public MealsListAdapter(HomeViewModel viewModel, LifecycleOwner lifecycleOwner, ItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            meals.clear();
            if (repos != null) {
                meals.addAll(repos);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    public void setList(List<Meal> lists) {
        meals = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_meal,
                parent, false);

        return new ViewHolder(view, itemSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(meals.get(i));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView mealThumb;
        TextView mealName;
        ImageView cartHome;
        TextView cancel;
        TextView itemCost;
        TextView minOrder;


        // Dialog cartHome
        Dialog dialog;

        private Meal meal;

        public ViewHolder(@NonNull View itemView, ItemSelectedListener listener) {
            super(itemView);
            mealThumb = itemView.findViewById(R.id.mealThumb);
            mealName = itemView.findViewById(R.id.mealName);
            cartHome = itemView.findViewById(R.id.cart_home);
            itemCost = itemView.findViewById(R.id.item_cost_meal);
            minOrder = itemView.findViewById(R.id.min_order);

            // Set Dialog
            dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.popup_item);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            cancel = dialog.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
            cartHome.setOnClickListener(this);
            mealThumb.setOnClickListener(v -> {
                if (meal != null) {
                    listener.itemSelected(meal);
                }
            });
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == cartHome.getId()){
                dialog.show();
            }
            if (v.getId() == cancel.getId()) {
                dialog.dismiss();
            }
        }

        void bind(Meal meal) {
            this.meal = meal;
            Glide.with(itemView).load(meal.getThumb()).placeholder(R.drawable.shadow_bottom_to_top).into(mealThumb);
            mealName.setText(meal.getName());
            itemCost.setText(meal.getCost() + "$");
            minOrder.setText(meal.getMinOrder() + "' Min Order");
        }
    }
}
