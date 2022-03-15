package com.mamits.zini24user.data.model.orders;

import com.google.gson.annotations.SerializedName;
import com.mamits.zini24user.data.model.home.TimerObj;
import com.mamits.zini24user.data.model.login.UserDataModel;
import com.mamits.zini24user.data.model.product.ProductDataModel;

import java.io.Serializable;
import java.util.List;

public class OrdersDataModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("order_id")
    String order_id;

    @SerializedName("user_id")
    int user_id;

    @SerializedName("store_id")
    int store_id;

    @SerializedName("product_id")
    int product_id;

    @SerializedName("order_amount")
    String order_amount;

    @SerializedName("payable_amount")
    String payable_amount;

    @SerializedName("offer_id")
    int offer_id;

    @SerializedName("offer_amount")
    String offer_amount;

    @SerializedName("status")
    int status;

    @SerializedName("payment_status")
    int payment_status;

    @SerializedName("description")
    String description;

    @SerializedName("payment_type")
    String payment_type;

    @SerializedName("payment_file")
    String payment_file;

    @SerializedName("type")
    String type;

    @SerializedName("order_completed_by")
    int order_completed_by;

    @SerializedName("order_detail")
    List<OrderDetailDataModel> order_detail;

    @SerializedName("orderdatetime")
    String orderdatetime;

    @SerializedName("order_completion_time")
    int order_completion_time;

    @SerializedName("rating_status")
    int rating_status;

    @SerializedName("userratting")
    float userratting;

    @SerializedName("createdBy")
    int createdBy;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("accepted_at")
    String accepted_at;

    @SerializedName("updated_at")
    String updated_at;

    @SerializedName("products")
    ProductDataModel products;

    @SerializedName("users")
    UserDataModel users;

    @SerializedName("storedetail")
    StoreDataModel storedetail;

    @SerializedName("time_type")
    String time_type;

    @SerializedName("isPaytoShop")
    String isPaytoShop;

    float myrating;

    TimerObj timerObj = null;

    public String getIsPaytoShop() {
        return isPaytoShop;
    }

    public void setIsPaytoShop(String isPaytoShop) {
        this.isPaytoShop = isPaytoShop;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public String getOffer_amount() {
        return offer_amount;
    }

    public void setOffer_amount(String offer_amount) {
        this.offer_amount = offer_amount;
    }

    public float getMyrating() {
        return myrating;
    }

    public void setMyrating(float myrating) {
        this.myrating = myrating;
    }

    public float getUserratting() {
        return userratting;
    }

    public void setUserratting(float userratting) {
        this.userratting = userratting;
    }

    public String getPayment_file() {
        return payment_file;
    }

    public void setPayment_file(String payment_file) {
        this.payment_file = payment_file;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public String getAccepted_at() {
        return accepted_at;
    }

    public void setAccepted_at(String accepted_at) {
        this.accepted_at = accepted_at;
    }

    public TimerObj getTimerObj() {
        return timerObj;
    }

    public void setTimerObj(TimerObj timerObj) {
        this.timerObj = timerObj;
    }

    public StoreDataModel getStoredetail() {
        return storedetail;
    }

    public void setStoredetail(StoreDataModel storedetail) {
        this.storedetail = storedetail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(String payable_amount) {
        this.payable_amount = payable_amount;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder_completed_by() {
        return order_completed_by;
    }

    public void setOrder_completed_by(int order_completed_by) {
        this.order_completed_by = order_completed_by;
    }

    public List<OrderDetailDataModel> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailDataModel> order_detail) {
        this.order_detail = order_detail;
    }

    public String getOrderdatetime() {
        return orderdatetime;
    }

    public void setOrderdatetime(String orderdatetime) {
        this.orderdatetime = orderdatetime;
    }

    public int getOrder_completion_time() {
        return order_completion_time;
    }

    public void setOrder_completion_time(int order_completion_time) {
        this.order_completion_time = order_completion_time;
    }

    public int getRating_status() {
        return rating_status;
    }

    public void setRating_status(int rating_status) {
        this.rating_status = rating_status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public ProductDataModel getProducts() {
        return products;
    }

    public void setProducts(ProductDataModel products) {
        this.products = products;
    }

    public UserDataModel getUsers() {
        return users;
    }

    public void setUsers(UserDataModel users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "OrdersDataModel{" +
                "id=" + id +
                ", order_id='" + order_id + '\'' +
                ", user_id=" + user_id +
                ", store_id=" + store_id +
                ", product_id=" + product_id +
                ", order_amount='" + order_amount + '\'' +
                ", payable_amount='" + payable_amount + '\'' +
                ", offer_id=" + offer_id +
                ", offer_amount='" + offer_amount + '\'' +
                ", status=" + status +
                ", payment_status=" + payment_status +
                ", description='" + description + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", payment_file='" + payment_file + '\'' +
                ", type='" + type + '\'' +
                ", order_completed_by=" + order_completed_by +
                ", order_detail=" + order_detail +
                ", orderdatetime='" + orderdatetime + '\'' +
                ", order_completion_time=" + order_completion_time +
                ", rating_status=" + rating_status +
                ", userratting=" + userratting +
                ", createdBy=" + createdBy +
                ", created_at='" + created_at + '\'' +
                ", accepted_at='" + accepted_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", products=" + products +
                ", users=" + users +
                ", storedetail=" + storedetail +
                ", time_type='" + time_type + '\'' +
                ", isPaytoShop='" + isPaytoShop + '\'' +
                ", myrating=" + myrating +
                ", timerObj=" + timerObj +
                '}';
    }
}
