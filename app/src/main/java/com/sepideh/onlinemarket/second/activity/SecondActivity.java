package com.sepideh.onlinemarket.second.activity;


import android.content.Intent;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.base.TheBaseActivity;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.networkerror.MyReceiver;
import com.sepideh.onlinemarket.second.compose.ComposeFragment;
import com.sepideh.onlinemarket.second.detail.DetailFragment;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.third.sabad.BasketFragment;
import com.sepideh.onlinemarket.utils.PublicMethods;

public class SecondActivity extends TheBaseActivity implements SecondContract.MyView, BaseFragment.OpenLogin {

    CoordinatorLayout coordinatorLayout;
    SecondContract.MyPresentr myPresenter;
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
        coordinatorLayout=findViewById(R.id.cor_second);
        myPresenter= new SecondPresenter(new SecondModel());
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
        Hawk.put(getString(R.string.loginUserInfoTag),userInfo);
        bottomSheetDialog.dismiss();
        PublicMethods.goNewFragment(this,R.id.frame_second_container,new ComposeFragment());


    }

    @Override
    public void userNotFound() {

    }

    @Override
    public void passwordIsWrong() {

    }

    @Override
    public void noNetworkConnection() {
        PublicMethods.setSnackbar(findViewById(R.id.cor_second),getString(R.string.error_network_conection),getResources().getColor(R.color.red),"تلاش مجدد",getResources().getColor(R.color.white));
    }

    @Override
    public void noServerConnection() {
        PublicMethods.setSnackbar(findViewById(R.id.cor_second),getString(R.string.error_server_conection),getResources().getColor(R.color.red),"تلاش مجدد",getResources().getColor(R.color.white));

    }

    @Override
    public void sendLoginRequest() {
        myPresenter.loginToApp(phoneNumberV, passwordV);
    }


    @Override
    protected void onStart() {
        super.onStart();
        myPresenter.attachView(this);

    }


    @Override
    protected void onStop() {
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void onActionConnection() {
        sendLoginRequest();
    }

    @Override
    public void onActionNoConnection() {

        noNetworkConnection();
    }


//    @Override
//    public void onActionConnection() {
//
//    }
}
