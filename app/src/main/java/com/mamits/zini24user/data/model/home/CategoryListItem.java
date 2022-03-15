package com.mamits.zini24user.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryListItem implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("banner_image")
    String banner_image;

    @SerializedName("category_order")
    int category_order;

    @SerializedName("image")
    String image;

    @SerializedName("bg_color")
    String bg_color;

    @SerializedName("description")
    String description;

    @SerializedName("short_description")
    String short_description;

    @SerializedName("subcategories")
    List<SubcategoryListItem> subcategories;

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

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

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public int getCategory_order() {
        return category_order;
    }

    public void setCategory_order(int category_order) {
        this.category_order = category_order;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<SubcategoryListItem> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryListItem> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public String toString() {
        return "CategoryListItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", banner_image='" + banner_image + '\'' +
                ", category_order=" + category_order +
                ", image='" + image + '\'' +
                ", bg_color='" + bg_color + '\'' +
                ", description='" + description + '\'' +
                ", short_description='" + short_description + '\'' +
                ", subcategories=" + subcategories +
                '}';
    }
}
