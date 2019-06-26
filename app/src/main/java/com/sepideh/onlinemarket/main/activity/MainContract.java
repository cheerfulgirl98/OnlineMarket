package com.sepideh.onlinemarket.main.activity;

import com.sepideh.onlinemarket.base.BaseActivity;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.Single;

/**
 * Created by pc on 5/14/2019.
 */

public interface MainContract {

    interface MyView extends BaseActivity{

    }
    interface MyPresentr{
        void attachView(MainContract.MyView myView);
        void detachView();
        void loginToApp(String phoneNumber,String password);

    }
    interface MyModel{
        Single<UserInfo> loginToApp(String phoneNumber, String password);
        void saveToSabad(ProductInfo productInfo);

    }
}
