package com.sepideh.onlinemarket.main.home;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseFragmentView;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 4/6/2019.
 */

public interface HomeContract {

    interface MyFragmentView extends BaseFragmentView {
        void showSlider(List<Slider> sliderList);
        void showSuggestionList(List<ProductInfo> suggestedProductList);
        void showBestList(List<ProductInfo> bestProductList);
        void showNewList(List<ProductInfo> newProductList);


    }
    interface MyPresenter extends BasePresenter<MyFragmentView>{
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
