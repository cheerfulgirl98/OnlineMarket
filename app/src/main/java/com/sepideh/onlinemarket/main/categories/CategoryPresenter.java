package com.sepideh.onlinemarket.main.categories;

import com.sepideh.onlinemarket.data.Category;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by pc on 5/18/2019.
 */

public class CategoryPresenter implements CategoryContract.MyPresenter {
    CategoryContract.MyFragmentView myView;
    CategoryContract.MyModel myModel;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    String errorText;

    public CategoryPresenter(CategoryContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(CategoryContract.MyFragmentView view) {

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

                        if (e instanceof HttpException) {

                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                errorText = responseBody.string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        } else if (e instanceof IOException)
                            myView.noServerConnection();
                    }
                });
    }
}
