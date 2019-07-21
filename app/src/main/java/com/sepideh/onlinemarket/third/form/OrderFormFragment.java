package com.sepideh.onlinemarket.third.form;


import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.Customer;
import com.sepideh.onlinemarket.map.MapsActivity;

import java.util.ArrayList;

/**
 * Created by sepideh on 6/22/2019.
 */

public class OrderFormFragment extends BaseFragment implements View.OnClickListener {
    TextView  goToMap;

    TextInputLayout nameInput,familyInput, mobileInput, addressInput;
    EditText name,family, mobile, address;
    Button sendInfo;

    ArrayList<EditText> editTexts=new ArrayList<>();
    Customer customer=new Customer();
    TextInputLayout errorInputLayout;


    @Override
    public void setUpViews() {

        goToMap = rootView.findViewById(R.id.txv_goToMap);
        goToMap.setOnClickListener(this);
        goToMap.setPaintFlags(goToMap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        nameInput = rootView.findViewById(R.id.inputLayout_form_name);
        familyInput = rootView.findViewById(R.id.inputLayout_form_family);
        mobileInput = rootView.findViewById(R.id.inputLayout_form_mobile);
        addressInput = rootView.findViewById(R.id.inputLayout_form_address);

        name = rootView.findViewById(R.id.edt_form_name);
        editTexts.add(name);
        family = rootView.findViewById(R.id.edt_form_family);
        editTexts.add(family);
        mobile = rootView.findViewById(R.id.edt_form_mobile);
        editTexts.add(mobile);
        address = rootView.findViewById(R.id.edt_form_address);
        editTexts.add(address);

        errorInputLayout=nameInput;

        sendInfo = rootView.findViewById(R.id.btn_buttom);
        sendInfo.setText(R.string.btn_send_info);
        sendInfo.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order_form;
    }

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.btn_buttom) {
            ArrayList<EditText> editTexts = new ArrayList<>();
            editTexts.add(name);
            editTexts.add(mobile);
            editTexts.add(address);

            if (checkEmptyness() && checkMobileValidation())
                sendRequest();
            else
                setError();

        } else if (view.getId() == R.id.txv_goToMap) {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent);
        }

    }
    private boolean checkEmptyness() {

        for (EditText editText : editTexts) {
            if (editText.getText().length() <= 0) {

                return false;

            }
        }

        customer.setName(name.getText().toString()).setFamily(family.getText().toString()).setAddress(address.getText().toString());

        removeInputError(errorInputLayout);
        return true;

    }

    private boolean checkMobileValidation() {
        String mobileV = mobile.getText().toString();
        if (mobileV.matches("(\\+98|0)?9\\d{9}")) {
            mobileInput.setError("");
            customer.setMobile(mobileV);
            return true;
        } else {

            mobileInput.setError(getString(R.string.validNumberError));
            return false;

        }
    }

       void sendRequest() {

    }

    private void setError() {

        if (name.getText().toString().equals("")) {
            nameInput.setError(getString(R.string.receiverNameError));

        } else if (family.getText().length() <= 0) {
            removeInputError(errorInputLayout);
            familyInput.setError(getString(R.string.receiverFamilyError));
            errorInputLayout = familyInput;

        } else if (mobile.getText().length() <= 0) {
            removeInputError(errorInputLayout);
            mobileInput.setError(getString(R.string.receiverMobileError));
            errorInputLayout = mobileInput;

        } else if (checkMobileValidation() && (address.getText().length() <= 0)) {
            removeInputError(errorInputLayout);
            addressInput.setError(getString(R.string.receiverAddressError));
            errorInputLayout = mobileInput;
        }

    }

    private void removeInputError(TextInputLayout textInputLayout) {
        textInputLayout.setError("");
    }


}
