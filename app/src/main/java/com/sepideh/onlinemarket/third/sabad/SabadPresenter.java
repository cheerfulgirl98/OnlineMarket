package com.sepideh.onlinemarket.third.sabad;

import com.sepideh.onlinemarket.data.Sabad;

import java.util.List;

/**
 * Created by pc on 4/6/2019.
 */

public class SabadPresenter implements SabadContract.MyPresenter {
    SabadContract.MyView myView;
    SabadContract.MyModel myModel;


    public SabadPresenter(SabadContract.MyModel myModel) {
        this.myModel = myModel;
    }


    @Override
    public void attachView(SabadContract.MyView myView) {
        this.myView = myView;

    }

    @Override
    public void detachView() {

        myView = null;


    }

    @Override
    public void getSabadList() {
        List<Sabad> sabads = myModel.getSabadList();
        myView.showSabadList(sabads);


    }

    @Override
    public void plusClicked(Sabad sabad) {
        myModel.plusClicked(sabad);
    }

    @Override
    public void minusClicked(Sabad sabad) {
        myModel.minusClicked(sabad);

    }

    @Override
    public void delete(String pro_id) {
        myModel.delete(pro_id);
            myView.proIsDeleted();

    }
}
