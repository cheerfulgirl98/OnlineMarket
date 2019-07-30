package com.sepideh.onlinemarket.second.detail;

import android.util.Log;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by pc on 4/13/2019.
 */

public class DetailPresenter implements DetailContract.MyPresenter {
    DetailContract.MyFragmentView myView;
    DetailContract.MyModel myModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String errorText;


    public DetailPresenter(DetailContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(DetailContract.MyFragmentView view) {

        this.myView = view;

    }

    @Override
    public void detachView() {

        this.myView = null;
        if (compositeDisposable != null & compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }
    }



    @Override
    public void getProductDetails(String id) {
        myModel.getProductDetails(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProductInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(ProductInfo productDetails) {

                        myView.showDetail(productDetails);


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
                            Log.d("ffff", "onError: ");

                    }


                });
    }

    @Override
    public void saveToFavorit(SqliteHelper sqliteHelper, ProductInfo selectedProduct) {
        myModel.saveToFavorit(sqliteHelper, selectedProduct);
    }

    @Override
    public void addToSabadClicked(ProductInfo productInfo) {
        Sabad existedSabad = myModel.searchInSabad(productInfo);
        if (existedSabad != null) {
            myModel.updatedSabad(existedSabad);
            myView.sabadUpdated();
        } else {
            myModel.saveToSabad(productInfo);
            myView.proAddedToSabad();


        }

    }


}
