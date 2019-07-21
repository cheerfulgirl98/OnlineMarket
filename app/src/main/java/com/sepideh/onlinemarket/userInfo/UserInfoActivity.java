package com.sepideh.onlinemarket.userInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.MyView, View.OnClickListener {

    CoordinatorLayout coordinatorLayout;
    UserInfoContract.MyPresenter myPresenter;

    RelativeLayout sabad;
    ImageView back;
    TextView toolbartitle;

    TextInputLayout nameInput, familyInput, tellInput, mobileInput;
    EditText name, family, tell, mobile;
    RadioGroup jensiatGroup;
    RadioButton manRadio, womanRadio;
    TextView errorJensiat;
    Button sendInfo;

    List<EditText> editTexts = new ArrayList<>();
    String jensiatV;
    boolean tellB, mobileB, validationB;

    TextInputLayout errorInputLayout;

    UserInfo logedinUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        myPresenter = new UserInfoPresenter(new UserInfoModel());
        setUpViews();
        setSavedInfo();

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

        coordinatorLayout = findViewById(R.id.coor_updateUserInfo);

        sabad = findViewById(R.id.rel_toolbar_sabad);
        sabad.setVisibility(View.GONE);

        back = findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);

        toolbartitle = findViewById(R.id.txv_toolbar_title);
        toolbartitle.setText(getString(R.string.title_usernfo));

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

        errorInputLayout = nameInput;

        jensiatGroup = findViewById(R.id.radioG_info_jensiat);
        jensiatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_info_man:
                        jensiatV = "0";
                        errorJensiat.setVisibility(View.GONE);
                        break;
                    case R.id.radio_info_woman:
                        jensiatV = "1";
                        errorJensiat.setVisibility(View.GONE);
                        break;

                }
            }
        });

        manRadio = findViewById(R.id.radio_info_man);
        womanRadio = findViewById(R.id.radio_info_woman);
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

        if (v.getId() == R.id.btn_buttom) {
            if (checkEmptyness()) {
                if (checkValidation() && checkRadioValidation())
                    myPresenter.updateUserInfo(logedinUserInfo);
            } else {

                setError();
            }


        } else if (v.getId() == back.getId())
            onBackPressed();


    }

    private void removeInputError(TextInputLayout textInputLayout) {
        textInputLayout.setError("");
    }

    private boolean checkEmptyness() {

        for (EditText editText : editTexts) {
            if (editText.getText().length() <= 0) {

                return false;

            }
        }

        logedinUserInfo.setName(name.getText().toString()).setFamily(family.getText().toString());

        removeInputError(errorInputLayout);
        return true;

    }

    private boolean checkValidation() {

        if (checkTellValidation() && checkMobileValidation())
            return true;

        return false;
    }

    private boolean checkRadioValidation() {
        if (jensiatGroup.getCheckedRadioButtonId() == -1) {
            errorJensiat.setVisibility(View.VISIBLE);
            return false;
        } else {
            errorJensiat.setVisibility(View.GONE);
            logedinUserInfo.setJensiat(jensiatV);
        }
        return true;
    }

    private boolean checkTellValidation() {
        if (tell.getText().length() == 8) {
            logedinUserInfo.setTell(tell.getText().toString());
            tellInput.setError("");
            return true;
        }
        tellInput.setError("طول شماره تلفن ثابت نباید کمتر از هشت رقم باشد .");
        return false;
    }

    private boolean checkMobileValidation() {
        String mobileV = mobile.getText().toString();
        if (mobileV.matches("(\\+98|0)?9\\d{9}")) {
            mobileInput.setError("");
            logedinUserInfo.setPhoneNumber(mobileV);
            return true;
        } else {

            mobileInput.setError(getString(R.string.validNumberError));
            return false;

        }
    }

    private void setError() {

        if (name.getText().toString().equals("")) {
            nameInput.setError(getString(R.string.error_info_name));

        } else if (family.getText().length() <= 0) {
            removeInputError(errorInputLayout);
            familyInput.setError(getString(R.string.error_info_family));
            errorInputLayout = familyInput;

        } else if (tell.getText().length() <= 0) {
            removeInputError(errorInputLayout);
            tellInput.setError(getString(R.string.error_info_tell));
            errorInputLayout = tellInput;

        } else if (checkTellValidation() && (mobile.getText().length() <= 0)) {
            removeInputError(errorInputLayout);
            mobileInput.setError(getString(R.string.error_info_mobile));
            errorInputLayout = mobileInput;
        }

    }

    private void setSavedInfo() {
        logedinUserInfo = Hawk.get(getString(R.string.loginUserInfoTag));
        mobile.setText(logedinUserInfo.getPhoneNumber());
        name.setText(logedinUserInfo.getName());
        family.setText(logedinUserInfo.getFamily());
        tell.setText(logedinUserInfo.getTell());
        String jensiatV = logedinUserInfo.getJensiat();
        if (jensiatV.equals("0"))
            manRadio.setChecked(true);
        else if (jensiatV.equals("1"))
            womanRadio.setChecked(true);


    }


    @Override
    public void userInfoUpdated(UserInfo refreshedUserInfo) {
        Hawk.put(getString(R.string.loginUserInfoTag), refreshedUserInfo);
        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.snack_update_info), getResources().getColor(R.color.green));
    }
}
