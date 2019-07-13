package com.sepideh.onlinemarket.userInfo;

import android.util.Log;

import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserInfoPresenter implements UserInfoContract.MyPresenter {

    UserInfoContract.MyView myView;
    UserInfoContract.MyModel myModel;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    public UserInfoPresenter(UserInfoContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(UserInfoContract.MyView myView) {
        this.myView=myView;
    }

    @Override
    public void detachView() {
        this.myView=null;
        if(compositeDisposable!=null && compositeDisposable.size()>0)
            compositeDisposable.clear();
    }

    @Override
    public void updateUserInfo(UserInfo updatedUserInfo) {

        myModel.updateUserInfo(updatedUserInfo.getPhoneNumber(),updatedUserInfo.getName(),updatedUserInfo.getFamily(),updatedUserInfo.getTell(),updatedUserInfo.getJensiat())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<UserInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(UserInfo refreshedUserInfo) {

                myView.userInfoUpdated(refreshedUserInfo);
            }

            @Override
            public void onError(Throwable e) {

                Log.d("lll", "onError: "+e.toString());
            }
        });


    }
}
