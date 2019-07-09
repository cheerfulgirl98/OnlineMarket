package com.sepideh.onlinemarket.userInfo;

import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.Constant;

import io.reactivex.Single;

public class UserInfoModel implements UserInfoContract.MyModel {
    @Override
    public Single<UserInfo> updateUserInfo(String phone_num,String name, String family, String tell, String jensiat) {
        return Constant.apiService.updateUserInfo(phone_num,name,family,tell,jensiat);
    }
}
