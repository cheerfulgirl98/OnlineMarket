package com.sepideh.onlinemarket.second.activity;


import android.content.Intent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.base_activity.TheBaseActivity;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.second.compose.ComposeFragment;
import com.sepideh.onlinemarket.second.detail.DetailFragment;
import com.sepideh.onlinemarket.utils.PublicMethods;

public class SecondActivity extends TheBaseActivity implements BaseFragment.OpenLogin {

    CoordinatorLayout coordinatorLayout;
    FragmentTransaction fragmentTransaction;
    ProductInfo selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setUpViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("selected_product_bundle");
        if (bundle != null) {
            selectedProduct = (ProductInfo) bundle.getSerializable("selected_product");
            Hawk.put(getString(R.string.Hawk_selected_product), selectedProduct);


        }
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    public void setUpViews() {
        coordinatorLayout = findViewById(R.id.cor_second);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_second_container, new DetailFragment());
        fragmentTransaction.commit();
    }


    @Override
    public void infoActivityToOpenLogin() {
        openButtomsheetLogin();
    }

    @Override
    public void successfulLogin(UserInfo userInfo) {
        super.successfulLogin(userInfo);
        PublicMethods.goNewFragment(this, R.id.frame_second_container, new ComposeFragment());
    }
}
