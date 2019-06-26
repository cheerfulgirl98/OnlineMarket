package com.sepideh.onlinemarket.second.comments;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.CommentsDetailAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.second.compose.ComposeFragment;
import com.sepideh.onlinemarket.data.Comment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.List;

/**
 * Created by pc on 4/26/2019.
 */

public class CommentsFragment extends BaseFragment implements CommentsContract.MyView,View.OnClickListener {

    CoordinatorLayout coordinatorLayout;

    CommentsContract.MyPresenter myPresenter;
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

        selectedProduct=Hawk.get(getString(R.string.Hawk_selected_product));
        productId = selectedProduct.getId();
        userInfo = Hawk.get(getString(R.string.loginUserInfoTag));

    }


    @Override
    public void setUpViews() {


        coordinatorLayout = rootView.findViewById(R.id.cor_comments_main);

        back=rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);
        toolbarTitle=rootView.findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(getString(R.string.comments));
        recyclerView = rootView.findViewById(R.id.rec_comments_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));

        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PublicMethods.checkLogin()){
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.productIdTag), productId);
                ComposeFragment composeFragment = new ComposeFragment();
                composeFragment.setArguments(bundle);
                PublicMethods.goNewFragment(getViewContext(),R.id.cor_main,composeFragment);
               }
                else {

                  openLogin.openLoginButtomsheet();}

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
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
        myPresenter.getAllComments(productId);
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
    public void conectionError() {
        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.conectionError), getViewContext().getResources().getColor(R.color.red));
    }

    @Override
    public void userNotLogin() {
        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.loginError), getViewContext().getResources().getColor(R.color.red));

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==back.getId()){
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                Log.i("ggg", "popping backstack");
                fm.popBackStack();
            } else {
                Log.i("ggg", "nothing on backstack, calling super");
                // super.onBackPressed();
            }
        }

    }


//    private OpenLogin openLogin;
//    public interface OpenLogin{
//        void openLoginButtomsheet();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        openLogin=(OpenLogin)context;
//    }
}
