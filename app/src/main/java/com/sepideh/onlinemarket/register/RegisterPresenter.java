package com.sepideh.onlinemarket.register;

import android.util.Log;

import com.sepideh.onlinemarket.data.Sms;
import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pc on 5/2/2019.
 */

public class RegisterPresenter implements RegisterContract.MyPresentr {
    RegisterContract.MyView myView;
    RegisterContract.MyModel myModel;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public RegisterPresenter(RegisterContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(RegisterContract.MyView view) {

        this.myView=view;
    }

    @Override
    public void detachView() {

        this.myView=null;
        if(compositeDisposable!=null & compositeDisposable.size()>0){
            compositeDisposable.clear();
        }
    }


    @Override
    public void sendCode(String template, String phoneNumber, String code) {
        myModel.sendCode(template,phoneNumber,code).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Sms>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Sms sms) {

                        Log.d("mytag", "onSuccess: "+sms.getReturn().getStatus() );
                        String statuse= String.valueOf(sms.getReturn().getStatus());

                        if(statuse.equals("200")){
                            Log.d("mytag", "onSuccess: ");
                            myView.successfulSendingSms();}

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void registerUser(String userName, String phoneNumber, String password) {

        myModel.registerUser(userName,phoneNumber,password).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        myView.successfulRegistration(userInfo);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("mytag", "onError: register"+e.toString());

                    }
                });
    }
}
