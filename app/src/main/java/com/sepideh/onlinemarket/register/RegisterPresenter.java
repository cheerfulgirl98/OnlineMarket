package com.sepideh.onlinemarket.register;

import android.util.Log;

import com.sepideh.onlinemarket.data.Sms;
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
 * Created by pc on 5/2/2019.
 */

public class RegisterPresenter implements RegisterContract.MyPresentr {
    RegisterContract.MyFragmentView myView;
    RegisterContract.MyModel myModel;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String errorText;


    public RegisterPresenter(RegisterContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(RegisterContract.MyFragmentView view) {

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

                            myView.successfulSendingSms();}

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

                        if (e instanceof HttpException) {

                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                errorText = responseBody.string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        } else if (e instanceof IOException)
                            Log.d("ggg", "onError: ");


                    }
                });
    }
}
