package com.mamits.zini24user.viewmodel.activity;

import android.app.Activity;

import com.androidnetworking.error.ANError;
import com.google.gson.JsonObject;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.navigator.activity.LoginActivityNavigator;
import com.mamits.zini24user.ui.utils.commonClasses.NetworkUtils;
import com.mamits.zini24user.ui.utils.listeners.ResponseListener;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.base.BaseViewModel;

import org.json.JSONObject;


public class LoginActivityViewModel extends BaseViewModel<LoginActivityNavigator> {


    public LoginActivityViewModel(IDataManager mDataManager, ISchedulerProvider mSchedulerProvider) {
        super(mDataManager, mSchedulerProvider);
    }

    public void userLogin(Activity mActivity, String jsonObject) {
        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmNavigator().get().showLoader();
            getmDataManger().userLogin(mActivity, jsonObject, new ResponseListener() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    try {
                        getmNavigator().get().hideLoader();
                        getmNavigator().get().onSuccessUserLogin(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
                        getmNavigator().get().hideLoader();
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            if (anError.getErrorBody() != null) {
                                JSONObject object = new JSONObject(anError.getErrorBody());
                                try {
                                    getmNavigator().get().checkValidation(anError.getErrorCode(), object.optString("message"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            throwable.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            getmNavigator().get().checkInternetConnection(mActivity.getResources().getString(R.string.check_internet_connection));

        }
    }


}
