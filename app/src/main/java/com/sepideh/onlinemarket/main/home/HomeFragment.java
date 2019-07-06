package com.sepideh.onlinemarket.main.home;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.ProductAdapter;
import com.sepideh.onlinemarket.adapter.SuggestionAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;
import com.sepideh.onlinemarket.data.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 4/6/2019.
 */

public class HomeFragment extends BaseFragment implements HomeContract.MyView, BaseSliderView.OnSliderClickListener {



    HomeContract.MyPresenter myPresenter;
    SliderLayout sliderLayout;
    ArrayList<String> sliderArrayList = new ArrayList<>();
    RecyclerView sugRecycler, bestRecycler, newRecycler;
    SuggestionAdapter suggestionAdapter;
    ProductAdapter productAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        myPresenter = new HomePresenter(new HomeModel());
    }


    @Override
    public void setUpViews() {


        sliderLayout = rootView.findViewById(R.id.slider_home);
        sugRecycler = rootView.findViewById(R.id.rec_home_suggestions);
        sugRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        bestRecycler = rootView.findViewById(R.id.rec_home_best);
        bestRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecycler = rootView.findViewById(R.id.rec_home_new);
        newRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
        Log.d("fff", "onStart: home");
    }

    @Override
    public void onStop() {
        Log.d("fff", "onStop: home");
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showSlider(List<Slider> sliderList) {

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        for (int i = 0; i < sliderList.size(); i++) {
            sliderArrayList.add(sliderList.get(i).getUrl());
        }
        for (int j = 0; j < sliderArrayList.size(); j++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
            defaultSliderView
                    .image(sliderArrayList.get(j))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            defaultSliderView.bundle(new Bundle());
            sliderLayout.addSlider(defaultSliderView);
        }
    }

    @Override
    public void showSuggestionList(List<ProductInfo> suggestedProductList) {

        fillSuggestionList(sugRecycler, suggestedProductList);

    }

    @Override
    public void showBestList(List<ProductInfo> bestProductList) {

        fillList(bestRecycler, bestProductList);

    }

    @Override
    public void showNewList(List<ProductInfo> newProductList) {

        fillList(newRecycler, newProductList);
    }

    @Override
    public void showError() {

    }

    void fillSuggestionList(RecyclerView view, List<ProductInfo> productInfos) {
        suggestionAdapter = new SuggestionAdapter(getViewContext(), productInfos);
        view.setAdapter(suggestionAdapter);

    }

    void fillList(RecyclerView view, List<ProductInfo> productInfos) {
        productAdapter = new ProductAdapter(getViewContext(), productInfos);
        view.setAdapter(productAdapter);

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


}
