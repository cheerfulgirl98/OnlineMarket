package com.sepideh.onlinemarket.sqlite;

import java.io.Serializable;

/**
 * Created by pc on 5/11/2019.
 */

public class Favorit implements Serializable {

    public static final String TABLE_NAME = "favorit";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DISCOUNT = "discount";
    public static final String COLUMN_PERCENTAGE = "percentage";
    public static final String COLUMN_URL = "url";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ID + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_BRAND + " TEXT,"
                    + COLUMN_MODEL + " TEXT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_DISCOUNT + " TEXT,"
                    + COLUMN_PERCENTAGE + " TEXT,"
                    + COLUMN_URL + " TEXT"
                    + ")";


    private int id;

    private String product_id;

    private String name;

    private String brand;

    private String model;

    private int price;

    private String discount;

    private String percentage;

    private String url;


    public Favorit(int id, String product_id, String name, String brand, String model, int price, String discount, String percentage, String url) {
        this.id = id;
        this.product_id = product_id;
        this.name = name;
        this.model=model;
        this.brand = brand;
        this.price = price;
        this.discount = discount;
        this.percentage = percentage;
        this.url=url;
    }

    public Favorit() {
    }

    public int getId() {
        return id;
    }

    public Favorit setId(int id) {
        this.id = id;
        return this;
    }

    public String getProduct_id() {
        return product_id;
    }

    public Favorit setProduct_id(String product_id) {
        this.product_id = product_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Favorit setName(String name) {
        this.name = name;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Favorit setModel(String model) {
        this.model = model;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Favorit setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Favorit setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getDiscount() {
        return discount;
    }

    public Favorit setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getPercentage() {
        return percentage;
    }

    public Favorit setPercentage(String percentage) {
        this.percentage = percentage;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Favorit setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "Favorit{" +
                "id=" + id +
                ", product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", discount='" + discount + '\'' +
                ", percentage='" + percentage + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
