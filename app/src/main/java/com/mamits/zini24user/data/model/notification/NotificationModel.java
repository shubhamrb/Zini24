package com.mamits.zini24user.data.model.notification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("sent_by")
    String sent_by;

    @SerializedName("sent_to")
    int sent_to;

    @SerializedName("noti_type")
    String noti_type;

    @SerializedName("is_read")
    int is_read;

    @SerializedName("status")
    int status;

    @SerializedName("message")
    String message;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSent_by() {
        return sent_by;
    }

    public void setSent_by(String sent_by) {
        this.sent_by = sent_by;
    }

    public int getSent_to() {
        return sent_to;
    }

    public void setSent_to(int sent_to) {
        this.sent_to = sent_to;
    }

    public String getNoti_type() {
        return noti_type;
    }

    public void setNoti_type(String noti_type) {
        this.noti_type = noti_type;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        return "NotificationModel{" +
                "id=" + id +
                ", sent_by='" + sent_by + '\'' +
                ", sent_to=" + sent_to +
                ", noti_type='" + noti_type + '\'' +
                ", is_read=" + is_read +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
