package com.sepideh.onlinemarket.main.favorit;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;
import com.sepideh.onlinemarket.sqlite.Favorit;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;

import java.util.List;

/**
 * Created by pc on 5/21/2019.
 */

public interface FavoritContract {

    interface MyView extends BaseView {

        void showFavoritProducts(List<Favorit> favorits);
        void listIsEmpty();
        void allFavoritDeleted();
    }

    interface MyPresenter extends BasePresenter<MyView> {
        void getFavoritProducts(SqliteHelper sqliteHelper);
        void deleteClicked(SqliteHelper sqliteHelper, Favorit favorit);
    }

    interface MyModel{
        List<Favorit> getFavoritProducts(SqliteHelper sqliteHelper);
        boolean deleteClicked(SqliteHelper sqliteHelper, Favorit favorit);
    }
}
