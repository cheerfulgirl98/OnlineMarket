package com.sepideh.onlinemarket.register;

import com.sepideh.onlinemarket.data.Return;
import com.sepideh.onlinemarket.data.Sms;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.Constant;

import io.reactivex.Single;

/**
 * Created by pc on 5/2/2019.
 */

public class RegisterModel implements RegisterContract.MyModel {

    @Override
    public Single<Sms> sendCode(String template, String phoneNumber, String code) {
        return Constant.apiServiceSms.SendCode(template,phoneNumber,code);
    }

    @Override
    public Single<UserInfo> registerUser(String userName, String phoneNumber, String password) {
        return Constant.apiService.register(userName,phoneNumber,password);
    }
}
