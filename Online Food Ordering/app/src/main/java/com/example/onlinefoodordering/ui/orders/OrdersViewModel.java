package com.example.onlinefoodordering.ui.orders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinefoodordering.firebase.firestore.FirestoreManages;
import com.example.onlinefoodordering.model.Meal;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class OrdersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private FirestoreManages manages;

    private MutableLiveData<List<Meal>> orderList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    private MutableLiveData<Double> grandTotal = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private List<Meal> list;

    CompositeDisposable disposable;
    Single<List<Meal>> single;


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

    @Inject
    public OrdersViewModel(FirestoreManages firestoreManages) {
        manages = firestoreManages;
        disposable = new CompositeDisposable();
    }


}
