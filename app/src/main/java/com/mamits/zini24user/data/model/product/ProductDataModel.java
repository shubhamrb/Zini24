package com.mamits.zini24user.data.model.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductDataModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("form_id")
    int form_id;

    @SerializedName("name")
    String name;

    @SerializedName("category_id")
    String category_id;

    @SerializedName("sub_category_id")
    int sub_category_id;

    @SerializedName("product_type")
    int product_type;

    @SerializedName("image")
    String image;

    @SerializedName("price")
    String price;

    @SerializedName("meta_data")
    String meta_data;

    @SerializedName("meta_description")
    String meta_description;

    @SerializedName("discount")
    int discount;

    @SerializedName("discount_type")
    int discount_type;

    @SerializedName("description")
    String description;

    @SerializedName("short_description")
    String short_description;

    @SerializedName("variation")
    String variation;

    @SerializedName("created_by")
    int created_by;

    @SerializedName("IsHomePage")
    int IsHomePage;

    @SerializedName("IsVerify")
    int IsVerify;

    @SerializedName("IsActive")
    int IsActive;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;

    @SerializedName("product_image")
    String product_image;


    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForm_id() {
        return form_id;
    }

    public void setForm_id(int form_id) {
        this.form_id = form_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(String meta_data) {
        this.meta_data = meta_data;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getIsHomePage() {
        return IsHomePage;
    }

    public void setIsHomePage(int isHomePage) {
        IsHomePage = isHomePage;
    }

    public int getIsVerify() {
        return IsVerify;
    }

    public void setIsVerify(int isVerify) {
        IsVerify = isVerify;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "ProductDataModel{" +
                "id=" + id +
                ", form_id=" + form_id +
                ", name='" + name + '\'' +
                ", category_id='" + category_id + '\'' +
                ", sub_category_id=" + sub_category_id +
                ", product_type=" + product_type +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", meta_data='" + meta_data + '\'' +
                ", meta_description='" + meta_description + '\'' +
                ", discount=" + discount +
                ", discount_type=" + discount_type +
                ", description='" + description + '\'' +
                ", short_description='" + short_description + '\'' +
                ", variation='" + variation + '\'' +
                ", created_by=" + created_by +
                ", IsHomePage=" + IsHomePage +
                ", IsVerify=" + IsVerify +
                ", IsActive=" + IsActive +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", product_image='" + product_image + '\'' +
                '}';
    }
}
