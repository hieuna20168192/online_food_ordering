package com.example.onlinefoodordering.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
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

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView mealThumb;
        TextView mealName;
        ImageView cartHome;
        TextView cancel;
        TextView itemCost;
        TextView minOrder;

        // Dialog cartHome
        Dialog dialog;
        ImageView imageThumb;
        TextView popupMealName;
        TextView popupItemCost;
        TextView popupMinOrder;
        TextView popupCount;
        AppCompatImageView iPlus;
        AppCompatImageView iMinus;
        ItemSelectedListener listener;
        AppCompatButton popupAddToCart;
        TextView popupTotalCost;

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

            // Set Listener
            this.listener = listener;

            imageThumb = dialog.findViewById(R.id.imageThumb);
            popupMealName = dialog.findViewById(R.id.popup_title_meal);
            popupItemCost = dialog.findViewById(R.id.popup_cost_meal);
            popupMinOrder = dialog.findViewById(R.id.popup_min_order);
            popupCount = dialog.findViewById(R.id.t_count);
            popupAddToCart = dialog.findViewById(R.id.popup_add_to_cart);
            popupTotalCost = dialog.findViewById(R.id.popup_total_cost);
            iPlus = dialog.findViewById(R.id.i_plus);
            iMinus = dialog.findViewById(R.id.i_minus);
            cancel = dialog.findViewById(R.id.cancel);

            cancel.setOnClickListener(this);
            cartHome.setOnClickListener(this);
            iPlus.setOnClickListener(this);
            iMinus.setOnClickListener(this);
            popupAddToCart.setOnClickListener(this);
            mealThumb.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (v.getId() == cartHome.getId()){
                dialog.show();
            }
            if (v.getId() == cancel.getId()) {
                dialog.dismiss();
            }
            if (v.getId() == iPlus.getId()) {
                meal.setQuantity(meal.getQuantity()+1);
                popupCount.setText(""+meal.getQuantity());
                popupTotalCost.setText(meal.calculateItemCost() + "$");
            }
            if (v.getId() == iMinus.getId()) {
                if (meal.getQuantity() != 0) {
                    meal.setQuantity(meal.getQuantity()-1);
                    popupCount.setText(""+ meal.getQuantity());
                    popupTotalCost.setText(meal.calculateItemCost() + "$");
                }
            }
            if (v.getId() == mealThumb.getId()) {

            }
            if (v.getId() == popupAddToCart.getId()) {
                if (meal != null) {
                    listener.itemSelected(meal);
                }
            }
        }

        void bind(Meal meal) {
            this.meal = meal;
            Glide.with(itemView).load(meal.getThumb()).placeholder(R.drawable.shadow_bottom_to_top).into(mealThumb);
            mealName.setText(meal.getName());
            itemCost.setText(meal.getCost() + "$");
            minOrder.setText(meal.getMinOrder() + "' Min Order");

            // Bind Dialog
            Glide.with(dialog.getContext()).load(meal.getThumb()).placeholder(R.drawable.shadow_bottom_to_top)
                    .into(imageThumb);
            popupMealName.setText(meal.getName());
            popupMinOrder.setText(meal.getMinOrder() + "' Min Order");
            popupItemCost.setText(meal.getCost() + "$");
            popupCount.setText(""+meal.getQuantity());
        }
    }

}
