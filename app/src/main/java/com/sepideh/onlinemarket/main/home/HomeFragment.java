package com.sepideh.onlinemarket.main.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
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

    TextView suggestionL,bestL,newL;

    ProgressBar progressBar ;

    @Override
    public void setUpViews() {

        myPresenter = new HomePresenter(new HomeModel());

        progressBar = rootView.findViewById(R.id.progress_bar);

        sliderLayout = rootView.findViewById(R.id.slider_home);
        sugRecycler = rootView.findViewById(R.id.rec_home_suggestions);
        sugRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        bestRecycler = rootView.findViewById(R.id.rec_home_best);
        bestRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecycler = rootView.findViewById(R.id.rec_home_new);
        newRecycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));

        suggestionL=rootView.findViewById(R.id.txv_suggestionsL);
        bestL=rootView.findViewById(R.id.txv_bestL);
        newL=rootView.findViewById(R.id.txv_newL);


    }


    @Override
    public void onStart() {
        myPresenter.attachView(this);
        super.onStart();

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
    public void sendServerRequest() {
        progressBar.setVisibility(View.VISIBLE);
        myPresenter.getSliderList();
        myPresenter.getSuggestionList();
        myPresenter.getBestList();
        myPresenter.getNewList();
    }

    public void noNetworkConnection() {
        hideView();
        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_home), getString(R.string.error_network_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));

    }
    private void hideView(){
        suggestionL.setVisibility(View.GONE);
        bestL.setVisibility(View.GONE);
        newL.setVisibility(View.GONE);
    }

    @Override
    public void noServerConnection() {
        hideView();
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
        progressBar.setVisibility(View.GONE);
        suggestionL.setVisibility(View.VISIBLE);
        fillSuggestionList(sugRecycler, suggestedProductList);

    }

    @Override
    public void showBestList(List<ProductInfo> bestProductList) {
        bestL.setVisibility(View.VISIBLE);
        fillList(bestRecycler, bestProductList);

    }

    @Override
    public void showNewList(List<ProductInfo> newProductList) {

        newL.setVisibility(View.VISIBLE);
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
