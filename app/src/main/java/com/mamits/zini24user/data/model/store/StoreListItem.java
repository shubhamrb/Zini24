package com.mamits.zini24user.data.model.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreListItem implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("ratting")
    float ratting;

    @SerializedName("payment_accept_mode")
    String payment_accept_mode;

    @SerializedName("IsAvailable")
    int IsAvailable;

    @SerializedName("user_id")
    int user_id;

    @SerializedName("openingtime")
    String openingtime;

    @SerializedName("closingtime")
    String closingtime;

    @SerializedName("image")
    String image;

    @SerializedName("distance")
    String distance;

    @SerializedName("openstatus")
    int openstatus;

    @SerializedName("is_available")
    String is_available;

    @SerializedName("next")
    boolean next;

    @SerializedName("price")
    double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public float getRatting() {
        return ratting;
    }

    public void setRatting(float ratting) {
        this.ratting = ratting;
    }

    public String getPayment_accept_mode() {
        return payment_accept_mode;
    }

    public void setPayment_accept_mode(String payment_accept_mode) {
        this.payment_accept_mode = payment_accept_mode;
    }

    public int getIsAvailable() {
        return IsAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        IsAvailable = isAvailable;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getOpenstatus() {
        return openstatus;
    }

    public void setOpenstatus(int openstatus) {
        this.openstatus = openstatus;
    }

    public String getIs_available() {
        return is_available;
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
    }

    @Override
    public String toString() {
        return "StoreListItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", ratting=" + ratting +
                ", payment_accept_mode='" + payment_accept_mode + '\'' +
                ", IsAvailable=" + IsAvailable +
                ", user_id=" + user_id +
                ", openingtime='" + openingtime + '\'' +
                ", closingtime='" + closingtime + '\'' +
                ", image='" + image + '\'' +
                ", distance='" + distance + '\'' +
                ", openstatus=" + openstatus +
                ", is_available='" + is_available + '\'' +
                ", next=" + next +
                ", price=" + price +
                '}';
    }
}
