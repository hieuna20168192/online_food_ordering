package com.example.onlinefoodordering.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinefoodordering.firebase.Callback;
import com.example.onlinefoodordering.firebase.auth.AuthManages;
import com.example.onlinefoodordering.model.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    public CompositeDisposable disposable;

    private AuthManages authManages;

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    Single<User> single;
    Future<Void> future;

    private User currentUser = new User();

    public LiveData<Boolean> getLoading(){
        return loading;
    }

    public LiveData<User> getUserMutable(){
        return userMutableLiveData;
    }

    @Inject
    public LoginViewModel(AuthManages authManages) {
        this.authManages = authManages;
        disposable = new CompositeDisposable();
    }

    public AuthManages getAuthManages() {
        return authManages;
    }

    private void fetchCurrentUser() {
        loading.setValue(true);
        authManages.getCurrentUser(new Callback() {
            @Override
            public void onGetCurrentUser(User user) {
                single = Single.fromCallable(new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return user;
                    }
                });
                disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<User>() {
                            @Override
                            public void onSuccess(User user) {
                                currentUser = user;
                                userMutableLiveData.setValue(user);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("onError", e.getMessage());
                                loading.setValue(false);
                            }
                        }));
            }
        });
    }

    public void login(String email, String password) throws ExecutionException, InterruptedException {
        loading.setValue(true);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        future = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                authManages.login(email, password);
                loading.setValue(false);
                return null;
            }
        });
//        fetchCurrentUser();
    }

}
