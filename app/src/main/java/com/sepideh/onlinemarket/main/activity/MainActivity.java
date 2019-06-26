package com.sepideh.onlinemarket.main.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.main.categories.CategoryFragment;
import com.sepideh.onlinemarket.main.favorit.FavoritFragment;
import com.sepideh.onlinemarket.main.home.HomeFragment;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.third.activity.ThirdActivity;
import com.sepideh.onlinemarket.utils.PublicMethods;

public class MainActivity extends AppCompatActivity implements MainContract.MyView, BaseFragment.OpenLogin,View.OnClickListener {

    MainContract.MyPresentr myPresenter;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout sabad;
    DrawerLayout drawerLayout;
    ImageView burgerMenu, back;
    TextView toolbarTitle,badgeNotif;
    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;


    BottomSheetDialog bottomSheetDialog;
    View view1;
    EditText phoneNumber, password;
    TextView phoneNumberError, passwordError;
    String phoneNumberV, passwordV;
    ImageView closeLogin;
    Context mContext = this;

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
        sabad=findViewById(R.id.rel_detail_sabad);
        sabad.setOnClickListener(this);
        badgeNotif =findViewById(R.id.badge_notif);
        drawerLayout = findViewById(R.id.drawer);
        burgerMenu = findViewById(R.id.img_toolbar_burgerMenu);
        back = findViewById(R.id.img_toolbar_back);
        toolbarTitle = findViewById(R.id.txv_toolbar_title);

        burgerMenu.setOnClickListener(this);

        back.setOnClickListener(this);

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
                        return true;

                    case R.id.bottom_categories:
                        burgerMenu.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        toolbarTitle.setVisibility(View.VISIBLE);
                        toolbarTitle.setText("دسته بندی محصولات");
                        PublicMethods.goNewFragment(mContext, R.id.frame_main_container, new CategoryFragment());
                        return true;

                    case R.id.bottom_favorit:
                        burgerMenu.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        toolbarTitle.setVisibility(View.VISIBLE);
                        toolbarTitle.setText("محصولات مورد علاقه");
                        PublicMethods.goNewFragment(mContext, R.id.frame_main_container, new FavoritFragment());
                        return true;

                }

                return false;

            }
        });
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==sabad.getId()){
            Intent intent=new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        }

        else if (view.getId() == burgerMenu.getId()) {
            if (view.getId() == burgerMenu.getId()) {
                if (PublicMethods.checkLogin())
                    openLoginButtomsheet();

                else {
                    if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
            }
        }
        else if (view.getId() == back.getId())
            onBackPressed();
    }

    @Override
    public void successfulLogin(UserInfo userInfo) {

        bottomSheetDialog.dismiss();
        Toast.makeText(this, "به آنلاین مارکت خوش آمدید.", Toast.LENGTH_SHORT).show();
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

    public void setBadgeNotif() {
        int sabadsize= Hawk.get(getString(R.string.Hawk_sabad_size),0);
        if(sabadsize>0){
            badgeNotif.setVisibility(View.VISIBLE);
            badgeNotif.setText(String.valueOf(sabadsize));}
        else badgeNotif.setVisibility(View.GONE);
    }


    @Override
    public void openLoginButtomsheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        view1 = getLayoutInflater().inflate(R.layout.login_layout, null);
        bottomSheetDialog.setContentView(view1);
        phoneNumber = view1.findViewById(R.id.edt_login_phoneNumber);
        password = view1.findViewById(R.id.edt_login_password);
        phoneNumberError = view1.findViewById(R.id.txv_login_phoneNumberError);
        passwordError = view1.findViewById(R.id.txv_login_passwordError);
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
                if (phoneNumberV.equals(""))
                    phoneNumberError.setVisibility(View.VISIBLE);
                if (passwordV.equals(""))
                    passwordError.setVisibility(View.VISIBLE);
                else {
                    phoneNumberError.setVisibility(View.GONE);
                    passwordError.setVisibility(View.GONE);
                     myPresenter.loginToApp(phoneNumberV, passwordV);
                }
            }
        });
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
        setBadgeNotif();
    }
}