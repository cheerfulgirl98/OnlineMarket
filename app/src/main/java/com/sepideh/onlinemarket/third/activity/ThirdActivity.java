package com.sepideh.onlinemarket.third.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialog;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.base.TheBaseActivity;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.third.form.OrderFormFragment;
import com.sepideh.onlinemarket.third.sabad.BasketFragment;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;


public class ThirdActivity extends TheBaseActivity implements ThirdContract.MyView, View.OnClickListener, BasketFragment.ManageToolbarI,OrderFormFragment.ManageToolbarI,BaseFragment.OpenLogin {

    ThirdContract.MyPresentr myPresenter;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    RelativeLayout sabad;
    TextView toolbarTitle, badgeNotif;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setUpViews();

    }

    @Override
    public void setUpViews() {
        myPresenter = new ThirdPresenter(new ThirdModel());

        sabad = findViewById(R.id.rel_toolbar_sabad);
        badgeNotif = findViewById(R.id.badge_notif);
        PublicMethods.setBadgeNotif(this, badgeNotif);
        back = findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_third_container, new BasketFragment(), getString(R.string.basketfragTag));
        fragmentTransaction.commit();

        toolbarTitle = findViewById(R.id.txv_toolbar_title);

    }

    public void infoActivityToOpenLogin() {

        openButtomsheetLogin();
    }





    @Override
    public void successfulLogin(UserInfo userInfo) {

        Hawk.put(getString(R.string.loginUserInfoTag), userInfo);
        bottomSheetDialog.dismiss();
        PublicMethods.goNewFragment(this, R.id.frame_third_container, new OrderFormFragment(), getString(R.string.orderFormFragTag));


    }

    @Override
    public void userNotFound() {
        phoneNumberError.setVisibility(View.VISIBLE);
        phoneNumberError.setText("شماره تلفن وارد شده ثبت نام نشده است.");
    }

    @Override
    public void passwordIsWrong() {
        passwordError.setVisibility(View.VISIBLE);
        passwordError.setText("رمز عبور وارد شده صحیح نمی باشد.");
    }

    @Override
    public void noNetworkConnection() {

        PublicMethods.setSnackbar(findViewById(R.id.cor_third),getString(R.string.error_network_conection),getResources().getColor(R.color.red),"تلاش مجدد",getResources().getColor(R.color.white));
    }

    @Override
    public void noServerConnection() {
        PublicMethods.setSnackbar(findViewById(R.id.cor_third),getString(R.string.error_server_conection),getResources().getColor(R.color.red),"تلاش مجدد",getResources().getColor(R.color.white));

    }

    @Override
    public void sendLoginRequest() {
        myPresenter.loginToApp(phoneNumberV, passwordV);
    }

    private void manageToolbarTitl() {

        Fragment frag=fragmentManager.findFragmentById(R.id.frame_third_container);

        if (frag != null) {
            if (frag.getTag().equals(getString(R.string.basketfragTag)))
                toolbarTitle.setText(getString(R.string.your_basket));
            else if (frag.getTag().equals(getString(R.string.orderFormFragTag)))
                toolbarTitle.setText(R.string.receiverInfo);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_toolbar_back) {


            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                onBackPressed();
            }
        }
    }

    @Override
    public void proDelete() {
        PublicMethods.setBadgeNotif(this, badgeNotif);

    }



    @Override
    protected void onStart() {
        super.onStart();
        myPresenter.attachView(this);
        manageToolbarTitl();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void setToolbarTitle() {
        manageToolbarTitl();
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
//        sendLoginRequest();
//    }
}
