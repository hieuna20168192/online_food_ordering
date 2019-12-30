package com.example.onlinefoodordering.ui.login;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.ui.main.MainActivity;
import com.example.onlinefoodordering.ui.register.RegisterActivity;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    private LoginViewModel loginViewModel;

    private Button btnLogin;
    private TextView email;
    private TextView password;
    private TextView backRegister;

    private ImageView bgLoginImage;
    private View frame;
    private TextView loginLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.txt_login_email);
        password = findViewById(R.id.txt_login_password);
        backRegister = findViewById(R.id.textview_back_register);

        bgLoginImage = findViewById(R.id.bg_login);
        frame = findViewById(R.id.login_frame);
        loginLoading = findViewById(R.id.login_loading);

        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        observable();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginViewModel.login(email.getText().toString(), password.getText().toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        backRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void observable() {

        loginViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    loginLoading.setVisibility(View.VISIBLE);
                    frame.setVisibility(View.INVISIBLE);
                    bgLoginImage.setVisibility(View.INVISIBLE);
                } else {
                    loginLoading.setVisibility(View.INVISIBLE);
                    frame.setVisibility(View.VISIBLE);
                    bgLoginImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
