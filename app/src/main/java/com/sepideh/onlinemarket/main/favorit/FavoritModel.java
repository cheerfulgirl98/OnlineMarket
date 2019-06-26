package com.sepideh.onlinemarket.main.favorit;

import com.sepideh.onlinemarket.sqlite.Favorit;
import com.sepideh.onlinemarket.sqlite.SqliteHelper;

import java.util.List;

/**
 * Created by pc on 5/21/2019.
 */

public class FavoritModel implements FavoritContract.MyModel {
    @Override
    public List<Favorit> getFavoritProducts(SqliteHelper sqliteHelper) {

        return sqliteHelper.getAllFavorit();
    }

    @Override
    public boolean deleteClicked(SqliteHelper sqliteHelper, Favorit favorit) {
        return sqliteHelper.deleteNote(favorit);

    }
}
