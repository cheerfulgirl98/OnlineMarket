package com.sepideh.onlinemarket.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.networkerror.MyReceiver;
import com.sepideh.onlinemarket.utils.PublicMethods;

/**
 * Created by pc on 4/6/2019.
 */

public abstract class BaseFragment extends Fragment implements PublicMethods.SnackManage{

    public View rootView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
            setUpViews();
        }
        return rootView;
    }



    public abstract void setUpViews();

    public abstract int getLayout();


    public OpenLogin openLogin;
    public interface OpenLogin {
        void infoActivityToOpenLogin();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (PublicMethods.checkNetworkConnection())
            sendServerRequest();
        else {
            PublicMethods.setSnackManage(this);
            noNetworkConnection();

        }

    }

    public abstract Context getViewContext();
    public abstract void sendServerRequest();
    public abstract void noNetworkConnection() ;


}
