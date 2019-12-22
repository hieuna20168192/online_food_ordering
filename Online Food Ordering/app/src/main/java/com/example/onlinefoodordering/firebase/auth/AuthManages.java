package com.example.onlinefoodordering.firebase.auth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.onlinefoodordering.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class AuthManages implements AuthServices {

    private Context context;
    private FirebaseAuth firebaseAuth;

    @Inject
    public AuthManages(FirebaseAuth firebaseAuth, Context context) {
        this.context = context;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void login(final String email, String password) {

        if (!check(email, password)) {
            Toast.makeText(context, "Login fail because given String is empty or null", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("AuthManages", "Successful");
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AuthManages", "Fail" + e.getMessage());
                Toast.makeText(context, "Login fail because " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean check(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void register(String avatar, String userName, String email, String password) {

    }
}
