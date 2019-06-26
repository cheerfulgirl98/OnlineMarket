package com.sepideh.onlinemarket.second.detail;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 4/13/2019.
 */

public interface DetailContract {

    interface MyView extends BaseView{
        void showDetail(ProductInfo productDetailsList);
        void showError();
        void sabadUpdated();
        void proAddedToSabad();



    }

    interface MyPresenter extends BasePresenter<MyView>{

        void getProductDetails(String id);
        void saveToFavorit(SqliteHelper sqliteHelper,ProductInfo selectedProduct);
        void addToSabadClicked(ProductInfo productInfo);


    }

    interface MyModel{
        Single<ProductInfo> getProductDetails(String id);
        void saveToFavorit(SqliteHelper sqliteHelper,ProductInfo selectedProduct);
        Sabad searchInSabad(ProductInfo productInfo);
        void updatedSabad(Sabad sabad);
        void saveToSabad(ProductInfo productInfo);


    }
}
