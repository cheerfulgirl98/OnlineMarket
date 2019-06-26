package com.sepideh.onlinemarket.second.detail;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.realm.RealmController;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;
import com.sepideh.onlinemarket.utils.Constant;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 4/13/2019.
 */

public class DetailModel implements DetailContract.MyModel {
    RealmController realmController=new RealmController();

    @Override
    public Single<ProductInfo> getProductDetails(String id) {
        return Constant.apiService.getProductDetails(id);
    }

    @Override
    public void saveToFavorit(SqliteHelper sqliteHelper,ProductInfo selectedProduct) {
        sqliteHelper.insertFavorit(selectedProduct.getId(), selectedProduct.getName(), selectedProduct.getBrand(),selectedProduct.getModel(), selectedProduct.getPrice(), selectedProduct.getDiscount(), selectedProduct.getUrl());

    }

    @Override
    public Sabad searchInSabad(ProductInfo productInfo) {
        return realmController.searchInSabad(productInfo);
    }

    @Override
    public void updatedSabad(Sabad sabad) {

        realmController.plusNum(sabad);
    }


    @Override
    public void saveToSabad( ProductInfo productInfo) {
        realmController.saveOnRealm(productInfo);
            }


}
