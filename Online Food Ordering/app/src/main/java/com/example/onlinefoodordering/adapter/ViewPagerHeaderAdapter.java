package com.example.onlinefoodordering.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerHeaderAdapter extends PagerAdapter {

    private List<Category> categories = new ArrayList<>();

    private static ClickListener clickListener;

    public ViewPagerHeaderAdapter(HomeViewModel viewModel,
                                  LifecycleOwner lifecycleOwner) {
        viewModel.getTypes().observe(lifecycleOwner, types -> {
            categories.clear();
            if (types != null) {
                categories.addAll(types);
                notifyDataSetChanged();
            }
        });
    }

    public void setList(List<Category> list) {
        this.categories = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ViewPagerHeaderAdapter.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(
                R.layout.item_view_pager_header,
                container,
                false
        );

        ImageView categoryThumb = view.findViewById(R.id.categoryThumb);
        TextView categoryName = view.findViewById(R.id.categoryName);

        String strCategoryThumb = categories.get(position).getTypeThumb();
        //Picasso.get().load(strCategoryThumb).into(categoryThumb);
        Glide.with(container.getContext()).load(strCategoryThumb).placeholder(R.drawable.shadow_bottom_to_top).into(categoryThumb);

        String strCategoryName = categories.get(position).getTypeName();
        categoryName.setText(strCategoryName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, position);
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.4f;
    }

    public interface ClickListener {
        void onClick(View v, int position);
    }
}
