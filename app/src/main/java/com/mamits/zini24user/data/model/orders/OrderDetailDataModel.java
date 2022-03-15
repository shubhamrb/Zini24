package com.mamits.zini24user.data.model.orders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetailDataModel implements Serializable {

    @SerializedName("name")
    String name;

    @SerializedName("value")
    String value;

    @SerializedName("filedata")
    FileDataModel filedata;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FileDataModel getFiledata() {
        return filedata;
    }

    public void setFiledata(FileDataModel filedata) {
        this.filedata = filedata;
    }

    @Override
    public String toString() {
        return "OrderDetailDataModel{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", filedata=" + filedata +
                '}';
    }
}
