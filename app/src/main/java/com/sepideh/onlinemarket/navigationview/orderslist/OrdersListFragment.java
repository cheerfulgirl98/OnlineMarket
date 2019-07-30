package com.sepideh.onlinemarket.navigationview.orderslist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;

public class OrdersListFragment extends BaseFragment implements View.OnClickListener {

    RelativeLayout sabad;
    ImageView back;
    TextView toolbartitle;
    @Override
    public void setUpViews() {
        sabad=rootView.findViewById(R.id.rel_toolbar_sabad);
        sabad.setVisibility(View.GONE);

        back=rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);

        toolbartitle=rootView.findViewById(R.id.txv_toolbar_title);
        toolbartitle.setText(getString(R.string.title_ordersList));
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_orders_list;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void sendServerRequest() {

    }

    @Override
    public void noNetworkConnection() {

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==back.getId())
            getActivity().onBackPressed();
    }

    @Override
    public void onActionConnection() {

    }
    @Override
    public void onActionNoConnection() {
        noNetworkConnection();
    }
}
