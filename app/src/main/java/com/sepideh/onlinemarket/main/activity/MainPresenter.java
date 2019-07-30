package com.sepideh.onlinemarket.main.activity;

import android.util.Log;

import com.sepideh.onlinemarket.data.UserInfo;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by pc on 5/14/2019.
 */

public class MainPresenter implements MainContract.MyPresentr {

    MainContract.MyView myView;
    MainContract.MyModel myModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String errorText;

    public MainPresenter(MainContract.MyModel myModel) {
        this.myModel = myModel;
    }


    @Override
    public void attachView(MainContract.MyView myView) {
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

        myModel.loginToApp(phoneNumber, password).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(UserInfo userInfo) {
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
