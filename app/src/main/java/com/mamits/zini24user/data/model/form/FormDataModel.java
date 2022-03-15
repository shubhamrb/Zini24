package com.mamits.zini24user.data.model.form;

import com.google.gson.annotations.SerializedName;
import com.mamits.zini24user.data.model.orders.OrderDetailDataModel;

import java.io.Serializable;
import java.util.List;

public class FormDataModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("product_id")
    int product_id;

    @SerializedName("product_type")
    int product_type;

    @SerializedName("name")
    String name;

    @SerializedName("store_id")
    int store_id;

    @SerializedName("price")
    String price;

    @SerializedName("variation_price")
    List<OrderDetailDataModel> variation_price;

    @SerializedName("discount")
    int discount;

    @SerializedName("discount_type")
    int discount_type;

    @SerializedName("description")
    String description;

    @SerializedName("short_description")
    String short_description;

    @SerializedName("selectedPrice")
    String selectedPrice;

    @SerializedName("form")
    List<FormItem> form;

    public String getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(String selectedPrice) {
        this.selectedPrice = selectedPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<OrderDetailDataModel> getVariation_price() {
        return variation_price;
    }

    public void setVariation_price(List<OrderDetailDataModel> variation_price) {
        this.variation_price = variation_price;
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

    public List<FormItem> getForm() {
        return form;
    }

    public void setForm(List<FormItem> form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "FormDataModel{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", product_type=" + product_type +
                ", name='" + name + '\'' +
                ", store_id=" + store_id +
                ", price='" + price + '\'' +
                ", variation_price=" + variation_price +
                ", discount=" + discount +
                ", discount_type=" + discount_type +
                ", description='" + description + '\'' +
                ", short_description='" + short_description + '\'' +
                ", selectedPrice='" + selectedPrice + '\'' +
                ", form=" + form +
                '}';
    }
}
