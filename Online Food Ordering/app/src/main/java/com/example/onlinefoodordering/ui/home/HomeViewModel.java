package com.example.onlinefoodordering.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinefoodordering.firebase.Callback;
import com.example.onlinefoodordering.firebase.firestore.FirestoreManages;
import com.example.onlinefoodordering.model.Category;
import com.example.onlinefoodordering.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public  CompositeDisposable disposable;
    private FirestoreManages firestoreManages;
    private MutableLiveData<List<Meal>> lists = new MutableLiveData<>();
    private MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> noResultsIndicator = new MutableLiveData<>();
    private MutableLiveData<Boolean> typeLoading = new MutableLiveData<>();

    private MutableLiveData<List<Category>> types = new MutableLiveData<>();

    Single<List<Meal>> single;
    Single<List<Category>> singleCategory;

    private List<Meal> mealList = new ArrayList<>();
    private List<Category> typeList = new ArrayList<>();
    private List<Meal> mealsByCategory = new ArrayList<>();

    private PublishSubject<String> mSearchResultSubject;

    @Inject
    public HomeViewModel(FirestoreManages services) {
        Log.d("HomeViewModel", "is running");
        this.firestoreManages = services;
        disposable = new CompositeDisposable();
        mSearchResultSubject = PublishSubject.create();
        fetchTypes();
        fetchRepos();
        search();
    }

    public FirestoreManages getFirestoreManages() {
        return firestoreManages;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public List<Category> getTypeList() {
        return typeList;
    }

    public List<Meal> getMealsByCategory() {
        return mealsByCategory;
    }

    public LiveData<List<Meal>> getRepos() {
        return lists;
    }

    public LiveData<List<Category>> getTypes() {
        return types;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    LiveData<Boolean> getNoResult() {
        return noResultsIndicator;
    }

    LiveData<Boolean> getTypeLoading(){
        return typeLoading;
    }

    public void fetchRepos() {
        loading.setValue(true);
        firestoreManages.retrieveMeals(new Callback() {
            @Override
            public void onCallback(List<Meal> meals) {
                single = Single.fromCallable(new Callable<List<Meal>>() {
                    @Override
                    public List<Meal> call() throws Exception {
                        return meals;
                    }
                });
                disposable.add(single.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Meal>>() {
                            @Override
                            public void onSuccess(List<Meal> meals) {
                                mealList = meals;
                                repoLoadError.setValue(false);
                                lists.setValue(meals);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("onError ", e.getMessage());
                                repoLoadError.setValue(true);
                                loading.setValue(false);
                            }
                        }));
            }
        });
    }

    public void fetchTypes() {
        typeLoading.setValue(true);
        firestoreManages.retrieveTypes(new Callback() {
            @Override
            public void onCategoryCallback(List<Category> categories) {
                singleCategory = Single.fromCallable(new Callable<List<Category>>() {
                    @Override
                    public List<Category> call() throws Exception {
                        return categories;
                    }
                });
                disposable.add(singleCategory.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Category>>() {
                            @Override
                            public void onSuccess(List<Category> categories) {
                                typeList = categories;
                                types.setValue(categories);
                                repoLoadError.setValue(false);
                                typeLoading.setValue(false);
                            }
                            @Override
                            public void onError(Throwable e) {
                                Log.d("onError ", e.getMessage());
//                                loading.setValue(true);
                                repoLoadError.setValue(true);
                                typeLoading.setValue(false);
                            }
                        }));
            }
        });
    }

    public void search() {
        mSearchResultSubject.debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Function<String, List<Meal>>() {
                    @Override
                    public List<Meal> apply(String s) throws Exception {
                        return query(s);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Meal>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Meal> meals) {
                        if (meals.isEmpty()) {
                            noResultsIndicator.setValue(true);
                        } else {
                            lists.setValue(meals);
                            noResultsIndicator.setValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void inputString(String s) {
        mSearchResultSubject.onNext(s);
    }


    public List<Meal> query(String s) {
        List<Meal> listClone = new ArrayList<>();
        for (Meal meal : mealList) {
            if (meal.getName().toUpperCase().contains(s.toUpperCase())) {
                listClone.add(meal);
            }
        }
        return listClone;
    }

    public List<Meal> mealsByCategory(String categoryId) {
        mealsByCategory = new ArrayList<>();
        for (Meal meal : mealList) {
            if (meal.getType() != null && meal.getType().equals(categoryId)) {
                mealsByCategory.add(meal);
            }
        }
        return mealsByCategory;
    }

}
