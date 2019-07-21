package com.sepideh.onlinemarket.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.main.categories.CategoryFragment;
import com.sepideh.onlinemarket.main.favorit.FavoritFragment;
import com.sepideh.onlinemarket.main.home.HomeFragment;
import com.sepideh.onlinemarket.navigationview.NavigationActivity;
import com.sepideh.onlinemarket.navigationview.messages.MessageActivity;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.third.activity.ThirdActivity;
import com.sepideh.onlinemarket.userInfo.UserInfoActivity;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.MyView, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    MainContract.MyPresentr myPresenter;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout sabad;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView burgerMenu, back;
    TextView toolbarTitle, badgeNotif;
    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;

    BottomSheetDialog bottomSheetDialog;
    View view1;
    EditText phoneNumber, password;
    TextView phoneNumberError, passwordError, forgetPassword;
    String phoneNumberV, passwordV;
    ImageView closeLogin;
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
                PublicMethods.setSnackbar(coordinatorLayout, "به آنلاین مارکت خوش آمدید.", getResources().getColor(R.color.green));
            }

        }

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
    public void setUpViews() {
        myPresenter = new MainPresenter(new MainModel());
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
                    openLoginButtomsheet();

                else {
                    if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
            }
        } else if (view.getId() == back.getId())
            onBackPressed();
    }

    @Override
    public void successfulLogin(UserInfo userInfo) {

        Hawk.put(getString(R.string.loginUserInfoTag), userInfo);
        bottomSheetDialog.dismiss();
        drawerLayout.openDrawer(Gravity.RIGHT);
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



    public void openLoginButtomsheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        view1 = getLayoutInflater().inflate(R.layout.login_layout, null);
        bottomSheetDialog.setContentView(view1);
        phoneNumber = view1.findViewById(R.id.edt_login_phoneNumber);
        password = view1.findViewById(R.id.edt_login_password);
        phoneNumberError = view1.findViewById(R.id.txv_login_phoneNumberError);
        passwordError = view1.findViewById(R.id.txv_login_passwordError);
        forgetPassword = view1.findViewById(R.id.txv_login_forgot);
        forgetPassword.setPaintFlags(forgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        closeLogin = view1.findViewById(R.id.img_login_close);
        closeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();

        Button register = view1.findViewById(R.id.btn_login_goRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button login = view1.findViewById(R.id.btn_login_send);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNumberV = phoneNumber.getText().toString();
                passwordV = password.getText().toString();
                ArrayList<EditText> editTexts = new ArrayList<>();
                editTexts.add(phoneNumber);
                editTexts.add(password);

                if (checkEmptyness(editTexts) & checkValidation()) {
                    sendLoginRequest();

                } else setError(editTexts);
            }
        });
    }

    private boolean checkEmptyness(ArrayList<EditText> editTexts) {

        for (EditText editText : editTexts) {
            if (editText.getText().length() <= 0) {
                return false;

            } else {
                switch (editText.getId()) {
                    case R.id.edt_login_password:
                        passwordError.setVisibility(View.GONE);
                }
            }
        }

        return true;

    }


    private boolean checkValidation() {
        if (!phoneNumberV.matches("(\\+98|0)?9\\d{9}")) {
            phoneNumberError.setVisibility(View.VISIBLE);
            phoneNumberError.setText(getString(R.string.validNumberError));
            return false;
        }

        phoneNumberError.setVisibility(View.GONE);
        return true;
    }

    private void setError(ArrayList<EditText> editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().equals("")) {
                switch (editText.getId()) {
                    case R.id.edt_login_phoneNumber:
                        phoneNumberError.setVisibility(View.VISIBLE);
                        phoneNumberError.setText(getString(R.string.phoneNumberError));
                        break;
                    case R.id.edt_login_password:
                        passwordError.setVisibility(View.VISIBLE);
                        passwordError.setText(getString(R.string.passwordError));
                        break;
                }
            }
        }

    }

    private void sendLoginRequest() {
        phoneNumberError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);

        myPresenter.loginToApp(phoneNumberV, passwordV);

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
        PublicMethods.setBadgeNotif(this,badgeNotif);
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

    @Override
    protected void onPause() {
        super.onPause();

    }

}
