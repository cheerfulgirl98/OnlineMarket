package com.sepideh.onlinemarket.main.favorit;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.FavoritAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.sqlite.Favorit;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;
import com.sepideh.onlinemarket.utils.PublicMethods;

import java.util.List;

/**
 * Created by pc on 5/18/2019.
 */

public class FavoritFragment extends BaseFragment implements FavoritContract.MyFragmentView,FavoritAdapter.DeleteInterface {

    FavoritContract.MyPresenter myPresenter;
    RecyclerView recyclerView;
    TextView emptyFavorit;

    SqliteHelper sqliteHelper;
    FavoritAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new FavoritPresenter(new FavoritModel());

    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
        myPresenter.getFavoritProducts(sqliteHelper);
    }

    @Override
    public void sendServerRequest() {

    }

    @Override
    public void noNetworkConnection() {

    }


    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void setUpViews() {

        emptyFavorit=rootView.findViewById(R.id.txv_sabad_emptyText);
        sqliteHelper=new SqliteHelper(getViewContext());
        recyclerView = rootView.findViewById(R.id.rec_favorit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext(), RecyclerView.VERTICAL, false));

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_favorit;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void noServerConnection() {


    }



    @Override
    public void showFavoritProducts(List<Favorit> favorits) {

        emptyFavorit.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter=new FavoritAdapter(getViewContext(),favorits);
        adapter.deleteInterface=this;
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void listIsEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyFavorit.setVisibility(View.VISIBLE);


    }

    @Override
    public void allFavoritDeleted() {

        listIsEmpty();

    }

    @Override
    public void deleteClicked(Favorit favorit) {
        myPresenter.deleteClicked(sqliteHelper,favorit);
    }

    @Override
    public void onActionConnection() {

    }

    @Override
    public void onActionNoConnection() {
        noNetworkConnection();
    }
}
