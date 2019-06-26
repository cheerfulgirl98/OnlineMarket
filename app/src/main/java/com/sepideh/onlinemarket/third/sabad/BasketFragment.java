package com.sepideh.onlinemarket.third.sabad;

import android.content.Context;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.SabadAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.third.form.OrderFormFragment;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.List;

/**
 * Created by pc on 5/27/2019.
 */

public class BasketFragment extends BaseFragment implements SabadContract.MyView, View.OnClickListener, SabadAdapter.MyClicked {
    SabadContract.MyPresenter myPresenter;
    ImageView back;
    BottomSheetBehavior bottomSheetBehavior;
    TextView toolbarTitle, badgeNotif, buy, totalCost, emptyText;
    RelativeLayout costBtmSheet;

    RecyclerView recyclerView;
    SabadAdapter adapter;

    int sabadSize;
    int finalCost;

    @Override
    public void setUpViews() {

        myPresenter = new SabadPresenter(new SabadModel());
        toolbarTitle = rootView.findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(getString(R.string.your_basket));

        back = rootView.findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);
        badgeNotif = rootView.findViewById(R.id.badge_notif);


        recyclerView = rootView.findViewById(R.id.rec_sbad);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));

        emptyText = rootView.findViewById(R.id.txv_sabad_emptyText);

        costBtmSheet = rootView.findViewById(R.id.rel_sabad_btmsheet);


        totalCost = rootView.findViewById(R.id.txv_sabad_totalCost);
        buy = rootView.findViewById(R.id.txv_sabad_buy);
        buy.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
        manageSabad();


    }
    void manageSabad(){

        sabadSize=Hawk.get(getString(R.string.Hawk_sabad_size), 0);
        Log.d("rrr", "manageSabad: "+ sabadSize);
        if (sabadSize >= 1)
        {
            badgeNotif.setVisibility(View.VISIBLE);
            badgeNotif.setText(String.valueOf(sabadSize));
            myPresenter.getSabadList();
            costBtmSheet.setVisibility(View.VISIBLE);
        } else {

            sabadIsEmpty();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == buy.getId()) {
            if (!PublicMethods.checkLogin()) {
                PublicMethods.goNewFragment(getViewContext(),R.id.frame_third_container,new OrderFormFragment());

            } else
                openLogin.openLoginButtomsheet();
        } else if (view.getId() == back.getId()) {
            getActivity().onBackPressed();
        }

    }


    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showSabadList(List<Sabad> sabads) {
        setFinalCost(sabads);
        emptyText.setVisibility(View.GONE);
        adapter = new SabadAdapter(getViewContext(), sabads);
        adapter.clickInstance = this;
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void proIsDeleted() {

        sabadSize=sabadSize-1;
        Hawk.put(getString(R.string.Hawk_sabad_size),sabadSize);
        manageSabad();

    }

    void setFinalCost(List<Sabad> sabads){
       finalCost=0;
            for (Sabad sabad : sabads) {

                int finalPrice = sabad.getNum()*(Integer.parseInt(sabad.getDiscount()));
                finalCost = finalCost + finalPrice;
            }
        totalCost.setText(String.valueOf(finalCost));


    }
    public void sabadIsEmpty() {

        badgeNotif.setVisibility(View.GONE);
        recyclerView.setVisibility(View.INVISIBLE);
        emptyText.setVisibility(View.VISIBLE);
        costBtmSheet.setVisibility(View.GONE);
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_sabad;
    }

    @Override
    public void plusClicked(Sabad sabad) {
        myPresenter.plusClicked(sabad);

        int proFinalPrice = Integer.parseInt(sabad.getDiscount());
        Log.d("jam", "plusClicked: "+ proFinalPrice + "  " +finalCost);
        finalCost = finalCost + proFinalPrice;
        totalCost.setText(String.valueOf(finalCost));
    }

    @Override
    public void minusClicked(Sabad sabad) {

        myPresenter.minusClicked(sabad);

        int proFinalPrice = Integer.parseInt(sabad.getDiscount());
        finalCost = finalCost - proFinalPrice;
        totalCost.setText(String.valueOf(finalCost));
    }

    @Override
    public void deleteClicked(String pro_id) {
        myPresenter.delete(pro_id);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        openLogin=(OpenLogin)context;
    }
}
