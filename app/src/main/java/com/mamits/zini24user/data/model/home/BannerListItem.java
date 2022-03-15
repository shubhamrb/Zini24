package com.mamits.zini24user.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannerListItem implements Serializable {
    @SerializedName("url")
    String url;

    @SerializedName("product_id")
    String product_id;

    @SerializedName("image")
    String image;

    @SerializedName("id")
    String id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BannerListItem{" +
                "url='" + url + '\'' +
                ", product_id='" + product_id + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
