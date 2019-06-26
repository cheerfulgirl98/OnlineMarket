package com.sepideh.onlinemarket.second.compose;

import com.sepideh.onlinemarket.utils.Constant;

import io.reactivex.Completable;

/**
 * Created by pc on 5/9/2019.
 */

public class ComposeModel implements ComposeContract.MyModel {
    @Override
    public Completable sendComment(String productId,String userId,String star,String description) {
        return Constant.apiService.sendComment(productId, userId, star, description);
    }
}
