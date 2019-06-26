package com.sepideh.onlinemarket.second.compose;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;

import io.reactivex.Completable;

/**
 * Created by pc on 5/9/2019.
 */

public interface ComposeContract {

    interface MyView extends BaseView{
        void successfulCompose();
        void commentedBefore();
    }

    interface MyPresenter extends BasePresenter<MyView>{
        void sendComment(String productId,String userId,String star,String description);
    }
    interface MyModel{
        Completable sendComment(String productId,String userId,String star,String description);
    }
}
