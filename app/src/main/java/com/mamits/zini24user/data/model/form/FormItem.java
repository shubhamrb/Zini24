package com.mamits.zini24user.data.model.form;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FormItem implements Serializable {
    @SerializedName("label")
    String label;

    @SerializedName("name")
    String name;

    @SerializedName("id")
    String id;

    @SerializedName("field_type")
    String field_type;

    @SerializedName("value")
    List<ValueModel> value;

    @SerializedName("isRequired")
    String isRequired;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public List<ValueModel> getValue() {
        return value;
    }

    public void setValue(List<ValueModel> value) {
        this.value = value;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public String toString() {
        return "FormItem{" +
                "label='" + label + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", field_type='" + field_type + '\'' +
                ", value=" + value +
                ", isRequired='" + isRequired + '\'' +
                '}';
    }
}
