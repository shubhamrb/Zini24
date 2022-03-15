package com.mamits.zini24user.ui.navigator.fragment;


import com.mamits.zini24user.ui.navigator.base.BaseNavigator;

public interface PaymentDetailNavigator extends BaseNavigator {


    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

}
