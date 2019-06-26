package com.sepideh.onlinemarket.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 5/11/2019.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public int tablesizeV;

    private static final String DATABASE_NAME = "online_market";
    private static final int DATABASE_VERSION = 1;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Favorit.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertFavorit(String product_id, String name, String brand, String model, int price, String discount, String url) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Favorit.COLUMN_PRODUCT_ID, product_id);
        contentValues.put(Favorit.COLUMN_NAME, name);
        contentValues.put(Favorit.COLUMN_BRAND, brand);
        contentValues.put(Favorit.COLUMN_MODEL, model);
        contentValues.put(Favorit.COLUMN_PRICE, price);
        contentValues.put(Favorit.COLUMN_DISCOUNT, discount);
        contentValues.put(Favorit.COLUMN_URL, url);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.insert(Favorit.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return result;


    }


    public List<Favorit> getAllFavorit(){
        String query="SELECT * FROM "+ Favorit.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);


        List<Favorit> favoritList = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Favorit favorit = new Favorit();
            favorit.setProduct_id(cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_PRODUCT_ID)))
                    .setName(cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_NAME)))
                    .setBrand(cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_BRAND)))
                    .setModel(cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_MODEL)))
                    .setPrice(cursor.getInt(cursor.getColumnIndex(Favorit.COLUMN_PRICE)))
                    .setDiscount(cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_DISCOUNT)))
                    .setUrl(cursor.getString(cursor.getColumnIndex(Favorit.COLUMN_URL)));
            favoritList.add(favorit);
        }


        sqLiteDatabase.close();
        return favoritList;

    }

    public boolean deleteNote(Favorit favorit) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(Favorit.TABLE_NAME, Favorit.COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(favorit.getProduct_id())});


        if(result==0)
            return false;
        else {
            return true;}
    }




}
