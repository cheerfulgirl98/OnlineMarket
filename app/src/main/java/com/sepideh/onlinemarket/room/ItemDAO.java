package com.sepideh.onlinemarket.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sepideh.onlinemarket.data.NotifItem;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    public void insert(NotifItem notifItem);

    @Update
    public void update(NotifItem notifItem);

    @Delete
    public void delete(NotifItem notifItem);

    @Query("SELECT * FROM notifItems")
    public List<NotifItem> getItems();



}
