package com.sepideh.onlinemarket.utils;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.hawk.Hawk;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by pc on 4/14/2019.
 */

public class MyApplication extends Application {

    public static MyApplication appInstance;
    public static Typeface NORMAL_TYPEFACE;


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance=this;
        Hawk.init(this).build();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);

        NORMAL_TYPEFACE = Typeface.createFromAsset(getAssets()
                , "fonts/ir_sans.ttf");
    }
}
