package com.sepideh.onlinemarket.second.compose;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;

/**
 * Created by pc on 5/10/2019.
 */

public class WriteFragment extends BaseFragment implements ComposeContract.MyView {

    ComposeContract.MyPresenter myPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter=new ComposePresenter(new ComposeModel());
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void successfulCompose() {

    }

    @Override
    public void commentedBefore() {

    }

    @Override
    public void setUpViews() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_compose;
    }
}
