package com.sepideh.onlinemarket.third.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.third.sabad.BasketFragment;


public class ThirdActivity extends AppCompatActivity implements ThirdContract.MyView, BaseFragment.OpenLogin {

    ThirdContract.MyPresentr myPresenter;

    FragmentTransaction fragmentTransaction;
    public BottomSheetDialog bottomSheetDialog;
    public View view1;
    public EditText phoneNumber, password;
    public TextView phoneNumberError, passwordError;
    public String phoneNumberV, passwordV;
    public ImageView closeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setUpViews();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_third_container, new BasketFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void setUpViews() {
        myPresenter = new ThirdPresenter(new ThirdModel());
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
    public void successfulLogin(UserInfo userInfo) {

    }

    @Override
    public void userNotFound() {

    }

    @Override
    public void passwordIsWrong() {

    }


}
