package com.sepideh.onlinemarket.userInfo;

import com.sepideh.onlinemarket.base.BaseActivity;
import com.sepideh.onlinemarket.data.UserInfo;

public interface UserInfoContract {

    interface MyView extends BaseActivity{}

    interface MyPresenter{
        void attachView(UserInfoContract.MyView myView);
        void detachView();
        void sendInfo(UserInfo userInfo);
    }
}
