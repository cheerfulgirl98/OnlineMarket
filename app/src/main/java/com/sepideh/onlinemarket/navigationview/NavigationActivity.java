package com.sepideh.onlinemarket.navigationview;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.MyBaseActivity;
import com.sepideh.onlinemarket.navigationview.orderslist.OrdersListFragment;

public class NavigationActivity extends MyBaseActivity {

    FragmentTransaction fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setupViews();
    }

    void setupViews(){
        fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_navigation_container,new OrdersListFragment());
        fm.commit();
    }
}
