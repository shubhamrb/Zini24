package com.mamits.zini24user.data.model.orders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileDataModel implements Serializable {

    @SerializedName("type")
    String type;

    @SerializedName("url")
    String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileDataModel{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
