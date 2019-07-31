package com.sepideh.onlinemarket.base_activity;

import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.Constant;

import io.reactivex.Single;

/**
 * Created by pc on 5/14/2019.
 */

public class BaseActivityModel implements BaseActivityContract.MyModel {


    @Override
    public Single<UserInfo> loginToApp(String phoneNumber, String password) {
        return Constant.apiService.login(phoneNumber,password);
    }



}
