package com.sepideh.onlinemarket.third.sabad;

import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.realm.RealmController;

import java.util.List;

/**
 * Created by pc on 4/6/2019.
 */

public class SabadModel implements SabadContract.MyModel {

    private RealmController realmController=new RealmController();

    @Override
    public List<Sabad> getSabadList() {
        return realmController.getSabads();
    }

    @Override
    public void plusClicked(Sabad sabad) {
        realmController.plusNum(sabad);
    }

    @Override
    public void minusClicked(Sabad sabad) {
        realmController.minusNum(sabad);

    }

    @Override
    public void delete(String pro_id) {
        realmController.delete(pro_id);

    }
}
