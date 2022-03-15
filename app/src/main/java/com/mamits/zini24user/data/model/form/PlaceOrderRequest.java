package com.mamits.zini24user.data.model.form;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlaceOrderRequest implements Serializable {
    @SerializedName("store_id")
    String store_id;

    @SerializedName("form_data")
    List<PlaceOderFormData> form_data;

    @SerializedName("price")
    String price;

    @SerializedName("product_id")
    String product_id;

    @SerializedName("type")
    String type;

    public PlaceOrderRequest(String store_id, List<PlaceOderFormData> form_data, String price, String product_id, String type) {
        this.store_id = store_id;
        this.form_data = form_data;
        this.price = price;
        this.product_id = product_id;
        this.type = type;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public List<PlaceOderFormData> getForm_data() {
        return form_data;
    }

    public void setForm_data(List<PlaceOderFormData> form_data) {
        this.form_data = form_data;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PlaceOrderRequest{" +
                "store_id='" + store_id + '\'' +
                ", form_data=" + form_data +
                ", price='" + price + '\'' +
                ", product_id='" + product_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
