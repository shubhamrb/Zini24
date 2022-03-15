package com.mamits.zini24user.ui.navigator.activity;

import com.mamits.zini24user.ui.navigator.base.BaseNavigator;

public interface WebViewActivityNavigator extends BaseNavigator {
    void showLoader();

    void hideLoader();


    void checkValidation(int type, String message);

    void throwable(Throwable it);

    void checkInternetConnection(String message);

    void onSuccessHelp(String response);
}
