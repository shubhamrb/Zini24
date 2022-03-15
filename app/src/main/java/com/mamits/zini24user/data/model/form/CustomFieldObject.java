package com.mamits.zini24user.data.model.form;

import java.util.List;

public class CustomFieldObject {
    private String id, name, fieldType, label, min, max, isRequired, ansValue,ext;
    private List<ValueModel> value;
    private List<String> visibilityControlField;
    private boolean isVisible=true;

    public CustomFieldObject(String id, String name, String fieldType, String label, String min, String max, String isRequired, String ansValue, List<ValueModel> value, List<String> visibilityControlField) {
        this.id = id;
        this.name = name;
        this.fieldType = fieldType;
        this.label = label;
        this.min = min;
        this.max = max;
        this.isRequired = isRequired;
        this.ansValue = ansValue;
        this.value = value;
        this.visibilityControlField = visibilityControlField;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getAnsValue() {
        return ansValue;
    }

    public void setAnsValue(String ansValue) {
        this.ansValue = ansValue;
    }

    public List<ValueModel> getValue() {
        return value;
    }

    public void setValue(List<ValueModel> value) {
        this.value = value;
    }

    public List<String> getVisibilityControlField() {
        return visibilityControlField;
    }

    public void setVisibilityControlField(List<String> visibilityControlField) {
        this.visibilityControlField = visibilityControlField;
    }

    @Override
    public String toString() {
        return "CustomFieldObject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", label='" + label + '\'' +
                ", min='" + min + '\'' +
                ", max='" + max + '\'' +
                ", isRequired='" + isRequired + '\'' +
                ", ansValue='" + ansValue + '\'' +
                ", ext='" + ext + '\'' +
                ", value=" + value +
                ", visibilityControlField=" + visibilityControlField +
                ", isVisible=" + isVisible +
                '}';
    }
}
