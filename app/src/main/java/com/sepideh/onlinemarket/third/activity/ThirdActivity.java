package com.sepideh.onlinemarket.third.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.main.activity.MainActivity;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.third.form.OrderFormFragment;
import com.sepideh.onlinemarket.third.sabad.BasketFragment;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;


public class ThirdActivity extends AppCompatActivity implements ThirdContract.MyView, View.OnClickListener ,BaseFragment.OpenLogin,BasketFragment.ManageSabadI{

    ThirdContract.MyPresentr myPresenter;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    RelativeLayout sabad;
    TextView toolbarTitle,badgeNotif;
    ImageView back;
    BottomSheetDialog bottomSheetDialog;
    View view1;
    EditText phoneNumber, password;
    TextView phoneNumberError, passwordError,forgetPassword;
    String phoneNumberV, passwordV;
    ImageView closeLogin;


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
        toolbarTitle=findViewById(R.id.txv_toolbar_title);
        setToolbarTitl();
        badgeNotif = findViewById(R.id.badge_notif);
        PublicMethods.setBadgeNotif(this, badgeNotif);
        back = findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_third_container, new BasketFragment(), getString(R.string.basketfragTag));
        fragmentTransaction.commit();
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
                Intent intent = new Intent(ThirdActivity.this, RegisterActivity.class);
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
    public void successfulLogin(UserInfo userInfo) {

        Hawk.put(getString(R.string.loginUserInfoTag), userInfo);
        bottomSheetDialog.dismiss();
        PublicMethods.goNewFragment(this,R.id.frame_third_container,new OrderFormFragment(),getString(R.string.orderFormFragTag));


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

    private void setToolbarTitl() {
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_third_container);


        if (fragment != null && fragment.getTag() != null) {
            if (fragment.getTag().equals(getString(R.string.basketfragTag)))
                toolbarTitle.setText(getString(R.string.your_basket));
            else if (fragment.getTag().equals(getString(R.string.orderFormFragTag)))
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
        PublicMethods.setBadgeNotif(this,badgeNotif);

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
}
