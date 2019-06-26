package com.sepideh.onlinemarket.second.detail;

import android.util.Log;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pc on 4/13/2019.
 */

public class DetailPresenter implements DetailContract.MyPresenter {
    DetailContract.MyView myView;
    DetailContract.MyModel myModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    public DetailPresenter(DetailContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(DetailContract.MyView view) {

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
                        myView.showError();

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
            Log.d("rrr", "addToSabadClicked: ");
            myModel.updatedSabad(existedSabad);
            myView.sabadUpdated();
        } else {
            myModel.saveToSabad(productInfo);
            myView.proAddedToSabad();


        }

    }


}
