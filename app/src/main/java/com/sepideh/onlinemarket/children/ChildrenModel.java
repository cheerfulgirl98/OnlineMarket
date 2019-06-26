package com.sepideh.onlinemarket.children;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.utils.Constant;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 5/19/2019.
 */

public class ChildrenModel implements ChildrenContract.MyModel {
    @Override
    public Single<List<ProductInfo>> getChildrenProduct(String catChild,int catHeader) {
        return Constant.apiService.getChildrenProducts(catChild,catHeader);
    }
}
