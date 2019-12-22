package com.example.onlinefoodordering.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.model.Meal;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<Meal> carts = new ArrayList<>();
    private ItemSelectedListener itemSelectedListener;

    public ItemListAdapter(OrdersViewModel viewModel, LifecycleOwner lifecycleOwner, ItemSelectedListener selectedListener) {
        viewModel.getOrderList().observe(lifecycleOwner, repos -> {
            this.itemSelectedListener = selectedListener;
            carts.clear();
            if (repos != null) {
                carts.addAll(repos);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    public void setCart(List<Meal> carts) {
        this.carts = carts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item,
                parent, false);
        return new ViewHolder(v, itemSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(carts.get(i));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemThumb;
        TextView itemName;
        TextView itemCost;
        TextView itemMinOrder;
        TextView itemQuantity;
        AppCompatImageView iPlus, iMinus;
        TextView tCount;
        ItemSelectedListener listener;

        private Meal item;

        public ViewHolder(@NonNull View itemView, ItemSelectedListener listener) {
            super(itemView);
            itemThumb = itemView.findViewById(R.id.item_meal_thumb);
            itemName = itemView.findViewById(R.id.item_title_meal);
            itemCost = itemView.findViewById(R.id.item_cost_meal);
            itemMinOrder = itemView.findViewById(R.id.item_min_order);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            iPlus = itemView.findViewById(R.id.i_plus);
            iMinus = itemView.findViewById(R.id.i_minus);
            tCount = itemView.findViewById(R.id.t_count);
            iPlus.setOnClickListener(this);
            iMinus.setOnClickListener(this);

            // Set listener
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == iPlus.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                tCount.setText("" + item.getQuantity());
                itemQuantity.setText(" x"+ item.getQuantity());
                listener.changeCart(item);
            }
            if (v.getId() == iMinus.getId()) {
                if (item.getQuantity() != 0) {
                    item.setQuantity(item.getQuantity() - 1);
                    tCount.setText("" + item.getQuantity());
                    itemQuantity.setText(" x"+ item.getQuantity());
                    listener.changeCart(item);
                }
            }
        }

        void bind(Meal item) {
            this.item = item;
            Glide.with(itemView).load(item.getThumb()).placeholder(R.drawable.shadow_bottom_to_top).into(itemThumb);
            itemName.setText(item.getName());
            itemCost.setText(item.getCost() + "$");
            itemMinOrder.setText(item.getMinOrder() + "' Min Order");
            itemQuantity.setText(" x" + item.getQuantity());
            tCount.setText("" + item.getQuantity());
        }
    }
}
