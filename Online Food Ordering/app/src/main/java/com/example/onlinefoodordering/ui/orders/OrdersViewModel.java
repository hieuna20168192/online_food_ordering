package com.example.onlinefoodordering.ui.orders;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinefoodordering.adapter.ItemSelectedListener;
import com.example.onlinefoodordering.firebase.firestore.FirestoreManages;
import com.example.onlinefoodordering.model.Meal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class OrdersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    CompositeDisposable disposable;

    private FirestoreManages manages;

    private MutableLiveData<List<Meal>> orderList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    private MutableLiveData<Double> grandTotal = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private List<Meal> ordersList = new ArrayList<>();

    private PublishSubject mCartListSubject;

    public MutableLiveData<List<Meal>> getOrderList() {
        return orderList;
    }

    public MutableLiveData<Boolean> getIsEmpty() {
        return isEmpty;
    }

    public MutableLiveData<Double> getGrandTotal() {
        return grandTotal;
    }

    public MutableLiveData<Boolean> getIsError() {
        return isError;
    }

    public List<Meal> getOrdersList() {
        return ordersList;
    }

    @Inject
    public OrdersViewModel(FirestoreManages firestoreManages) {
        manages = firestoreManages;
        disposable = new CompositeDisposable();
        mCartListSubject = PublishSubject.create();
        subscribeToCartChanges();
    }

    private void subscribeToCartChanges() {
        isEmpty.setValue(true);
        mCartListSubject.subscribe(getObserver());
    }

    private Observer<List<Meal>> getObserver() {
        return new Observer<List<Meal>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Meal> meals) {
                if (meals.isEmpty()) {
                    isEmpty.setValue(true);
                } else {
                    isEmpty.setValue(false);
                }
                orderList.setValue(meals);
                grandTotal.setValue(canculateGrandTotal());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private boolean checkDuplicatedItem(Meal item) {
        Boolean check = false;
        for (Meal meal : ordersList) {
            Log.d("meal ", meal.getId());
            if (meal.getId().equals(item.getId())) {
                check = !check;
                break;
            }
        }
        return check;
    }

    private void updateItem(Meal item) {
        for (Meal meal : ordersList) {
            if (meal.getId().equals(item.getId())) {
                meal.setQuantity(item.getQuantity());
                break;
            }
        }
    }

    private Double canculateGrandTotal() {
        Double grandTotal = 0.0;
        for (Meal meal : ordersList) {
            grandTotal += meal.calculateItemCost();
        }
        return grandTotal;
    }

    public int canculateItemCount(){
       int itemCount = 0;
       for (Meal meal : ordersList) {
           itemCount += meal.getQuantity();
       }
       return itemCount;
    }

    public void updateCartList(Meal meal) {
        if (meal.getQuantity() >= 0) {
            if (!checkDuplicatedItem(meal)) {
                Log.d("meal ", "add");
                ordersList.add(meal);
            } else {
                Log.d("meal ", "update");
                updateItem(meal);
            }
            cleanCartList();
            mCartListSubject.onNext(ordersList);
        }
    }

    public void cleanCartList() {
        for (Iterator<Meal> it = ordersList.iterator(); it.hasNext();) {
            Meal item = it.next();
            if (item.getQuantity() == 0) {
                it.remove();
            }
        }
    }
}


