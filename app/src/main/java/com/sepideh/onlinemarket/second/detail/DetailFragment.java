package com.sepideh.onlinemarket.second.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.CommentsAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.Comment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.second.comments.CommentsFragment;
import com.sepideh.onlinemarket.second.compose.ComposeFragment;
import com.sepideh.onlinemarket.sqlite.Favorit;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;
import com.sepideh.onlinemarket.third.activity.ThirdActivity;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.List;

/**
 * Created by pc on 4/11/2019.
 */

public class DetailFragment extends BaseFragment implements DetailContract.MyView, BaseSliderView.OnSliderClickListener, View.OnClickListener {
    DetailContract.MyPresenter myPresenter;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout sabad;
    ProductInfo selectedProduct;
    SliderLayout sliderLayout;
    TextView name, brand, size, material, price, disount, commentT, allComents, compose, badgeNotif, toolbarTitle;
    SimpleRatingBar ratingBar;
    RecyclerView recyclerView;
    RelativeLayout commentBox, noCommentBox;
    ImageView share, favoritIcon, back;
    Button addSabad;

    int star = 0;
    FragmentTransaction fragmentTransaction;

    String productId;
    SqliteHelper sqliteHelper;

    int sabadSizeV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new DetailPresenter(new DetailModel());

        selectedProduct = Hawk.get(getString(R.string.Hawk_selected_product));
        productId = selectedProduct.getId();


    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);

        myPresenter.getProductDetails(productId);

//        //after setupview
//        Cursor cursor = sqliteHelper.getAllFavorit();
//        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//            String pro_id = cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_PRODUCT_ID));
//            if (pro_id.equals(productId))
//                favoritIcon.setColorFilter(Color.argb(255, 255, 213, 79));
//        }

        List<Favorit> favorits=sqliteHelper.getAllFavorit();
        for(Favorit theFavorit:favorits){
            String pro_id=theFavorit.getProduct_id();
            if(pro_id.equals(productId))
                favoritIcon.setColorFilter(Color.argb(255, 255, 213, 79));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        manageSabad();

    }

    @Override
    public void onStop() {

        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void setUpViews() {

        coordinatorLayout = rootView.findViewById(R.id.cor_detail_main);
        badgeNotif = rootView.findViewById(R.id.badge_notif);
        toolbarTitle = rootView.findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(R.string.product_features);
        sabad = rootView.findViewById(R.id.rel_toolbar_sabad);
        sabad.setOnClickListener(this);
        back = rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);

        sqliteHelper = new SqliteHelper(getViewContext());
        share = rootView.findViewById(R.id.img_detail_share);
        share.setOnClickListener(this);
        favoritIcon = rootView.findViewById(R.id.img_detail_favorit);
        favoritIcon.setOnClickListener(this);
        sliderLayout = rootView.findViewById(R.id.slider_detail);
        name = rootView.findViewById(R.id.txv_detail_name);
        brand = rootView.findViewById(R.id.txv_detail_brand);
        size = rootView.findViewById(R.id.txv_detail_size);
        material = rootView.findViewById(R.id.txv_detail_materail);
        price = rootView.findViewById(R.id.txv_detail_price);
        disount = rootView.findViewById(R.id.txv_detail_discount);
        ratingBar = rootView.findViewById(R.id.star_detail);
        commentT = rootView.findViewById(R.id.txv_detail_commentT);
        recyclerView = rootView.findViewById(R.id.rec_detail_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext(), RecyclerView.VERTICAL, false));
        allComents = rootView.findViewById(R.id.txv_detail_allcomments);
        allComents.setOnClickListener(this);

        commentBox = rootView.findViewById(R.id.rel_detail_commentBox);
        noCommentBox = rootView.findViewById(R.id.rel_detail_noCommentBox);
        compose = rootView.findViewById(R.id.txv_detail_compose);
        compose.setOnClickListener(this);

        addSabad = rootView.findViewById(R.id.btn_buttom);
        addSabad.setText(R.string.btn_add_to_sabad);
        addSabad.setOnClickListener(this);

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showDetail(ProductInfo productDetails) {

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        List<String> sliderUrl = productDetails.getSlider();

        for (int j = 0; j < sliderUrl.size(); j++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
            defaultSliderView
                    .image(sliderUrl.get(j))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(this);

            defaultSliderView.bundle(new Bundle());
            sliderLayout.addSlider(defaultSliderView);
        }

        name.setText(selectedProduct.getName());
        if (!selectedProduct.getBrand().equals(""))
            brand.setText(selectedProduct.getBrand());
        else brand.setText("مدل :" + selectedProduct.getModel());
        if (!productDetails.getSize().equals("")) {
            size.setVisibility(View.VISIBLE);
            size.setText(productDetails.getSize());
        }
        material.setText(productDetails.getMaterial());
        if (selectedProduct.getPrice() != 0) {

            price.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", selectedProduct.getPrice(), getString(R.string.toman))));
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else price.setText("");

        if (selectedProduct.getDiscount() != null) {

            int discount = Integer.parseInt(selectedProduct.getDiscount());
            disount.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", discount, getString(R.string.toman))));

        }

        List<Comment> comments = productDetails.getComments();
        if (comments.size() > 0) {
            commentBox.setVisibility(View.VISIBLE);
            noCommentBox.setVisibility(View.GONE);

            for (int i = 0; i < comments.size(); i++) {
                star += Integer.parseInt(comments.get(i).getStar());

            }

            int average = star / comments.size();
            ratingBar.setRating(average);
            commentT.setText("از مجموع " + comments.size() + " رای ثبت شده ");

            CommentsAdapter adapter = new CommentsAdapter(comments, getViewContext());
            recyclerView.setAdapter(adapter);
        } else {

            commentBox.setVisibility(View.GONE);
            noCommentBox.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void showError() {

    }


    private void manageSabad() {
        sabadSizeV = Hawk.get(getString(R.string.Hawk_sabad_size), -1);
        if (sabadSizeV > 0) {
            //when come back from basketfragment and change sth there, set size from hawk
            setSabadSizeOnBadge(sabadSizeV);
        } else if (sabadSizeV == 0) {
            //when come back from basketfragment and make sabad empty there.
            sabadIsEmpty();
        } else{
            //for the first time that sabad is empty and hawk is null(-1)
            sabadSizeV = 0;
            sabadIsEmpty();}

    }


    private void setSabadSizeOnBadge(int sabadSize) {
        badgeNotif.setVisibility(View.VISIBLE);
        badgeNotif.setText(String.valueOf(sabadSize));
        Hawk.put(getString(R.string.Hawk_sabad_size), sabadSize);
    }


    private void sabadIsEmpty() {
        badgeNotif.setVisibility(View.GONE);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == sabad.getId()) {
            Intent intent = new Intent(getActivity(), ThirdActivity.class);
            startActivity(intent);
        } else if (view.getId() == back.getId()) {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                getActivity().onBackPressed();
            }
        } else if (view.getId() == R.id.img_detail_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.digistyle.com/product/1349441-%DA%A9%DB%8C%D9%81-%D8%AF%D9%88%D8%B4%DB%8C-%D8%B1%D9%88%D8%B2%D9%85%D8%B1%D9%87-%D8%AF%D8%AE%D8%AA%D8%B1%D8%A7%D9%86%D9%87");
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
        } else if (view.getId() == favoritIcon.getId()) {

            myPresenter.saveToFavorit(sqliteHelper, selectedProduct);
            favoritIcon.setColorFilter(Color.argb(255, 255, 213, 79));
        } else if (view.getId() == R.id.txv_detail_allcomments) {

            PublicMethods.goNewFragment(getViewContext(), R.id.frame_second_container, new CommentsFragment());

        } else if (view.getId() == R.id.txv_detail_compose) {
            if (!PublicMethods.checkLogin()) {
                openLogin.openLoginButtomsheet();

            } else {
                PublicMethods.goNewFragment(getViewContext(), R.id.frame_second_container, new ComposeFragment());

            }
        } else if (view.getId() == addSabad.getId()) {
            myPresenter.addToSabadClicked(selectedProduct);
        }
    }


    @Override
    public void sabadUpdated() {


    }

    @Override
    public void proAddedToSabad() {

        PublicMethods.setSnackbar(coordinatorLayout, "محصول به سبد خرید شما افزوده شد", getViewContext().getResources().getColor(R.color.green));
        sabadSizeV=sabadSizeV+1;
        setSabadSizeOnBadge(sabadSizeV );
        Hawk.put(getString(R.string.Hawk_sabad_size),sabadSizeV);

    }


    //    private OpenLogin openLogin;
//    public interface OpenLogin{
//        void openLoginButtomsheet();
//    }
//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        openLogin=(OpenLogin) context;
    }
}
