package com.mamits.zini24user.data.model.offer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfferDataModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("coupon")
    String coupon;

    @SerializedName("description")
    String description;

    @SerializedName("discount_amount")
    String discount_amount;

    @SerializedName("store_id")
    int store_id;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    @Override
    public String toString() {
        return "OfferDataModel{" +
                "id=" + id +
                ", coupon='" + coupon + '\'' +
                ", description='" + description + '\'' +
                ", discount_amount='" + discount_amount + '\'' +
                ", store_id=" + store_id +
                '}';
    }
}
