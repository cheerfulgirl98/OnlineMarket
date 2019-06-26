package com.sepideh.onlinemarket.main.home;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 4/6/2019.
 */

public interface HomeContract {

    interface MyView extends BaseView{
        void showSlider(List<Slider> sliderList);
        void showSuggestionList(List<ProductInfo> suggestedProductList);
        void showBestList(List<ProductInfo> bestProductList);
        void showNewList(List<ProductInfo> newProductList);
        void showError();

    }
    interface MyPresenter extends BasePresenter<MyView>{
        void getSliderList();
        void getSuggestionList();
        void getBestList();
        void getNewList();

    }
    interface MyModel{
       Single<List<Slider>> getSliderList();
       Single<List<ProductInfo>> getSuggestionList();
        Single<List<ProductInfo>> getBestList();
        Single<List<ProductInfo>> getNewList();



    }
}
