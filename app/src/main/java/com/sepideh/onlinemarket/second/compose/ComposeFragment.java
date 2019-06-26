package com.sepideh.onlinemarket.second.compose;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.utils.PublicMethods;

/**
 * Created by pc on 5/9/2019.
 */

public class ComposeFragment extends BaseFragment implements ComposeContract.MyView, View.OnClickListener {

    ComposeContract.MyPresenter myPresenter;
    CoordinatorLayout coordinatorLayout;
    TextView toolbarTitle;
    SimpleRatingBar simpleRatingBar;
    EditText editText;
    Button button;
    ImageView back, basket;
    RelativeLayout sabadRel;

    String star;
    ProductInfo selectedProduct;
    String productId;
    UserInfo userInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new ComposePresenter(new ComposeModel());

//        Bundle bundle=getArguments();
//        if(bundle!=null){
//           productId=bundle.getString(getString(R.string.productIdTag));
//        }
        selectedProduct = Hawk.get(getString(R.string.Hawk_selected_product));
        productId = selectedProduct.getId();
        userInfo = Hawk.get(getString(R.string.loginUserInfoTag));
    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void setUpViews() {


        coordinatorLayout = rootView.findViewById(R.id.cor_compose);
        back = rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);
        basket = rootView.findViewById(R.id.img_toolbar_basket);
        basket.setVisibility(View.GONE);
        toolbarTitle = rootView.findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(R.string.writeComment);
        simpleRatingBar = rootView.findViewById(R.id.star_compose);
        simpleRatingBar.setOnClickListener(this);
        editText = rootView.findViewById(R.id.edt_compose_comment);
        button = rootView.findViewById(R.id.btn_compose_send);
        button.setOnClickListener(this);
        sabadRel=rootView.findViewById(R.id.rel_toolbar_sabad);
        sabadRel.setVisibility(View.GONE);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_compose;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == back.getId()) {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                getActivity().onBackPressed();
            }
        }
        if (view.getId() == R.id.star_compose) {
            star = String.valueOf(simpleRatingBar.getRating());

        } else if (view.getId() == button.getId()) {
            String comment = editText.getText().toString();
            if (star != null && !comment.equals("")) {
                try {
                    myPresenter.sendComment(productId, userInfo.getId(), star, comment);
                } catch (NullPointerException e) {
                    openLogin.openLoginButtomsheet();
                }
            } else if (star == null)
                PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.nullStarError), getViewContext().getResources().getColor(R.color.red));

            else if (comment.equals(""))
                PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.nullCommentError), getViewContext().getResources().getColor(R.color.red));

        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void successfulCompose() {

        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.successful_compose), getResources().getColor(R.color.green));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        }, 3000);


    }

    @Override
    public void commentedBefore() {
        PublicMethods.setSnackbar(coordinatorLayout, getString(R.string.commented_before), getResources().getColor(R.color.red));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        openLogin=(OpenLogin)context;
    }
}
