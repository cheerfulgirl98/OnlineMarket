package com.sepideh.onlinemarket.register;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.sepideh.onlinemarket.R;

public class RegisterActivity extends AppCompatActivity{

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpView();
    }

    void setUpView() {

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_Register_container,new RegisterFragment());
        fragmentTransaction.commit();

    }


}
