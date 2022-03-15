package com.mamits.zini24user.ui.navigator.activity;


import com.google.gson.JsonObject;
import com.mamits.zini24user.ui.navigator.base.BaseNavigator;

public interface MessageNavigator extends BaseNavigator {


    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessMessages(JsonObject jsonObject);
    void onSuccessMessageSend(JsonObject jsonObject);
}
