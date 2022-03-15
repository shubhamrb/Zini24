package com.mamits.zini24user.data.model.orders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreDataModel implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("store_id")
    private String store_id;

    @SerializedName("name")
    private String name;

    @SerializedName("mobile_number")
    private String mobile_number;

    @SerializedName("qr_image")
    private String qr_image;

    @SerializedName("address")
    private String address;

    @SerializedName("openingtime")
    private String openingtime;

    @SerializedName("closingtime")
    private String closingtime;

    @SerializedName("payment_accept_mode")
    private String payment_accept_mode;

    @SerializedName("whatsapp_no")
    private String whatsapp_no;

    @SerializedName("upi_number")
    private String upi_number;

    @SerializedName("account_number")
    private String account_number;

    @SerializedName("bank_name")
    private String bank_name;

    @SerializedName("ifsc_code")
    private String ifsc_code;

    @SerializedName("description")
    private String description;

    @SerializedName("IsAvailable")
    private int IsAvailable;

    public String getQr_image() {
        return qr_image;
    }

    public void setQr_image(String qr_image) {
        this.qr_image = qr_image;
    }

    public int getIsAvailable() {
        return IsAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        IsAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningtime() {
        return openingtime;
    }

    public void setOpeningtime(String openingtime) {
        this.openingtime = openingtime;
    }

    public String getClosingtime() {
        return closingtime;
    }

    public void setClosingtime(String closingtime) {
        this.closingtime = closingtime;
    }

    public String getPayment_accept_mode() {
        return payment_accept_mode;
    }

    public void setPayment_accept_mode(String payment_accept_mode) {
        this.payment_accept_mode = payment_accept_mode;
    }

    public String getWhatsapp_no() {
        return whatsapp_no;
    }

    public void setWhatsapp_no(String whatsapp_no) {
        this.whatsapp_no = whatsapp_no;
    }

    public String getUpi_number() {
        return upi_number;
    }

    public void setUpi_number(String upi_number) {
        this.upi_number = upi_number;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StoreDataModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", store_id='" + store_id + '\'' +
                ", name='" + name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", qr_image='" + qr_image + '\'' +
                ", address='" + address + '\'' +
                ", openingtime='" + openingtime + '\'' +
                ", closingtime='" + closingtime + '\'' +
                ", payment_accept_mode='" + payment_accept_mode + '\'' +
                ", whatsapp_no='" + whatsapp_no + '\'' +
                ", upi_number='" + upi_number + '\'' +
                ", account_number='" + account_number + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", ifsc_code='" + ifsc_code + '\'' +
                ", description='" + description + '\'' +
                ", IsAvailable=" + IsAvailable +
                '}';
    }
}
