package com.sepideh.onlinemarket.second.compose;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseFragmentView;

import io.reactivex.Completable;

/**
 * Created by pc on 5/9/2019.
 */

public interface ComposeContract {

    interface MyFragmentView extends BaseFragmentView {
        void successfulCompose();
        void commentedBefore();
    }

    interface MyPresenter extends BasePresenter<MyFragmentView>{
        void sendComment(String productId,String userId,String star,String description);
    }
    interface MyModel{
        Completable sendComment(String productId,String userId,String star,String description);
    }
}
