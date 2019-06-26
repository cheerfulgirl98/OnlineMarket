package com.sepideh.onlinemarket.base;

import com.sepideh.onlinemarket.data.UserInfo;

/**
 * Created by pc on 5/27/2019.
 */

public interface BaseActivity {
    void setUpViews();
    void successfulLogin(UserInfo userInfo);
    void userNotFound();
    void passwordIsWrong();

}
