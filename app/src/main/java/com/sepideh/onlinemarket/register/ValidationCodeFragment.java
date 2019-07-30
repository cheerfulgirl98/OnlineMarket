package com.sepideh.onlinemarket.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.main.activity.MainActivity;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.RegisterUserInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.PublicMethods;

/**
 * Created by pc on 5/2/2019.
 */

public class ValidationCodeFragment extends BaseFragment implements RegisterContract.MyFragmentView, View.OnClickListener {

    RegisterContract.MyPresentr myPresentr;

    TextView validationT, codeError;
    EditText validationCode;
    Button sendCode;

    RegisterUserInfo userInfoRegister;
    String userPhone;

    boolean connectionIsOkToSendRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresentr = new RegisterPresenter(new RegisterModel());
        Bundle bundle = getArguments();
        if (bundle != null) {
            userInfoRegister = (RegisterUserInfo) bundle.getSerializable(getString(R.string.registerUserInfoTag));
            userPhone = userInfoRegister.getPhoneNumber();
        }

    }

    @Override
    public void setUpViews() {
        validationT = rootView.findViewById(R.id.txv_validation_validationT);
        validationT.setText("کد تایید به شماره تلفن " + userPhone + " ارسال گردید .");
        validationCode = rootView.findViewById(R.id.edt_validation_code);
        codeError = rootView.findViewById(R.id.edt_validation_codeError);
        sendCode = rootView.findViewById(R.id.btn_validation_send);
        sendCode.setOnClickListener(this);




    }


    @Override
    public int getLayout() {
        return R.layout.fragment_validation_code;
    }

    @Override
    public Context getViewContext() {
        return null;
    }

    @Override
    public void noServerConnection() {
        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_register_fragment), getString(R.string.error_server_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));


    }



    @Override
    public void successfulSendingSms() {


    }

    @Override
    public void successfulRegistration(UserInfo userInfo) {
        Hawk.put(getString(R.string.loginUserInfoTag), userInfo);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("newregistered", "yes");
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_validation_send) {
            String generatedCode = Hawk.get("generatedCode");
            String typedCode = validationCode.getText().toString();
            if (validationCode.length() <= 0) {
                codeError.setVisibility(View.VISIBLE);
                codeError.setText(getString(R.string.codeError));
            } else if (!typedCode.equals(generatedCode)) {
                codeError.setVisibility(View.VISIBLE);
                codeError.setText(getString(R.string.validationError));
            } else if (connectionIsOkToSendRequest) {

                //typedCode equals generatedCode
                sendRegisterRequest();
            }

        }
    }

    private void sendRegisterRequest(){
        myPresentr.registerUser(userInfoRegister.getUserName(), userInfoRegister.getPhoneNumber(), userInfoRegister.getPassword());

    }
    @Override
    public void onStart() {
        super.onStart();
        myPresentr.attachView(this);

    }


    public void sendServerRequest() {
        connectionIsOkToSendRequest = true;
    }


    public void noNetworkConnection() {
        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_register_fragment), getString(R.string.error_network_conection), getResources().getColor(R.color.red), "تلاش مجدد", getResources().getColor(R.color.white));


    }

    @Override
    public void onStop() {
        super.onStop();
        myPresentr.detachView();
    }

    @Override
    public void onActionConnection() {
        sendRegisterRequest();
    }

    @Override
    public void onActionNoConnection() {
        noNetworkConnection();
    }
}

