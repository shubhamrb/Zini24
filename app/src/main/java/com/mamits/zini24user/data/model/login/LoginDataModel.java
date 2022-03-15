package com.mamits.zini24user.data.model.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginDataModel implements Serializable {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private UserDataModel user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDataModel getUser() {
        return user;
    }

    public void setUser(UserDataModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginDataModel{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
