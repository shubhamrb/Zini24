package com.mamits.zini24user.data.model.search;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchDataModel implements Serializable {
    @SerializedName("name")
    private String name = "";

    @SerializedName("type")
    private String type;

    @SerializedName("label")
    private String label;

    @SerializedName("value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SearchDataModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
