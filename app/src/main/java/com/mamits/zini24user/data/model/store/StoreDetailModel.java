package com.mamits.zini24user.data.model.store;

import com.google.gson.annotations.SerializedName;
import com.mamits.zini24user.data.model.home.CategoryListItem;
import com.mamits.zini24user.data.model.home.SubcategoryListItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class StoreDetailModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("ratting")
    float ratting;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("storelogo")
    String storelogo;

    @SerializedName("openingtime")
    String openingtime;

    @SerializedName("closingtime")
    String closingtime;

    @SerializedName("description")
    String description;

    @SerializedName("is_available")
    int is_available;

    @SerializedName("IsAvailable")
    int IsAvailable;

    @SerializedName("distance")
    int distance;

    @SerializedName("openstatus")
    int openstatus;

    @SerializedName("totalrating")
    int totalrating;

    @SerializedName("rating")
    HashMap<String,Integer> rating;

    @SerializedName("category")
    List<SubcategoryListItem> category;

    @SerializedName("banner_image")
    List<String> banner_image;

    public List<String> getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(List<String> banner_image) {
        this.banner_image = banner_image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatting() {
        return ratting;
    }

    public void setRatting(float ratting) {
        this.ratting = ratting;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStorelogo() {
        return storelogo;
    }

    public void setStorelogo(String storelogo) {
        this.storelogo = storelogo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIs_available() {
        return is_available;
    }

    public void setIs_available(int is_available) {
        this.is_available = is_available;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getOpenstatus() {
        return openstatus;
    }

    public void setOpenstatus(int openstatus) {
        this.openstatus = openstatus;
    }

    public int getTotalrating() {
        return totalrating;
    }

    public void setTotalrating(int totalrating) {
        this.totalrating = totalrating;
    }

    public HashMap<String, Integer> getRating() {
        return rating;
    }

    public void setRating(HashMap<String, Integer> rating) {
        this.rating = rating;
    }

    public List<SubcategoryListItem> getCategory() {
        return category;
    }

    public void setCategory(List<SubcategoryListItem> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "StoreDetailModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ratting=" + ratting +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", storelogo='" + storelogo + '\'' +
                ", openingtime='" + openingtime + '\'' +
                ", closingtime='" + closingtime + '\'' +
                ", description='" + description + '\'' +
                ", is_available=" + is_available +
                ", IsAvailable=" + IsAvailable +
                ", distance=" + distance +
                ", openstatus=" + openstatus +
                ", totalrating=" + totalrating +
                ", rating=" + rating +
                ", category=" + category +
                ", banner_image=" + banner_image +
                '}';
    }
}
