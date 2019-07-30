package com.sepideh.onlinemarket.main.home;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.ProductAdapter;
import com.sepideh.onlinemarket.adapter.SuggestionAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 4/6/2019.
 */

public class HomeFragment extends BaseFragment implements HomeContract.MyFragmentView, BaseSliderView.OnSliderClickListener {

    HomeContract.MyPresenter myPresenter;
    SliderLayout sliderLayout;
    ArrayList<String> sliderArrayList = new ArrayList<>();
    RecyclerView sugRecycler, bestRecycler, newRecycler;
    SuggestionAdapter suggestionAdapter;
    ProductAdapter productAdapter;


    @Override
    public void setUpViews() {

        myPresenter = new HomePresenter(new HomeModel());

        sliderLayout = rootView.findViewById(R.id.slider_home);
        sugRecycler = rootView.findViewById(R.id.rec_home_suggestions);
        sugRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        bestRecycler = rootView.findViewById(R.id.rec_home_best);
        bestRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecycler = rootView.findViewById(R.id.rec_home_new);
        newRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));


    }


    @Override
    public void onStart() {
        myPresenter.attachView(this);
        super.onStart();

    }


    public void sendServerRequest() {
        myPresenter.getSliderList();
        myPresenter.getSuggestionList();
        myPresenter.getBestList();
        myPresenter.getNewList();
    }


    public void noNetworkConnection() {
        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_home), getString(R.string.error_network_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        openLogin = (OpenLogin) context;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }


    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void noServerConnection() {
        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_home), getString(R.string.error_server_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));
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


    @Override
    public void onActionConnection() {
            sendServerRequest();
    }

    @Override
    public void onActionNoConnection() {
        noNetworkConnection();
    }
}
