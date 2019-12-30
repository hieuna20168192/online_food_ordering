package com.example.onlinefoodordering.ui.categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.adapter.ViewPagerCategoryAdapter;
import com.example.onlinefoodordering.di.component.DaggerApplicationComponent;
import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import dagger.android.support.DaggerAppCompatActivity;


public class CategoryActivity extends DaggerAppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        initActionBar();
        initIntent();

    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initIntent() {
        Intent intent = getIntent();
        List<Category> categories =
                (List<Category>) intent.getSerializableExtra(Constants.EXTRA_CATEGORY);
        int position = intent.getIntExtra(Constants.EXTRA_POSITION, 0);


        ViewPagerCategoryAdapter adapter = new ViewPagerCategoryAdapter(
                getSupportFragmentManager(),
                categories
        );
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position, true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
