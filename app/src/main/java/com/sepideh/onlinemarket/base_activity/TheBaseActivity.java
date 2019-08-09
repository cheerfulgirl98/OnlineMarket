package com.sepideh.onlinemarket.base_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jakewharton.rxbinding3.view.RxView;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.networkerror.MyReceiver;
import com.sepideh.onlinemarket.register.RegisterActivity;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;

public abstract class TheBaseActivity extends AppCompatActivity implements BaseActivityContract.MyView, PublicMethods.SnackManage {

    BaseActivityContract.MyPresenter myPresenter;
    MyReceiver myReceiver = new MyReceiver();


    public BottomSheetDialog bottomSheetDialog;
    public View view1;
    EditText phoneNumber, password;
    public TextView phoneNumberError, passwordError, forgetPassword;
    public String phoneNumberV, passwordV;
    public ImageView closeLogin;
    Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_base);
        myPresenter = new BaseActivityPresenter(new BaseActivityModel());
        PublicMethods.setSnackManage(this);
    }

    public abstract void setUpViews();

    @Override
    protected void onStart() {
        Log.d("pppd", "onStart: base");
        super.onStart();
        myPresenter.attachView(this);


    }

    @Override
    protected void onResume() {
        Log.d("pppd", "onResume: base");
        broadcastIntent();
        super.onResume();
    }

    private void broadcastIntent() {
        Log.d("pppd", "broadcastIntent: ");
        registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        Log.d("pppd", "onPause: base");
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onStop() {
        Log.d("pppd", "onStop:base ");
        super.onStop();
        myPresenter.detachView();
    }
    public void openButtomsheetLogin() {

        bottomSheetDialog = new BottomSheetDialog(this);
        view1 = getLayoutInflater().inflate(R.layout.login_layout, null);
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();
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

        Button register = view1.findViewById(R.id.btn_login_goRegister);
        bottomSheetDialog.show();
        register.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(TheBaseActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        login = view1.findViewById(R.id.btn_login_send);
        RxView.clicks(login).debounce(3000, TimeUnit.MILLISECONDS).subscribe(this::manageLoginRequest);

    }

    private void manageLoginRequest(Unit unit) {

        phoneNumberV = phoneNumber.getText().toString();
        passwordV = password.getText().toString();
        ArrayList<EditText> editTexts = new ArrayList<>();
        editTexts.add(phoneNumber);
        editTexts.add(password);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (checkEmptyness(editTexts) & checkValidation()) {
                    if (PublicMethods.checkNetworkConnection()) {
                        phoneNumberError.setVisibility(View.GONE);
                        passwordError.setVisibility(View.GONE);
                        sendLoginRequest();


                    } else
                        noNetworkConnection();


                } else setError(editTexts);
            }
        });


    }

    private void sendLoginRequest(){

        myPresenter.loginToApp(phoneNumberV,passwordV);
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

    @Override
    public void successfulLogin(UserInfo userInfo) {
        Hawk.put(getString(R.string.loginUserInfoTag), userInfo);
        bottomSheetDialog.dismiss();
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


    public void noNetworkConnection() {
        PublicMethods.setSnackbar(view1.findViewById(R.id.cor_login), getString(R.string.error_network_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));
    }



    @Override
    public void noServerConnection() {
        bottomSheetDialog.dismiss();
        PublicMethods.setSnackbar(view1.findViewById(R.id.cor_login), getString(R.string.error_server_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));


    }

    @Override
    public void onActionConnection() {
        myPresenter.loginToApp(phoneNumberV, passwordV);
    }

    @Override
    public void onActionNoConnection() {
        noNetworkConnection();

    }


}
