package com.sepideh.onlinemarket.third.activity;

import com.sepideh.onlinemarket.base.BaseActivity;
import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.Single;

/**
 * Created by pc on 5/14/2019.
 */

public interface ThirdContract {

    interface MyView extends BaseActivity {

    }
    interface MyPresentr {
        void attachView(ThirdContract.MyView myView);

        void detachView();

        void loginToApp(String phoneNumber, String password);
    }
    interface MyModel{
        Single<UserInfo> loginToApp(String phoneNumber, String password);

    }
}
