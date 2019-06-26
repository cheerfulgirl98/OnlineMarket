
package com.sepideh.onlinemarket.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails {

    @SerializedName("material")
    private String material;

    @SerializedName("size")
    private String size;

    @SerializedName("star")
    private String star;

    @SerializedName("cat_title")
    private String catTitle;

    @SerializedName("gender_name")
    private String genderName;

    @SerializedName("slider")
    private List<String> slider = null;

    @SerializedName("comments")
    private List<Comment> comments = null;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public String getCatTitle() {
        return catTitle;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public List<String> getSlider() {
        return slider;
    }

    public void setSlider(List<String> slider) {
        this.slider = slider;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "material='" + material + '\'' +
                ", size='" + size + '\'' +
                ", star='" + star + '\'' +
                ", catTitle='" + catTitle + '\'' +
                ", genderName='" + genderName + '\'' +
                ", slider=" + slider +
                ", comments=" + comments +
                '}';
    }
}
