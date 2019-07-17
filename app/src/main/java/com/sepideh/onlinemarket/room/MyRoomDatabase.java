package com.sepideh.onlinemarket.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.sepideh.onlinemarket.data.NotifItem;


@Database(entities = {NotifItem.class},exportSchema = false, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract ItemDAO getItemDao();

    private static MyRoomDatabase instance;
    public static synchronized MyRoomDatabase getAppDatabase(Context context){
        if(instance==null)
            instance= Room.databaseBuilder(context,MyRoomDatabase.class,"MyDb").allowMainThreadQueries().build();

            return instance;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
