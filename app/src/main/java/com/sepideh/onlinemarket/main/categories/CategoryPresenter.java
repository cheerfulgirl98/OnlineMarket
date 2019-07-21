package com.sepideh.onlinemarket.main.categories;

import android.util.Log;

import com.sepideh.onlinemarket.data.Category;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pc on 5/18/2019.
 */

public class CategoryPresenter implements CategoryContract.MyPresenter {
    CategoryContract.MyView myView;
    CategoryContract.MyModel myModel;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    public CategoryPresenter(CategoryContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(CategoryContract.MyView view) {

        this.myView=view;
    }

    @Override
    public void detachView() {

        this.myView=null;
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void getChildern() {

        myModel.getChildern().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Category>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Category> categories) {

                        myView.childrenAreReady(categories);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("myyy", "onError: "+ e.getMessage());
                    }
                });
    }
}
