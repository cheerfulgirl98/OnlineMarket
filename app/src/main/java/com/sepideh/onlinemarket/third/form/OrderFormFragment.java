package com.sepideh.onlinemarket.third.form;


import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.third.map.MapsActivity;

import java.util.ArrayList;

/**
 * Created by sepideh on 6/22/2019.
 */

public class OrderFormFragment extends BaseFragment implements View.OnClickListener {
    TextView toolbarTitle, goToMap;
    RelativeLayout sabad;
    ImageView back;

    TextInputLayout nameInput, mobileInput, addressInput;
    EditText name, mobile, address;
    Button sendInfo;

    String nameV, mobileV, addressV;
    boolean isNotEmptyB;

    @Override
    public void setUpViews() {

        //toolbar
        toolbarTitle = rootView.findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(R.string.receiverInfo);
        goToMap = rootView.findViewById(R.id.txv_goToMap);
        goToMap.setOnClickListener(this);
        sabad = rootView.findViewById(R.id.rel_toolbar_sabad);
        sabad.setVisibility(View.GONE);
        back = rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);

        //

        nameInput = rootView.findViewById(R.id.inputLayout_form_name);
        mobileInput = rootView.findViewById(R.id.inputLayout_form_mobile);
        addressInput = rootView.findViewById(R.id.inputLayout_form_address);

        name = rootView.findViewById(R.id.edt_form_name);
        mobile = rootView.findViewById(R.id.edt_form_mobile);
        address = rootView.findViewById(R.id.edt_form_address);
        sendInfo = rootView.findViewById(R.id.btn_form_sendInfo);
        sendInfo.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order_form;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_toolbar_back) {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                getActivity().onBackPressed();
            }
        } else if (view.getId() == R.id.btn_form_sendInfo) {
            ArrayList<EditText> editTexts = new ArrayList<>();
            editTexts.add(name);
            editTexts.add(mobile);
            editTexts.add(address);
            isNotEmptyB = checkFields(editTexts);
            if (isNotEmptyB)
                sendRequest();
            else
                setError(editTexts);

        } else if (view.getId() == R.id.txv_goToMap) {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent);
        }

    }


    boolean checkFields(ArrayList<EditText> editTexts) {

        nameV = name.getText().toString();
        mobileV = mobile.getText().toString();
        addressV = address.getText().toString();

        for (EditText editText : editTexts) {
            if (editText.getText().length() <= 0) {

                return false;

            }
        }

        return true;
    }

    void sendRequest() {

    }

    void setError(ArrayList<EditText> editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().equals("")) {
                switch (editText.getId()) {
                    case (R.id.edt_form_name):
                        nameInput.setError(getString(R.string.receiverNameError));
                        break;

                    case (R.id.edt_form_mobile):
                        mobileInput.setError(getString(R.string.receiverMobileError));
                        break;

                    case (R.id.edt_form_address):
                        addressInput.setError(getString(R.string.receiverAddressError));
                        break;
                }
            } else {
                switch (editText.getId()) {
                    case (R.id.edt_form_name):
                        nameInput.setError("");
                        break;

                    case (R.id.edt_form_mobile):
                        if (!mobileV.matches("(\\+98|0)?9\\d{9}"))
                            mobileInput.setError(getString(R.string.validNumberError));
                        else
                            mobileInput.setError("");
                        break;

                    case (R.id.edt_form_address):
                        addressInput.setError("");
                        break;

                }

            }

        }
    }


}
