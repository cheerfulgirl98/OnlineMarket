package com.sepideh.onlinemarket.realm;

import android.util.Log;

import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Sabad;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by pc on 5/25/2019.
 */

public class RealmController {

    private Realm realm = Realm.getDefaultInstance();


    //find all objects in the Book.class
    public List<Sabad> getSabads() {

        RealmResults<Sabad> sabads= realm.where(Sabad.class).findAll();
        List<Sabad> sabadArrayList=new ArrayList<>();
        sabadArrayList.addAll(sabads);

        return sabadArrayList;
    }

    public Sabad searchInSabad(ProductInfo productInfo){

        return realm.where(Sabad.class).equalTo("pro_id",productInfo.getId()).findFirst();

    }

    public  void saveOnRealm(ProductInfo productInfo) {

        realm.beginTransaction();

        int nextId;
        try {
            nextId = realm.where(Sabad.class).max("id").intValue() + 1;
        }catch (NullPointerException e){
            nextId=0;
        }
        Sabad sabad = realm.createObject(Sabad.class,nextId);
        sabad.setPro_id(productInfo.getId());
        sabad.setName(productInfo.getName());
        sabad.setBrand(productInfo.getBrand());
        sabad.setModel(productInfo.getModel());
        sabad.setNum(1);
        sabad.setPrice(productInfo.getPrice());
        sabad.setDiscount(productInfo.getDiscount());
        sabad.setUrl(productInfo.getUrl());
        realm.commitTransaction();
    }

    public void plusNum(Sabad sabad){
        realm.beginTransaction();
        sabad.setNum(sabad.getNum()+1);
        realm.commitTransaction();
    }
    public void minusNum(Sabad sabad){
        realm.beginTransaction();
        sabad.setNum(sabad.getNum()-1);
        realm.commitTransaction();

    }

    public void delete(String pro_id){
        realm.beginTransaction();
        RealmResults<Sabad> realmResults=realm.where(Sabad.class).equalTo("pro_id",pro_id).findAll();
        realmResults.deleteAllFromRealm();
        realm.commitTransaction();



    }


    }
