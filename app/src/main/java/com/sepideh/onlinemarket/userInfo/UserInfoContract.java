package com.sepideh.onlinemarket.userInfo;

import com.sepideh.onlinemarket.base.BaseActivity;
import com.sepideh.onlinemarket.data.UserInfo;

import io.reactivex.Single;

public interface UserInfoContract {

    interface MyView extends BaseActivity{
        void userInfoUpdated(UserInfo refreshedUserInfo);
    }

    interface MyPresenter{
        void attachView(UserInfoContract.MyView myView);
        void detachView();
        void updateUserInfo(UserInfo updatedUserInfo);

    }
    interface MyModel{
        Single<UserInfo> updateUserInfo(String phone_num,String name,String family,String tell,String jensiat );
    }
}
