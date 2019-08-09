package com.sepideh.onlinemarket.base_activity;

import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;
import com.sepideh.onlinemarket.data.UserInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by pc on 5/14/2019.
 */

public class BaseActivityPresenter implements BaseActivityContract.MyPresenter {

    BaseActivityContract.MyView myView;
    BaseActivityContract.MyModel myModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String errorText;

    public BaseActivityPresenter(BaseActivityContract.MyModel myModel) {
        this.myModel = myModel;
    }


    @Override
    public void attachView(BaseActivityContract.MyView myView) {
        this.myView = myView;
    }

    @Override
    public void detachView() {
        myView = null;
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }

    }

    @Override
    public void loginToApp(String phoneNumber, String password) {

//        RxView.clicks(button).debounce(3000, TimeUnit.MILLISECONDS).subscribe(this::sendReq);
//        this.phoneNumber=phoneNumber;
//        this.password=password;
        myModel.loginToApp(phoneNumber, password).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        Log.d("pppd", "onSuccess: "+ userInfo.toString());
                        myView.successfulLogin(userInfo);

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
                                Log.d("myrx", "onError: " + ((HttpException) e).code());
                                myView.userNotFound();
                            } else
                                myView.passwordIsWrong();
                        } else if (e instanceof IOException)
                            myView.noServerConnection();
                    }
                });



    }



}
