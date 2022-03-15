package com.mamits.zini24user.data.model.payment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KeysDataModel implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("cashfree_mode")
    private String cashfree_mode;

    @SerializedName("appid")
    private String appid;

    @SerializedName("paytm_mode")
    private String paytm_mode;

    @SerializedName("paytm_merchant_key")
    private String paytm_merchant_key;

    @SerializedName("paytm_merchant_mid")
    private String paytm_merchant_mid;

    @SerializedName("paytm_merchant_website")
    private String paytm_merchant_website;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCashfree_mode() {
        return cashfree_mode;
    }

    public void setCashfree_mode(String cashfree_mode) {
        this.cashfree_mode = cashfree_mode;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPaytm_mode() {
        return paytm_mode;
    }

    public void setPaytm_mode(String paytm_mode) {
        this.paytm_mode = paytm_mode;
    }

    public String getPaytm_merchant_key() {
        return paytm_merchant_key;
    }

    public void setPaytm_merchant_key(String paytm_merchant_key) {
        this.paytm_merchant_key = paytm_merchant_key;
    }

    public String getPaytm_merchant_mid() {
        return paytm_merchant_mid;
    }

    public void setPaytm_merchant_mid(String paytm_merchant_mid) {
        this.paytm_merchant_mid = paytm_merchant_mid;
    }

    public String getPaytm_merchant_website() {
        return paytm_merchant_website;
    }

    public void setPaytm_merchant_website(String paytm_merchant_website) {
        this.paytm_merchant_website = paytm_merchant_website;
    }

    @Override
    public String toString() {
        return "KeysDataModel{" +
                "id=" + id +
                ", cashfree_mode='" + cashfree_mode + '\'' +
                ", appid='" + appid + '\'' +
                ", paytm_mode='" + paytm_mode + '\'' +
                ", paytm_merchant_key='" + paytm_merchant_key + '\'' +
                ", paytm_merchant_mid='" + paytm_merchant_mid + '\'' +
                ", paytm_merchant_website='" + paytm_merchant_website + '\'' +
                '}';
    }
}
