package com.sepideh.onlinemarket.main.categories;

import com.sepideh.onlinemarket.data.Category;
import com.sepideh.onlinemarket.utils.Constant;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 5/18/2019.
 */

public class CategoryModel implements CategoryContract.MyModel {

    @Override
    public Single<List<Category>> getChildern() {
        return Constant.apiService.getChildern();
    }
}
