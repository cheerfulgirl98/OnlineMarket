
package com.sepideh.onlinemarket.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public Slider setId(String id) {
        this.id=id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Slider setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
