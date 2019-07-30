package com.sepideh.onlinemarket.second.comments;

import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.CommentsDetailAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.second.compose.ComposeFragment;
import com.sepideh.onlinemarket.data.Comment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.third.activity.ThirdActivity;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.List;

/**
 * Created by pc on 4/26/2019.
 */

public class CommentsFragment extends BaseFragment implements CommentsContract.MyFragmentView, View.OnClickListener {

    CommentsContract.MyPresenter myPresenter;

    CoordinatorLayout coordinatorLayout;
    RelativeLayout sabad;
    TextView badgeNotif;

    RecyclerView recyclerView;
    FloatingActionButton fab;
    ImageView back;
    TextView toolbarTitle;


    UserInfo userInfo;
    ProductInfo selectedProduct;
    String productId;
    CommentsDetailAdapter commentsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new CommentsPresenter(new CommentsModel());

        selectedProduct = Hawk.get(getString(R.string.Hawk_selected_product));
        productId = selectedProduct.getId();
        userInfo = Hawk.get(getString(R.string.loginUserInfoTag));


    }


    @Override
    public void setUpViews() {


        coordinatorLayout = rootView.findViewById(R.id.cor_comments_fragment);

        sabad = rootView.findViewById(R.id.rel_toolbar_sabad);
        sabad.setOnClickListener(this);
        badgeNotif = rootView.findViewById(R.id.badge_notif);
        PublicMethods.setBadgeNotif(getViewContext(), badgeNotif);

        back = rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);
        toolbarTitle = rootView.findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(getString(R.string.comments));
        recyclerView = rootView.findViewById(R.id.rec_comments_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext(), RecyclerView.VERTICAL, false));

        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicMethods.checkLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.productIdTag), productId);
                    ComposeFragment composeFragment = new ComposeFragment();
                    composeFragment.setArguments(bundle);
                    PublicMethods.goNewFragment(getViewContext(), R.id.frame_second_container, composeFragment);
                } else {

                    openLogin.infoActivityToOpenLogin();
                }

            }
        });


    }


    @Override
    public int getLayout() {
        return R.layout.fragment_comments;
    }


    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void noServerConnection() {


        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_comments_fragment),getString(R.string.error_server_conection),getResources().getColor(R.color.red),"تلاش مجدد",getResources().getColor(R.color.white));

    }



    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);


    }

    public void sendServerRequest() {
        myPresenter.getAllComments(productId);
    }


    public void noNetworkConnection() {
        PublicMethods.setSnackbar(rootView.findViewById(R.id.cor_comments_fragment),getString(R.string.error_network_conection),getResources().getColor(R.color.red),"تلاش مجدد",getResources().getColor(R.color.white));

    }


    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }



    @Override
    public void showAllComments(List<Comment> comments) {

        commentsAdapter = new CommentsDetailAdapter(myPresenter, comments, getViewContext());
        //commentsAdapter.onClickInstance = this;
        recyclerView.setAdapter(commentsAdapter);
    }


    @Override
    public void voteBefore() {
        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.voteBeforeError), getViewContext().getResources().getColor(R.color.red));

    }


    @Override
    public void userNotLogin() {
        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.loginError), getViewContext().getResources().getColor(R.color.red));

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == back.getId()) {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }

        } if (view.getId() == sabad.getId()) {
            Intent intent = new Intent(getActivity(), ThirdActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onActionConnection() {

    }

    @Override
    public void onActionNoConnection() {
        noNetworkConnection();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        openLogin = (OpenLogin) context;
    }
}
