package com.mamits.zini24user.data.model.form;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ValueModel implements Serializable {
    @SerializedName("value")
    String value;

    @SerializedName("condition")
    String condition;

    @SerializedName("hidefield")
    List<String> hidefield;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getHidefield() {
        return hidefield;
    }

    public void setHidefield(List<String> hidefield) {
        this.hidefield = hidefield;
    }

    @Override
    public String toString() {
        return "ValueModel{" +
                "value='" + value + '\'' +
                ", condition='" + condition + '\'' +
                ", hidefield=" + hidefield +
                '}';
    }
}
