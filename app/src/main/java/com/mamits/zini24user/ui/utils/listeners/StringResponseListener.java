package com.mamits.zini24user.ui.utils.listeners;


public interface StringResponseListener {

    void onSuccess(String response);

    void onFailed(Throwable t);
}
