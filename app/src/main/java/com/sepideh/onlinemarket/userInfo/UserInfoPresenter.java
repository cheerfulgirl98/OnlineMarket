package com.sepideh.onlinemarket.userInfo;

import com.sepideh.onlinemarket.data.UserInfo;

public class UserInfoPresenter implements UserInfoContract.MyPresenter {

    UserInfoContract.MyView myView;
    @Override
    public void attachView(UserInfoContract.MyView myView) {
        this.myView=myView;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void sendInfo(UserInfo userInfo) {

    }
}
