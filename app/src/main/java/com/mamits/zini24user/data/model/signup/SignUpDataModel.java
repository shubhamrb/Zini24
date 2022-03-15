package com.mamits.zini24user.data.model.signup;

import com.google.gson.annotations.SerializedName;
import com.mamits.zini24user.data.model.login.UserDataModel;

import java.io.Serializable;

public class SignUpDataModel implements Serializable {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("otp")
    private String otp;

    @SerializedName("phone_number")
    private String phone_number;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "SignUpDataModel{" +
                "user_id='" + user_id + '\'' +
                ", otp='" + otp + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
