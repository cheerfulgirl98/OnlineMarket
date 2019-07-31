package com.sepideh.onlinemarket.third.sabad;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseFragmentView;
import com.sepideh.onlinemarket.data.Sabad;

import java.util.List;

/**
 * Created by pc on 4/6/2019.
 */

public interface SabadContract {

    interface MyFragmentView extends BaseFragmentView {
        void showSabadList(List<Sabad> sabads);
        void proIsDeleted();


    }
    interface MyPresenter extends BasePresenter<MyFragmentView> {
        void getSabadList();
        void plusClicked(Sabad sabad);
        void minusClicked(Sabad sabad);
        void delete(String pro_id);



    }
    interface MyModel{
        List<Sabad> getSabadList();
        void plusClicked(Sabad sabad);
        void minusClicked(Sabad sabad);
        void delete(String pro_id);




    }
}
