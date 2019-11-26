package com.example.onlinefoodordering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerHeaderAdapter extends PagerAdapter {

    private List<Category> categories;
    private Context context;
    private static ClickListener clickListener;

    public ViewPagerHeaderAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_view_pager_header,
                container,
                false
        );

        ImageView categoryThumb = view.findViewById(R.id.categoryThumb);
        TextView categoryName = view.findViewById(R.id.categoryName);

        String strCategoryThumb = categories.get(position).getCategoryThumb();
        //Picasso.get().load(strCategoryThumb).into(categoryThumb);
        Glide.with(context).load(strCategoryThumb).placeholder(R.drawable.shadow_bottom_to_top).into(categoryThumb);

        String strCategoryName = categories.get(position).getCategoryName();
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
        return 0.6f;
    }

    public interface ClickListener {
        void onClick(View v, int position);
    }
}
