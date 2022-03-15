package com.mamits.zini24user.ui.navigator.fragment;


import com.google.gson.JsonObject;
import com.mamits.zini24user.ui.navigator.base.BaseNavigator;

public interface PaymentSummaryFragmentNavigator extends BaseNavigator {


    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessCouponList(JsonObject jsonObject);

    void onSuccessApplyCoupon(JsonObject jsonObject);

    void onSuccessRemoveCoupon(JsonObject jsonObject);

    void onSuccessPaymentKeys(JsonObject jsonObject);
}
