package com.sepideh.onlinemarket.main.categories;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseFragmentView;
import com.sepideh.onlinemarket.data.Category;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pc on 5/18/2019.
 */

public interface CategoryContract {

    interface MyFragmentView extends BaseFragmentView {
        void childrenAreReady(List<Category> childern);

    }

    interface MyPresenter extends BasePresenter<MyFragmentView>{
        void getChildern();
    }

    interface MyModel{
        Single<List<Category>> getChildern();
    }
}
