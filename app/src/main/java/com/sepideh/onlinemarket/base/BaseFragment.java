package com.sepideh.onlinemarket.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pc on 4/6/2019.
 */

public abstract class BaseFragment extends Fragment {
    public View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(rootView==null){
        rootView=inflater.inflate(getLayout(),container,false);
        setUpViews();}
        return rootView;
    }

    public abstract void setUpViews();
    public abstract int getLayout();


    public OpenLogin openLogin;
    public interface OpenLogin{
        void openLoginButtomsheet();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        openLogin=(OpenLogin)context;
//
//
//    }


}
