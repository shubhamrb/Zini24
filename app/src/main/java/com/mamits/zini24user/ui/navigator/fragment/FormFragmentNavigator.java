package com.mamits.zini24user.ui.navigator.fragment;


import com.google.gson.JsonObject;
import com.mamits.zini24user.ui.navigator.base.BaseNavigator;

public interface FormFragmentNavigator extends BaseNavigator {


    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessProductDetail(JsonObject jsonObject);

    void onSuccessFileUpload(JsonObject jsonObject);

    void onSuccessPlaceOrder(JsonObject jsonObject);
}
