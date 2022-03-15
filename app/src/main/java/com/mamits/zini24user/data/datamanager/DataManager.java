package com.mamits.zini24user.data.datamanager;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.mamits.zini24user.data.local.pref.IPreferenceHelper;
import com.mamits.zini24user.data.local.pref.PreferenceHelper;
import com.mamits.zini24user.data.model.form.PlaceOrderRequest;
import com.mamits.zini24user.data.remote.ApiHelper;
import com.mamits.zini24user.data.remote.IApiHelper;
import com.mamits.zini24user.ui.utils.listeners.ResponseListener;
import com.mamits.zini24user.ui.utils.listeners.StringResponseListener;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@Singleton
public class DataManager implements IDataManager {
    private static final String TAG = "DataManager";

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final Gson mGson;

    PreferenceHelper mPreferenceHelper;

    @Inject
    public DataManager(Context context, IApiHelper apiHelper, IPreferenceHelper preferenceHelper, Gson gson) {
        this.mContext = context;
        this.mApiHelper = (ApiHelper) apiHelper;
        this.mPreferenceHelper = (PreferenceHelper) preferenceHelper;
        this.mGson = gson;
    }

    @Override
    public void userLogin(Activity activity, String jsonObject, ResponseListener responseListener) {
        mApiHelper.userLogin(activity, jsonObject, responseListener);
    }

    @Override
    public void userSignUp(Activity activity, String jsonObject, ResponseListener responseListener) {
        mApiHelper.userSignUp(activity, jsonObject, responseListener);
    }

    @Override
    public void verifyOtp(Activity activity, String jsonObject, ResponseListener responseListener) {
        mApiHelper.verifyOtp(activity, jsonObject, responseListener);
    }

    @Override
    public void verifyOtp(Activity activity, String mobile, String otp, ResponseListener responseListener) {
        mApiHelper.verifyOtp(activity, mobile,otp, responseListener);

    }

    @Override
    public void fetchBanner(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchBanner(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchNearbyStores(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchNearbyStores(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchStoreByProduct(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchStoreByProduct(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchSubcategory(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchSubcategory(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchProductDetail(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchProductDetail(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void uploadFile(Activity activity, String accessToken, MultipartBody.Part multipartBody, RequestBody requestDocs, ResponseListener responseListener) {
        mApiHelper.uploadFile(activity, accessToken, multipartBody, requestDocs, responseListener);
    }

    @Override
    public void placeOrder(Activity activity, String accessToken, PlaceOrderRequest placeOrderRequest, ResponseListener responseListener) {
        mApiHelper.placeOrder(activity, accessToken, placeOrderRequest, responseListener);
    }

    @Override
    public void searchServices(Activity activity, String accessToken, String value, ResponseListener responseListener) {
        mApiHelper.searchServices(activity, accessToken, value, responseListener);
    }

    @Override
    public void fetchHelp(Activity activity, String accessToken, StringResponseListener responseListener) {
        mApiHelper.fetchHelp(activity, accessToken, responseListener);
    }

    @Override
    public void fetchEnquiry(Activity activity, String accessToken, StringResponseListener responseListener) {
        mApiHelper.fetchEnquiry(activity, accessToken, responseListener);
    }

    @Override
    public void fetchStoreDetail(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchStoreDetail(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchOrderHistory(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchOrderHistory(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchNewOrder(Activity activity, String accessToken, ResponseListener responseListener) {
        mApiHelper.fetchNewOrder(activity, accessToken,  responseListener);
    }

    @Override
    public void saveRating(Activity activity, String accessToken, int order_id, int store_id, float rating, ResponseListener responseListener) {
        mApiHelper.saveRating(activity, accessToken, order_id, store_id, rating, responseListener);
    }

    @Override
    public void applyCoupon(Activity activity, String accessToken, String coupon, int order_id, String order_amount, ResponseListener responseListener) {
        mApiHelper.applyCoupon(activity, accessToken, coupon, order_id, order_amount, responseListener);

    }

    @Override
    public void removeCoupon(Activity activity, String accessToken, int order_id, String mCouponAmt, String mPayableAmt, ResponseListener responseListener) {
        mApiHelper.removeCoupon(activity, accessToken, order_id, mCouponAmt, mPayableAmt, responseListener);

    }

    @Override
    public void fetchNotification(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchNotification(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchOffers(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchOffers(activity, accessToken, jsonObject, responseListener);
    }

    @Override
    public void fetchServices(Activity activity, String accessToken, int cat_id, int sub_cat_id, ResponseListener responseListener) {
        mApiHelper.fetchServices(activity, accessToken, cat_id, sub_cat_id, responseListener);
    }

    @Override
    public void fetchServicesByStore(Activity activity, String accessToken, int cat_id, int store_id, ResponseListener responseListener) {
        mApiHelper.fetchServicesByStore(activity, accessToken, cat_id, store_id, responseListener);
    }

    @Override
    public void fetchMessage(Activity mActivity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        mApiHelper.fetchMessage(mActivity, accessToken,  jsonObject, responseListener);
    }

    @Override
    public void sendMessage(Activity mActivity, String accessToken, int user_id, int order_id, String message, File uploadedFile, ResponseListener responseListener) {
        mApiHelper.sendMessage(mActivity, accessToken, user_id, order_id, message, uploadedFile, responseListener);
    }

    @Override
    public void fetchCfsToken(Activity mActivity, String accessToken, String orderId, String amount, ResponseListener responseListener) {
        mApiHelper.fetchCfsToken(mActivity, accessToken, orderId, amount, responseListener);
    }

    @Override
    public void fetchPaytmToken(Activity mActivity, String accessToken, String orderId, String amount, String customerPhone, String customerEmail, ResponseListener responseListener) {
        mApiHelper.fetchPaytmToken(mActivity, accessToken, orderId, amount, customerPhone, customerEmail, responseListener);

    }

    @Override
    public void savePaymentResponse(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        mApiHelper.savePaymentResponse(mActivity, accessToken, object, responseListener);

    }

    @Override
    public void fetchPaymentKeys(Activity mActivity, String accessToken, ResponseListener responseListener) {
        mApiHelper.fetchPaymentKeys(mActivity, accessToken, responseListener);
    }

    @Override
    public void changePassword(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        mApiHelper.changePassword(mActivity, accessToken, object, responseListener);
    }

    @Override
    public void updateProfile(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        mApiHelper.updateProfile(mActivity, accessToken, object, responseListener);
    }

    @Override
    public void sendOtp(Activity mActivity, String number, ResponseListener responseListener) {
        mApiHelper.sendOtp(mActivity, number, responseListener);
    }

    @Override
    public void updatePin(Activity mActivity, String number, String newPin, ResponseListener responseListener) {
        mApiHelper.updatePin(mActivity, number, newPin, responseListener);
    }

    @Override
    public String getAccessToken() {
        return mPreferenceHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferenceHelper.setAccessToken(accessToken);
    }


    @Override
    public void setCurrentUserId(int userId) {
        mPreferenceHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserId() {
        return mPreferenceHelper.getCurrentUserId();
    }

    @Override
    public String getUsername() {
        return mPreferenceHelper.getUsername();
    }

    @Override
    public void setUsername(String username) {
        mPreferenceHelper.setUsername(username);
    }

    @Override
    public String getUserNumber() {
        return mPreferenceHelper.getUserNumber();
    }

    @Override
    public void settUserNumber(String number) {
        mPreferenceHelper.settUserNumber(number);
    }

    @Override
    public String getUserEmail() {
        return mPreferenceHelper.getUserEmail();
    }

    @Override
    public void settUserEmail(String email) {
        mPreferenceHelper.settUserEmail(email);
    }

    @Override
    public double getLatitude() {
        return mPreferenceHelper.getLatitude();
    }

    @Override
    public void setLatitude(double latitude) {
        mPreferenceHelper.setLatitude(latitude);
    }

    @Override
    public double getLongitude() {
        return mPreferenceHelper.getLongitude();
    }

    @Override
    public void setLongitude(double longitude) {
        mPreferenceHelper.setLongitude(longitude);
    }

    @Override
    public boolean isPaymentOpen() {
        return mPreferenceHelper.isPaymentOpen();
    }

    @Override
    public void setPaymentOpen(boolean isOpen) {
        mPreferenceHelper.setPaymentOpen(isOpen);
    }

    @Override
    public void clearAllPreference() {
        mPreferenceHelper.clearAllPreference();
    }
}
