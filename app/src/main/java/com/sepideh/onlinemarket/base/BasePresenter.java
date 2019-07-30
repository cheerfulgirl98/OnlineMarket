package com.sepideh.onlinemarket.base;

/**
 * Created by pc on 4/6/2019.
 */

public interface BasePresenter<T extends BaseFragmentView> {


    void attachView(T view);
    void detachView();
}
