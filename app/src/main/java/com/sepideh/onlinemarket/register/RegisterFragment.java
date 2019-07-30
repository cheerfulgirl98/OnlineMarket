package com.sepideh.onlinemarket.register;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.RegisterUserInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.Constant;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pc on 5/2/2019.
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.MyFragmentView, View.OnClickListener {

    RegisterContract.MyPresentr myPresenter;


    ImageView close;
    TextInputLayout usernameInput, phoneInput, passworInput;
    EditText userName, phoneNumber, password;
    Button button;
    String usernameV, phoneNumberV, passwordV;
    TextInputLayout errorInputLayout;

    boolean connectionIsOkToSendRequest;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new RegisterPresenter(new RegisterModel());
    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);


    }


    @Override
    public void setUpViews() {

        close = rootView.findViewById(R.id.img_register_close);
        close.setOnClickListener(this);

        usernameInput = rootView.findViewById(R.id.inputLayout_userName);
        userName = rootView.findViewById(R.id.edt_register_userName);
        phoneInput = rootView.findViewById(R.id.inputLayout_phoneNumber);
        phoneNumber = rootView.findViewById(R.id.edt_register_phoneNumber);
        passworInput = rootView.findViewById(R.id.inputLayout_password);
        password = rootView.findViewById(R.id.edt_register_password);


        button = rootView.findViewById(R.id.btn_register_send);
        button.setOnClickListener(this);

        errorInputLayout = usernameInput;


    }


    public void sendServerRequest() {
        connectionIsOkToSendRequest=true;
    }


    public void noNetworkConnection() {

        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_register_fragment), getString(R.string.error_network_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));

    }

    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_register_close) {
            getActivity().finish();
        }

        if (view.getId() == R.id.btn_register_send) {

            usernameV = userName.getText().toString();
            phoneNumberV = phoneNumber.getText().toString();
            passwordV = password.getText().toString();

            ArrayList<EditText> editTexts = new ArrayList<>();
            editTexts.add(userName);
            editTexts.add(phoneNumber);
            editTexts.add(password);

            if (checkEmptyness(editTexts) && checkValidation()) {
                if (connectionIsOkToSendRequest)
                    sendCodeRequest();
            } else
                setError();


        }


    }

    private boolean checkEmptyness(ArrayList<EditText> editTexts) {

        for (EditText editText : editTexts) {
            if (editText.getText().length() <= 0) {

                return false;

            }
        }

        return true;

    }


    private boolean checkValidation() {


        if (!phoneNumberV.matches("(\\+98|0)?9\\d{9}")) {
            phoneInput.setError(getString(R.string.validNumberError));
            return false;
        }
        return true;


    }

    private void sendCodeRequest() {
        Random random = new Random();
        String generatedCode = String.valueOf(random.nextInt(10000));
        Hawk.put("generatedCode", generatedCode);
        myPresenter.sendCode(Constant.TEMPLATE, phoneNumberV, generatedCode);
    }

    private void setError() {

        if (userName.getText().length() <= 0) {

            usernameInput.setError(getString(R.string.usernameError));
        } else if (phoneNumber.getText().length() <= 0) {
            removeInputError(errorInputLayout);
            phoneInput.setError(getString(R.string.phoneNumberError));
            errorInputLayout = phoneInput;
        } else if (checkValidation() && (password.getText().length() <= 0)) {
            removeInputError(errorInputLayout);
            passworInput.setError(getString(R.string.passwordError));
            removeInputError(errorInputLayout);

        }


    }

    private void removeInputError(TextInputLayout textInputLayout) {
        textInputLayout.setError("");
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void noServerConnection() {

        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_register_fragment), getString(R.string.error_server_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));

    }



    @Override
    public void successfulSendingSms() {
        RegisterUserInfo userInfo = new RegisterUserInfo();
        userInfo.setUserName(usernameV).setPhoneNumber(phoneNumberV).setPassword(passwordV);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.registerUserInfoTag), userInfo);
        ValidationCodeFragment validationCodeFragment = new ValidationCodeFragment();
        validationCodeFragment.setArguments(bundle);
        FragmentManager manager = ((AppCompatActivity) getViewContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Register_container, validationCodeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @Override
    public void successfulRegistration(UserInfo userInfo) {

    }

    @Override
    public void onActionConnection() {
        sendCodeRequest();
    }

    @Override
    public void onActionNoConnection() {
        noNetworkConnection();
    }
}
