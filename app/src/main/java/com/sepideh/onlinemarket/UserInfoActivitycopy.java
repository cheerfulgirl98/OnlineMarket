package com.sepideh.onlinemarket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.userInfo.UserInfoContract;
import com.sepideh.onlinemarket.userInfo.UserInfoPresenter;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivitycopy extends AppCompatActivity implements UserInfoContract.MyView, View.OnClickListener {

    UserInfoContract.MyPresenter myPresenter;
    TextInputLayout nameInput, familyInput, codeMeliInput, tellInput, mobileInput;
    EditText name, family, codeMeli, tell, mobile;
    RadioGroup jensiat;
    TextView errorJensiat;
    Button sendInfo;

    List<EditText> editTexts = new ArrayList<>();
    UserInfo userInfo = new UserInfo();
    String jensiatV;
    boolean tellB, mobileB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setUpViews();

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
        myPresenter = new UserInfoPresenter();

        name = findViewById(R.id.edt_info_name);
        editTexts.add(name);
        family = findViewById(R.id.edt_info_family);
        editTexts.add(family);
        tell = findViewById(R.id.edt_info_tell);
        editTexts.add(tell);
        mobile = findViewById(R.id.edt_info_mobile);
        editTexts.add(mobile);


        nameInput = findViewById(R.id.inputLayout_name);
        familyInput = findViewById(R.id.inputLayout_family);
        tellInput = findViewById(R.id.inputLayout_tell);
        mobileInput = findViewById(R.id.inputLayout_mobile);

        jensiat = findViewById(R.id.radioG_info_jensiat);
        jensiat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_info_man:
                        jensiatV = "man";
                        break;
                    case R.id.radio_info_woman:
                        jensiatV = "woman";
                        break;

                }
            }
        });
        errorJensiat = findViewById(R.id.txv_error_jensiat);

        sendInfo = findViewById(R.id.btn_buttom);
        sendInfo.setText(R.string.btn_send_info);
        sendInfo.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        checkRadioValidation();
        if (v.getId() == R.id.btn_buttom) {
            if (checkInfoLength()) {
                if (checkValidation() & checkRadioValidation())
                    myPresenter.sendInfo(userInfo);
            } else {
                checkEditTextEmptyness();
            }


        }


    }


    private boolean checkInfoLength() {

        for (EditText editText : editTexts) {
            if (editText.getText().length() <= 0) {

                return false;

            }
        }

        userInfo.setName(name.getText().toString()).setFamily(family.getText().toString());
        return true;

    }

    private boolean checkValidation() {

        if (tell.getText().length() == 8) {
            userInfo.setTell(tell.getText().toString());
            tellInput.setError("");
            tellB = true;
        } else tellInput.setError("طول شماره تلفن ثابت نباید کمتر از هشت رقم باشد .");

        String mobileV = mobile.getText().toString();
        if (!mobileV.matches("(\\+98|0)?9\\d{9}"))
            mobileInput.setError(getString(R.string.validNumberError));
        else {
            mobileInput.setError("");
            userInfo.setPhoneNumber(mobileV);
            mobileB = true;
        }


        return tellB & mobileB;
    }

    private boolean checkRadioValidation() {
        if (jensiat.getCheckedRadioButtonId() == -1) {
            errorJensiat.setVisibility(View.VISIBLE);
            return false;
        } else {
            errorJensiat.setVisibility(View.GONE);
            userInfo.setJesiat(jensiatV);
        }
        return true;
    }

    private void checkEditTextEmptyness() {

        for (EditText editText : editTexts) {

            if (editText.getText().toString().equals("")) {
                switch (editText.getId()) {
                    case R.id.edt_info_name:
                        nameInput.setError(getString(R.string.error_info_name));
                        break;
                    case R.id.edt_info_family:
                        familyInput.setError(getString(R.string.error_info_family));
                        break;
                    case R.id.edt_info_tell:
                        tellInput.setError(getString(R.string.error_info_tell));
                        break;
                    case R.id.edt_info_mobile:
                        mobileInput.setError(getString(R.string.error_info_mobile));
                        break;

                }

            } else {
                switch (editText.getId()) {
                    case R.id.edt_info_name:
                        nameInput.setError("");
                        userInfo.setName(name.getText().toString());
                        break;
                    case R.id.edt_info_family:
                        nameInput.setError("");
                        userInfo.setFamily(family.getText().toString());
                        break;
                    case R.id.edt_info_tell:
                        nameInput.setError("");
                        userInfo.setTell(tell.getText().toString());
                        break;
                    case R.id.edt_info_mobile:
                        mobileInput.setError("");
                        break;
                }
            }
        }
    }


}
