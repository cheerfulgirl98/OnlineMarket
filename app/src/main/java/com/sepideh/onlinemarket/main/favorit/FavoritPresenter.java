package com.sepideh.onlinemarket.main.favorit;

import com.sepideh.onlinemarket.sqlite.Favorit;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;

import java.util.List;

/**
 * Created by pc on 5/21/2019.
 */

public class FavoritPresenter implements FavoritContract.MyPresenter {
    FavoritContract.MyView myView;
    FavoritContract.MyModel myModel;

    public FavoritPresenter(FavoritContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(FavoritContract.MyView view) {

        this.myView = view;
    }

    @Override
    public void detachView() {

        this.myView = null;
    }

    @Override
    public void getFavoritProducts(SqliteHelper sqliteHelper) {

        List<Favorit> favorits=myModel.getFavoritProducts(sqliteHelper);

        if (favorits.size()>0)
            myView.showFavoritProducts(favorits);
        else
            myView.listIsEmpty();
    }

    @Override
    public void deleteClicked(SqliteHelper sqliteHelper, Favorit favorit) {
        myModel.deleteClicked(sqliteHelper,favorit);

       if(sqliteHelper.getAllFavorit().size()==0) {
           myView.allFavoritDeleted();
       }

    }
}
