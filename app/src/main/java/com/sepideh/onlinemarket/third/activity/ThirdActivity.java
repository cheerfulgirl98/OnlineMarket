package com.sepideh.onlinemarket.third.activity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.base_activity.TheBaseActivity;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.third.form.OrderFormFragment;
import com.sepideh.onlinemarket.third.sabad.BasketFragment;
import com.sepideh.onlinemarket.utils.PublicMethods;


public class ThirdActivity extends TheBaseActivity implements View.OnClickListener, BasketFragment.ManageToolbarI, OrderFormFragment.ManageToolbarI, BaseFragment.OpenLogin {


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

        super.successfulLogin(userInfo);
        PublicMethods.goNewFragment(this, R.id.frame_third_container, new OrderFormFragment(), getString(R.string.orderFormFragTag));


    }


    @Override
    public void passwordIsWrong() {
        passwordError.setVisibility(View.VISIBLE);
        passwordError.setText("رمز عبور وارد شده صحیح نمی باشد.");
    }


    private void manageToolbarTitl() {

        Fragment frag = fragmentManager.findFragmentById(R.id.frame_third_container);

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
        manageToolbarTitl();
    }


    @Override
    public void setToolbarTitle() {
        manageToolbarTitl();
    }



}
