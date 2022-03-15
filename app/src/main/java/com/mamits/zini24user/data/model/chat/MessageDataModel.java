package com.mamits.zini24user.data.model.chat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageDataModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("order_id")
    int order_id;

    @SerializedName("from_user")
    int from_user;

    @SerializedName("to_user")
    int to_user;

    @SerializedName("message")
    String message;

    @SerializedName("attachment")
    String attachment;

    @SerializedName("file_type")
    String file_type;

    @SerializedName("created_at")
    String  created_at;

    @SerializedName("updated_at")
    String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getFrom_user() {
        return from_user;
    }

    public void setFrom_user(int from_user) {
        this.from_user = from_user;
    }

    public int getTo_user() {
        return to_user;
    }

    public void setTo_user(int to_user) {
        this.to_user = to_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
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
        return "MessageDataModel{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", from_user=" + from_user +
                ", to_user=" + to_user +
                ", message='" + message + '\'' +
                ", attachment='" + attachment + '\'' +
                ", file_type='" + file_type + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
