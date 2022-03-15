package com.mamits.zini24user.viewmodel.activity;

import android.app.Activity;

import com.androidnetworking.error.ANError;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.navigator.activity.WebViewActivityNavigator;
import com.mamits.zini24user.ui.utils.commonClasses.NetworkUtils;
import com.mamits.zini24user.ui.utils.listeners.StringResponseListener;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.base.BaseViewModel;

import org.json.JSONObject;


public class WebViewActivityViewModel extends BaseViewModel<WebViewActivityNavigator> {


    public WebViewActivityViewModel(IDataManager mDataManager, ISchedulerProvider mSchedulerProvider) {
        super(mDataManager, mSchedulerProvider);
    }

    public void fetchHelp(Activity mActivity) {
        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmDataManger().fetchHelp(mActivity, getmDataManger().getAccessToken(), new StringResponseListener() {
                @Override
                public void onSuccess(String response) {
                    try {
                        getmNavigator().get().onSuccessHelp(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
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

    public void fetchEnquiry(Activity mActivity) {
        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmDataManger().fetchEnquiry(mActivity, getmDataManger().getAccessToken(), new StringResponseListener() {
                @Override
                public void onSuccess(String response) {
                    try {
                        getmNavigator().get().onSuccessHelp(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
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
