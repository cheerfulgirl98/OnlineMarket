package com.sepideh.onlinemarket.base_activity;

import android.widget.Button;

import com.sepideh.onlinemarket.base.BaseActivity;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.Single;

/**
 * Created by pc on 5/14/2019.
 */

public interface BaseActivityContract {

    interface MyView extends BaseActivity {
        void successfulLogin(UserInfo userInfo);
        void userNotFound();
        void passwordIsWrong();

    }
    interface MyPresenter {
        void attachView(BaseActivityContract.MyView myView);
        void detachView();
        void loginToApp( String phoneNumber, String password);

    }
    interface MyModel{
        Single<UserInfo> loginToApp(String phoneNumber, String password);


    }
}
