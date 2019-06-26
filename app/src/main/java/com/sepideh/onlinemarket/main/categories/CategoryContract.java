package com.sepideh.onlinemarket.main.categories;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;
import com.sepideh.onlinemarket.data.Category;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 5/18/2019.
 */

public interface CategoryContract {

    interface MyView extends BaseView{
        void childrenAreReady(List<Category> childern);
    }

    interface MyPresenter extends BasePresenter<MyView>{
        void getChildern();
    }

    interface MyModel{
        Single<List<Category>> getChildern();
    }
}
