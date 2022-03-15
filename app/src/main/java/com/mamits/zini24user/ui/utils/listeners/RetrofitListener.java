package com.mamits.zini24user.ui.utils.listeners;


import com.mamits.zini24user.data.model.ErrorObject;

public interface RetrofitListener {
    void onResponseSuccess(String responseBodyString, int apiFlag);

    void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag);
}
