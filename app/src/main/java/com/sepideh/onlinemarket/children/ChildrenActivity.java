package com.sepideh.onlinemarket.children;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.ChildProductAdapter;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.third.activity.ThirdActivity;

import java.util.List;

public class ChildrenActivity extends AppCompatActivity implements ChildrenContract.MyView,View.OnClickListener {

    ChildrenContract.MyPresenter myPresenter;
    RecyclerView recyclerView;
    ImageView back;
    TextView toolbarTitle,badgeNotif;
    RelativeLayout sabad;

    ChildProductAdapter catProductAdapter;
    String catChild;
    int catHeader;
    String catHeaderV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);
        myPresenter = new ChildernPresenter(new ChildrenModel());

        Intent intent=getIntent();
        if(intent!=null){
            catChild = intent.getStringExtra("catChild");
            catHeader = intent.getIntExtra("catHeader",0);
            switch (catHeader){
                case 1:
                    catHeaderV=getString(R.string.men);
                    break;
                case 2:
                    catHeaderV=getString(R.string.women);
                    break;
                case 3:
                    catHeaderV=getString(R.string.boys);
                    break;
                case 4:
                    catHeaderV=getString(R.string.girls);
                    break;
            }
        }
        setUpViews();
        setBadgeNotif();

    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
        myPresenter.getChildrenProducts(catChild,catHeader);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBadgeNotif();
    }

    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }


    public void setUpViews() {

        back=findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);
        toolbarTitle=findViewById(R.id.txv_toolbar_title);
        toolbarTitle.setText(catChild + " " + catHeaderV);
        badgeNotif=findViewById(R.id.badge_notif);
        sabad=findViewById(R.id.rel_detail_sabad);
        sabad.setOnClickListener(this);
        recyclerView = findViewById(R.id.rec_children);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }


    public void setBadgeNotif() {
        int sabadsize= Hawk.get(getString(R.string.Hawk_sabad_size),0);
        if(sabadsize>0){
            badgeNotif.setVisibility(View.VISIBLE);
            badgeNotif.setText(String.valueOf(sabadsize));}
        else badgeNotif.setVisibility(View.GONE);
    }
    @Override
    public void showChildrenProducts(List<ProductInfo> productInfos) {

        catProductAdapter = new ChildProductAdapter(this, productInfos);
        recyclerView.setAdapter(catProductAdapter);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==sabad.getId()){
            Intent intent=new Intent(this, ThirdActivity.class);
            startActivity(intent);
        }

        else if(view.getId()==back.getId())
            onBackPressed();
    }


}
