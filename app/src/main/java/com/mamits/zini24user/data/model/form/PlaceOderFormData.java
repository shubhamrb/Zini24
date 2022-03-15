package com.mamits.zini24user.data.model.form;

public class PlaceOderFormData {
    private String name, field_type, isRequired, ansValue, ext;

    public PlaceOderFormData(String name, String fieldType, String isRequired, String ansValue, String ext) {
        this.name = name;
        this.field_type = fieldType;
        this.isRequired = isRequired;
        this.ansValue = ansValue;
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return field_type;
    }

    public void setFieldType(String fieldType) {
        this.field_type = fieldType;
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

    @Override
    public String toString() {
        return "PlaceOderFormData{" +
                "name='" + name + '\'' +
                ", field_type='" + field_type + '\'' +
                ", isRequired='" + isRequired + '\'' +
                ", ansValue='" + ansValue + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
