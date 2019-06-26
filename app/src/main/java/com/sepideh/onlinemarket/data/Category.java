
package com.sepideh.onlinemarket.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cat_child")
    @Expose
    private String catChild;
    @SerializedName("cat_header")
    @Expose
    private int catHeader;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatChild() {
        return catChild;
    }

    public void setCatChild(String catChild) {
        this.catChild = catChild;
    }

    public int getCatHeader() {
        return catHeader;
    }

    public void setCatHeader(int catHeader) {
        this.catHeader = catHeader;
    }

}
