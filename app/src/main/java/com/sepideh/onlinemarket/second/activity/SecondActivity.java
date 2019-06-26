package com.sepideh.onlinemarket.second.activity;


import android.content.Intent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.second.detail.DetailFragment;
import com.sepideh.onlinemarket.register.RegisterActivity;

public class SecondActivity extends AppCompatActivity implements SecondContract.MyView, BaseFragment.OpenLogin {

    SecondContract.MyPresentr myPresenter;
    FragmentTransaction fragmentTransaction;
    ProductInfo selectedProduct;


    public BottomSheetDialog bottomSheetDialog;
    public View view1;
    public EditText phoneNumber, password;
    public TextView phoneNumberError, passwordError;
    public String phoneNumberV, passwordV;
    public ImageView closeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setUpViews();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("selected_product_bundle");
        if (bundle != null) {
            selectedProduct = (ProductInfo) bundle.getSerializable("selected_product");
            Hawk.put(getString(R.string.Hawk_selected_product), selectedProduct);


        }
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
                Intent intent = new Intent(SecondActivity.this, RegisterActivity.class);
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
    public void setUpViews() {
        myPresenter= new SecondPresenter(new SecondModel());
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_second_container, new DetailFragment());
        fragmentTransaction.commit();
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
