package com.sepideh.onlinemarket.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.base_activity.TheBaseActivity;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.main.categories.CategoryFragment;
import com.sepideh.onlinemarket.main.favorit.FavoritFragment;
import com.sepideh.onlinemarket.main.home.HomeFragment;
import com.sepideh.onlinemarket.navigationview.NavigationActivity;
import com.sepideh.onlinemarket.navigationview.messages.MessageActivity;
import com.sepideh.onlinemarket.third.activity.ThirdActivity;
import com.sepideh.onlinemarket.userInfo.UserInfoActivity;
import com.sepideh.onlinemarket.utils.MyApplication;
import com.sepideh.onlinemarket.utils.PublicMethods;

public class MainActivity extends TheBaseActivity implements  View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    CoordinatorLayout coordinatorLayout;
    RelativeLayout sabad;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View navHeader;
    ImageView burgerMenu, back;
    TextView toolbarTitle, badgeNotif,usernameDrwr,phonenumberDrawer;
    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;

    Context mContext = this;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();

        Intent intent = getIntent();
        if (intent != null) {
            String registered = intent.getStringExtra("newregistered");
            if (registered != null && registered.equals("yes")) {
                PublicMethods.setSnackbar(coordinatorLayout, "به آنلاین مارکت خوش آمدید.", R.color.green);
            }

        }

    }


    @Override
    public void setUpViews() {
        coordinatorLayout = findViewById(R.id.cor_main);
        sabad = findViewById(R.id.rel_detail_sabad);
        sabad.setOnClickListener(this);
        badgeNotif = findViewById(R.id.badge_notif);
        drawerLayout = findViewById(R.id.drawer);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        burgerMenu = findViewById(R.id.img_toolbar_burgerMenu);
        burgerMenu.setOnClickListener(this);

        back = findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);

        toolbarTitle = findViewById(R.id.txv_toolbar_title);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader=navigationView.getHeaderView(0);

        usernameDrwr=navHeader.findViewById(R.id.txv_username_drawer);
        phonenumberDrawer=navHeader.findViewById(R.id.txv_phonenumber_drawer);


        bottomNavigationView = findViewById(R.id.bottm_main_navigate);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main_container, new HomeFragment());
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        burgerMenu.setVisibility(View.VISIBLE);
                        back.setVisibility(View.GONE);
                        toolbarTitle.setVisibility(View.GONE);
                        PublicMethods.goNewFragment(mContext, R.id.frame_main_container, new HomeFragment());
                        break;

                    case R.id.bottom_categories:
                        burgerMenu.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        toolbarTitle.setVisibility(View.VISIBLE);
                        toolbarTitle.setText("دسته بندی محصولات");
                        PublicMethods.goNewFragment(mContext, R.id.frame_main_container, new CategoryFragment());
                        break;

                    case R.id.bottom_favorit:
                        burgerMenu.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        toolbarTitle.setVisibility(View.VISIBLE);
                        toolbarTitle.setText("محصولات مورد علاقه");
                        PublicMethods.goNewFragment(mContext, R.id.frame_main_container, new FavoritFragment());
                        break;

                }

                return true;

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == sabad.getId()) {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        } else if (view.getId() == burgerMenu.getId()) {
            if (view.getId() == burgerMenu.getId()) {
                if (!PublicMethods.checkLogin())
                    openButtomsheetLogin();

                else {
                    if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                        UserInfo userInfoV=Hawk.get(getString(R.string.loginUserInfoTag));
                        usernameDrwr.setText(userInfoV.getUserName());
                        phonenumberDrawer.setText(userInfoV.getPhoneNumber());


                    }
                }
            }
        } else if (view.getId() == back.getId())
            onBackPressed();
    }

    @Override
    public void successfulLogin(UserInfo userInfo) {

        super.successfulLogin(userInfo);
        drawerLayout.openDrawer(Gravity.RIGHT);
        usernameDrwr.setText(userInfo.getUserName());
        phonenumberDrawer.setText(userInfo.getPhoneNumber());

    }


    @Override
    public void onBackPressed() {


        if (bottomNavigationView.getSelectedItemId() == R.id.bottom_home) {

            finish();
        } else {

//            PublicMethods.goNewFragment(mContext, R.id.frame_main_container, new HomeFragment());
//            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("pppd", "onResume: main");
        PublicMethods.setBadgeNotif(this, badgeNotif);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_edit_info:
                intent = new Intent(this, UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_user_orders:

                intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_messages:

                intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                Hawk.put(getString(R.string.loginUserInfoTag), null);
                break;
        }
        // navigationView.setCheckedItem(id);
        drawerLayout.closeDrawers();
        return false;
    }




}
