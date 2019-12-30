package com.example.onlinefoodordering.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinefoodordering.R;
import com.example.onlinefoodordering.ui.login.LoginActivity;
import com.example.onlinefoodordering.utils.ViewModelFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends DaggerAppCompatActivity {

    private final String TAG = "RegisterActivity";
    private final int REQUEST_CODE_LOAD_IMAGE = 0;

    private TextView backLogin;
    private TextView registerUsername, registerEmail, registerPassword;
    private Button selectPhoto, btnRegister;
    private CircleImageView selectPhotoImage;

    private Uri selectedPhotoUri;

    @Inject
    ViewModelFactory factory;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up view model
        registerViewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel.class);

        // Set up view
        registerUsername = findViewById(R.id.txt_register_username);
        registerEmail = findViewById(R.id.txt_register_email);
        registerPassword = findViewById(R.id.txt_register_password);
        selectPhoto = findViewById(R.id.select_photo_button);
        selectPhotoImage = findViewById(R.id.select_photo_image_view_register);
        btnRegister = findViewById(R.id.btn_register);

        backLogin = findViewById(R.id.textview_back_login);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Try to show login Activity");
                // Launch Login Activity somehow
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Try to show photo selector");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_LOAD_IMAGE);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPhotoUri == null) {
                    Toast.makeText(getApplication(), "Please choose a photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerViewModel.getAuthManages().register(selectedPhotoUri, registerUsername.getText().toString(),
                        registerEmail.getText().toString(), registerPassword.getText().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == Activity.RESULT_OK
                && data != null) {
            // Proceed and check what the selected image was...

            selectedPhotoUri = data.getData();
            selectPhotoImage.setImageURI(selectedPhotoUri);
            selectPhoto.setAlpha(0f);
        }
    }

}
