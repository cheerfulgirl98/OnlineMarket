package com.sepideh.onlinemarket.children;

import android.util.Log;

import com.sepideh.onlinemarket.data.ProductInfo;

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
 * Created by pc on 5/19/2019.
 */

public class ChildernPresenter implements ChildrenContract.MyPresenter {
    ChildrenContract.MyView myView;
    ChildrenContract.MyModel myModel;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    String errorText;

    public ChildernPresenter(ChildrenContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(ChildrenContract.MyView view) {
        this.myView=view;

    }

    @Override
    public void detachView() {

        this.myView=null;
        if(compositeDisposable!=null && compositeDisposable.size()>0)
            compositeDisposable.clear();
    }

    @Override
    public void getChildrenProducts(final String catChild, int catHeader) {

        myModel.getChildrenProduct(catChild,catHeader).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProductInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<ProductInfo> productInfos) {

                        myView.showChildrenProducts(productInfos);
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
                            if (errorText.equals("user not found")) {
                                myView.userNotFound();
                            } else
                                myView.passwordIsWrong();
                        } else if (e instanceof IOException)
                            myView.noServerConnection();
                    }
                });
    }
}
