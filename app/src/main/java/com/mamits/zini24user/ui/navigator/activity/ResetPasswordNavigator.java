package com.mamits.zini24user.ui.navigator.activity;

import com.google.gson.JsonObject;
import com.mamits.zini24user.ui.navigator.base.BaseNavigator;

public interface ResetPasswordNavigator extends BaseNavigator {
    void showLoader();

    void hideLoader();


    void checkValidation(int type, String message);

    void throwable(Throwable it);

    void checkInternetConnection(String message);

    void onSuccessPasswordUpdated(JsonObject jsonObject, String number);
}
