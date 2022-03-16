package com.mamits.zini24user.data.remote;

import android.app.Activity;

import com.mamits.zini24user.data.model.form.PlaceOrderRequest;
import com.mamits.zini24user.ui.utils.listeners.ResponseListener;
import com.mamits.zini24user.ui.utils.listeners.StringResponseListener;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IApiHelper {

    void userLogin(Activity activity, String jsonObject, ResponseListener responseListener);

    void userSignUp(Activity activity, String jsonObject, ResponseListener responseListener);

    void verifyOtp(Activity activity, String jsonObject, ResponseListener responseListener);

    void verifyOtp(Activity activity, String mobile, String otp, ResponseListener responseListener);

    void fetchBanner(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchNearbyStores(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchStoreByProduct(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchSubcategory(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchProductDetail(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void uploadFile(Activity activity, String accessToken, MultipartBody.Part multipartBody, RequestBody requestDocs, ResponseListener responseListener);

    void placeOrder(Activity activity, String accessToken, PlaceOrderRequest placeOrderRequest, ResponseListener responseListener);

    void searchServices(Activity activity, String accessToken, String value, ResponseListener responseListener);

    void fetchHelp(Activity activity, String accessToken, StringResponseListener responseListener);

    void fetchEnquiry(Activity activity, String accessToken, StringResponseListener responseListener);

    void fetchStoreDetail(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchOrderHistory(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchNewOrder(Activity activity, String accessToken, ResponseListener responseListener);

    void saveRating(Activity activity, String accessToken, int order_id, int store_id, float rating, ResponseListener responseListener);

    void applyCoupon(Activity activity, String accessToken, String coupon, int order_id, String order_amount, ResponseListener responseListener);

    void removeCoupon(Activity activity, String token, int order_id, String mCouponAmt, String mPayableAmt, ResponseListener responseListener);

    void fetchNotification(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchOffers(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void fetchServices(Activity activity, String accessToken, int cat_id, int sub_cat_id, ResponseListener responseListener);

    void fetchServicesByStore(Activity activity, String accessToken, int cat_id, int store_id, ResponseListener responseListener);

    void fetchMessage(Activity mActivity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void sendMessage(Activity mActivity, String accessToken, int user_id, int order_id, String message, File uploadedFile, ResponseListener responseListener);

    void fetchCfsToken(Activity mActivity, String accessToken, String orderId, String amount, ResponseListener responseListener);

    void fetchPaytmToken(Activity mActivity, String accessToken, String orderId, String amount, String customerPhone, String customerEmail, ResponseListener responseListener);

    void savePaymentResponse(Activity mActivity, String accessToken, JSONObject object, ResponseListener message);

    void fetchPaymentKeys(Activity mActivity, String accessToken, ResponseListener message);

    void changePassword(Activity mActivity, String accessToken, JSONObject object, ResponseListener message);

    void updateProfile(Activity mActivity, String accessToken, JSONObject object, ResponseListener message);

    void sendOtp(Activity mActivity, String number, ResponseListener message);

    void updatePin(Activity mActivity, String number, String newPin, ResponseListener message);

}