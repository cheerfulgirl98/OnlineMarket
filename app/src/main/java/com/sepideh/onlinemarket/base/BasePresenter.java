package com.sepideh.onlinemarket.base;

import android.view.View;

/**
 * Created by pc on 4/6/2019.
 */

public interface BasePresenter<T extends BaseView> {


    void attachView(T view);
    void detachView();
}
