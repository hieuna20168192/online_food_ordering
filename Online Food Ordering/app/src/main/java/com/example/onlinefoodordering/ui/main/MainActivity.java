package com.example.onlinefoodordering.ui.main;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.ui.home.HomeViewModel;
import com.example.onlinefoodordering.ui.orders.OrdersViewModel;
import com.example.onlinefoodordering.utils.ViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottom_nav;
    private AppCompatButton viewCart;

    //Sliding cart
    private SlidingUpPanelLayout slidingCartPane;
    private TextView slidingCartCount;
    private TextView slidingCartPrice;

    // ViewModel
    @Inject
    ViewModelFactory factory;
    private OrdersViewModel ordersViewModel;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set ViewModel
        ordersViewModel = ViewModelProviders.of(this, factory).get(OrdersViewModel.class);
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottom_nav = findViewById(R.id.bottom_nav);

        // Navigate from bottom_nav to mobile_nav
        NavigationUI.setupWithNavController(bottom_nav, navController);

        // Sliding Cart
        slidingCartPane = findViewById(R.id.sliding_cart);
        slidingCartCount = findViewById(R.id.t_cart_count);
        slidingCartPrice = findViewById(R.id.t_total_price);
        viewCart = findViewById(R.id.b_cart);
        observableViewModel();

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.ordersFragment);
                slidingCartPane.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void observableViewModel() {
        ordersViewModel.getOrderList().observe(this, cart -> {
            if (cart != null) {
                slidingCartCount.setText(String.valueOf(ordersViewModel.canculateItemCount()));
            } else {
                slidingCartCount.setText("0");
            }
        });

        ordersViewModel.getGrandTotal().observe(this, total -> {
            if (total != null) {
                slidingCartPrice.setText(ordersViewModel.getGrandTotal().getValue().toString() + "$");
            } else {
                slidingCartPrice.setText("0$");
            }
        });

        ordersViewModel.getIsEmpty().observe(this, status -> {
            if (status != null)
                if (status == true) {
                    slidingCartPane.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    if (navController.getCurrentDestination().getId() == R.id.homeFragment) {
                        slidingCartPane.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    }
                }
        });
    }

}
