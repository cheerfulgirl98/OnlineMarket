package com.sepideh.onlinemarket.register;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;
import com.sepideh.onlinemarket.data.Return;
import com.sepideh.onlinemarket.data.Sms;
import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.Single;

/**
 * Created by pc on 5/2/2019.
 */

public interface RegisterContract {
    interface MyView extends BaseView{
        void successfulSendingSms();
        void successfulRegistration(UserInfo userInfo);
    }

    interface MyPresentr extends BasePresenter<MyView>{
        void sendCode(String template,String phoneNumber,String code);
        void registerUser(String userName,String phoneNumber,String password);
    }
    interface MyModel{
        Single<Sms> sendCode(String template, String phoneNumber, String code);
        Single<UserInfo> registerUser(String userName, String phoneNumber, String password);
    }

}
