package com.sepideh.onlinemarket.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pc on 5/25/2019.
 */

public class Sabad extends RealmObject {

    @PrimaryKey
    private int id;
    private String pro_id, name, brand, model, discount, url;
    private int num, price;

    public Sabad() {
    }

    public Sabad(String pro_id, String name, String brand, String model, int num, int price, String discount, String url) {
        this.pro_id = pro_id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.num = num;
        this.discount = discount;
        this.price = price;
        this.url = url;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
