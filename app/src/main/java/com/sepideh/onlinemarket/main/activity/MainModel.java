package com.sepideh.onlinemarket.main.activity;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.realm.RealmController;
import com.sepideh.onlinemarket.utils.Constant;

import io.reactivex.Single;

/**
 * Created by pc on 5/14/2019.
 */

public class MainModel implements MainContract.MyModel {

    RealmController realmController=new RealmController();
    @Override
    public Single<UserInfo> loginToApp(String phoneNumber, String password) {
        return Constant.apiService.login(phoneNumber,password);
    }

    @Override
    public void saveToSabad(ProductInfo productInfo) {
        realmController.saveOnRealm(productInfo);
    }


}
