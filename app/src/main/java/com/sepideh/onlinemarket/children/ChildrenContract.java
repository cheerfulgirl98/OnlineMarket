package com.sepideh.onlinemarket.children;

import com.sepideh.onlinemarket.base.BaseActivity;
import com.sepideh.onlinemarket.data.ProductInfo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 5/19/2019.
 */

public interface ChildrenContract {

    interface MyView extends BaseActivity {

       void showChildrenProducts(List<ProductInfo> productInfos);
    }

    interface MyPresenter{
        void attachView(ChildrenContract.MyView myView);
        void detachView();
        void getChildrenProducts(String catChild,int catHeader);
    }

    interface MyModel{
        Single<List<ProductInfo>> getChildrenProduct(String catChild,int catHeader);
    }
}
