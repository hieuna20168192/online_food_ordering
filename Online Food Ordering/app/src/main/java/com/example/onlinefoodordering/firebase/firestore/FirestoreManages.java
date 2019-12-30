package com.example.onlinefoodordering.firebase.firestore;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.onlinefoodordering.firebase.Callback;
import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.model.Meal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FirestoreManages implements FirestoreServices{

    private FirebaseFirestore firestore;
    private Context context;


    @Inject
    public FirestoreManages(FirebaseFirestore firestore, Context context) {
        this.firestore = firestore;
        this.context = context;
    }


    @Override
    public void retrieveMeals(Callback callback) {
        List<Meal> mealList = new ArrayList<>();

        firestore.collection("recipes").get(Source.SERVER).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FirestoreManages", " Retrieve data failure because "+e.getMessage());
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        mealList.add(document.toObject(Meal.class));
                        System.out.println("isFromCache: " + document.getMetadata().isFromCache());
                        Log.d("Fire store Manages", document.toObject(Meal.class).getName());
                    }
                    callback.onCallback(mealList);
                }
            }
        });
    }

    @Override
    public void retrieveTypes(Callback callback) {
        List<Category> typeList = new ArrayList<>();

        firestore.collection("recipes_types").get(Source.SERVER).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FirestoreManages", " Retrieve data failure because "+e.getMessage());
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        typeList.add(document.toObject(Category.class));
                    }
                    callback.onCategoryCallback(typeList);
                }
            }
        });
    }

}
