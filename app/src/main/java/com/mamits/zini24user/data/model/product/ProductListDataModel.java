package com.mamits.zini24user.data.model.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductListDataModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductListDataModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
