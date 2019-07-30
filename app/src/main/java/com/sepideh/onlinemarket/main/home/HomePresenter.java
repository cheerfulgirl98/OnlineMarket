package com.sepideh.onlinemarket.main.home;

import android.util.Log;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;

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
 * Created by pc on 4/6/2019.
 */

public class HomePresenter implements HomeContract.MyPresenter {
    HomeContract.MyFragmentView myView;
    HomeContract.MyModel myModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    String errorText;


    public HomePresenter(HomeContract.MyModel myModel) {
        this.myModel = myModel;
    }


    @Override
    public void getSliderList() {
        myModel.getSliderList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Slider>>() {
            @Override
            public void onSubscribe(Disposable d) {

                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<Slider> sliderList) {
                myView.showSlider(sliderList);

            }

            @Override
            public void onError(Throwable e) {
                myView.noServerConnection();

            }
        });
    }

    @Override
    public void getSuggestionList() {
        myModel.getSuggestionList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProductInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<ProductInfo> suggestedProductList) {
                        Log.d("gav", "onSuccess: " + suggestedProductList.toString());
                        myView.showSuggestionList(suggestedProductList);

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

    @Override
    public void getBestList() {
        myModel.getBestList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProductInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<ProductInfo> productInfos) {

                        myView.showBestList(productInfos);
                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });

    }

    @Override
    public void getNewList() {

        myModel.getNewList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProductInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<ProductInfo> productInfos) {

                        myView.showNewList(productInfos);
                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
    }


    @Override
    public void attachView(HomeContract.MyFragmentView myView) {
        this.myView = myView;

    }

    @Override
    public void detachView() {

        myView = null;
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }

    }
}
