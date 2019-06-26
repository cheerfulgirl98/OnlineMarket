package com.sepideh.onlinemarket.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pc on 5/14/2019.
 */

public class ProductInfo implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("brand")
    private String brand;

    @SerializedName("model")
    private String model;

    @SerializedName("material")
    private String material;

    @SerializedName("size")
    private String size;

    @SerializedName("price")
    private int price;

    @SerializedName("discount")
    private String discount;

    @SerializedName("url")
    private String url;

    @SerializedName("cat_title")
    private String catTitle;

    @SerializedName("gender_name")
    private String genderName;

    @SerializedName("slider")
    private List<String> slider = null;

    @SerializedName("comments")
    private List<Comment> comments = null;


    public String getId() {
        return id;
    }

    public ProductInfo setId(String id) {

        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductInfo setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductInfo setModel(String model) {
        this.model = model;
        return this;
    }

    public String getMaterial() {
        return material;
    }

    public ProductInfo setMaterial(String material) {
        this.material = material;
        return this;
    }

    public String getSize() {
        return size;
    }

    public ProductInfo setSize(String size) {
        this.size = size;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public ProductInfo setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getDiscount() {
        return discount;
    }

    public ProductInfo setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ProductInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public ProductInfo setCatTitle(String catTitle) {
        this.catTitle = catTitle;
        return this;
    }

    public String getGenderName() {
        return genderName;
    }

    public ProductInfo setGenderName(String genderName) {
        this.genderName = genderName;
        return this;
    }

    public List<String> getSlider() {
        return slider;
    }

    public ProductInfo setSlider(List<String> slider) {
        this.slider = slider;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public ProductInfo setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", material='" + material + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", discount='" + discount + '\'' +
                ", url='" + url + '\'' +
                ", catTitle='" + catTitle + '\'' +
                ", genderName='" + genderName + '\'' +
                ", slider=" + slider +
                ", comments=" + comments +
                '}';
    }
}
