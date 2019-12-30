package com.example.onlinefoodordering.firebase.auth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.onlinefoodordering.firebase.Callback;
import com.example.onlinefoodordering.firebase.firestore.FirestoreServices;
import com.example.onlinefoodordering.model.User;
import com.example.onlinefoodordering.ui.login.LoginActivity;
import com.example.onlinefoodordering.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

public class AuthManages implements AuthServices {

    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;

    @Inject
    public AuthManages(FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore, FirebaseStorage firebaseStorage, Context context) {
        this.context = context;
        this.firebaseAuth = firebaseAuth;
        firestore = firebaseFirestore;
        storage = firebaseStorage;
    }

    @Override
    public void login(final String email, String password) {

        if (!check(email, password)) {
            Toast.makeText(context, "Login fail because given form is empty or null", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
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
    public void register(Uri avatar, String userName, String email, String password) {
        if (!check(email, password) && userName.isEmpty()) {
            Toast.makeText(context, "Register fail because given form is empty or null", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) return;

                        // task.isSuccessful, save user to database
                        uploadImageToFirebaseStorage(avatar, userName, email);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AuthManages", "Fail" + e.getMessage());
                Toast.makeText(context, "Register fail because " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getCurrentUser(Callback callback) {
        firestore.collection("users").document(firebaseAuth.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("AuthManages", "DocumentSnapshot data:" + document.getData());
                                User user = document.toObject(User.class);
                                callback.onGetCurrentUser(user);
                            } else {
                                Log.d("AuthManages", "No such document");
                            }
                        } else {
                            Log.d("AuthManages", "Get failed with", task.getException());
                        }
                    }
                });
    }

    private void uploadImageToFirebaseStorage(Uri imageThumb, String userName, String email) {
        if (imageThumb == null) return;

        String filename = UUID.randomUUID().toString();
        StorageReference ref = storage.getReference("/images/" + filename);
        ref.putFile(imageThumb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("Register", "Successfully uploaded image: " + taskSnapshot.getMetadata().getPath());
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Register", "File Location : " + uri);
                        saveUser(uri.toString(), userName, email);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Register", "Failed to upload image to storage: " + e.getMessage());
            }
        });
    }

    private void saveUser(String avatar, String userName, String email) {
        // Create a new user with the input data
        Map<String, Object> user = new HashMap<>();
        user.put("userName", userName);
        user.put("email", email);
        user.put("profileThumb", avatar);
        user.put("role", "customer");
        user.put("uid", firebaseAuth.getUid());

        // Add a new user with a generated ID
        firestore.collection("users").document(firebaseAuth.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Register successfully", Toast.LENGTH_LONG).show();

                        // Intent Main Activity
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AuthManages", "Error adding document " + e.getMessage());
                        Toast.makeText(context, "Register failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
