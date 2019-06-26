package com.sepideh.onlinemarket.main.home;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;
import com.sepideh.onlinemarket.utils.Constant;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 4/6/2019.
 */

public class HomeModel implements HomeContract.MyModel {
    @Override
    public Single<List<Slider>> getSliderList() {

        return Constant.apiService.getSliderList();
    }

    @Override
    public Single<List<ProductInfo>> getSuggestionList() {

        return Constant.apiService.getSuggestions();
    }

    @Override
    public Single<List<ProductInfo>> getBestList() {
        return Constant.apiService.getBestList();
    }

    @Override
    public Single<List<ProductInfo>> getNewList() {
        return Constant.apiService.getNewList();
    }


}
