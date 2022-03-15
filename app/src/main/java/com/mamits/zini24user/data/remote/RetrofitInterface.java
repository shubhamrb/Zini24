package com.mamits.zini24user.data.remote;

import com.google.gson.JsonObject;
import com.mamits.zini24user.data.model.form.PlaceOrderRequest;
import com.mamits.zini24user.ui.utils.constants.ApiConstant;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.LOGIN_END_POINT)
    Call<JsonObject> userLogin(@Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SIGNUP_END_POINT)
    Call<JsonObject> userSignUp(@Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.VERIFY_OTP_END_POINT)
    Call<JsonObject> verifyOtp(@Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.VERIFY_FORGOT_OTP_END_POINT)
    Call<JsonObject> verifyForgotOtp(@Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.HOME_DATA_END_POINT)
    Call<JsonObject> fetchHomeData(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.NEARBY_STORES_END_POINT)
    Call<JsonObject> fetchNearbyStores(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.STORES_BY_PRODUCT_END_POINT)
    Call<JsonObject> fetchStoreByProduct(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.FETCH_SUBCATEGORY_END_POINT)
    Call<JsonObject> fetchSubcategory(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.FETCH_PRODUCT_DETAIL_END_POINT)
    Call<JsonObject> fetchProductDetail(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Multipart
    @POST(ApiConstant.UPLOAD_FILE_END_POINT)
    Call<JsonObject> uploadFile(@Header("Authorization") String accessToken, @Part MultipartBody.Part file, @Part("name") RequestBody name);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.PLACE_ORDER_END_POINT)
    Call<JsonObject> placeOrder(@Header("Authorization") String accessToken, @Body PlaceOrderRequest placeOrderRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SEARCH_SERVICES_END_POINT)
    Call<JsonObject> searchServices(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.HELP_END_POINT)
    Call<String> fetchHelp(@Header("Authorization") String accessToken);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.ENQUIRY_END_POINT)
    Call<String> fetchEnquiry(@Header("Authorization") String accessToken);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.STORE_DETAIL_END_POINT)
    Call<JsonObject> fetchStoreDetail(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.ORDER_HISTORY_END_POINT)
    Call<JsonObject> fetchOrderHistory(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.NEW_ORDER_END_POINT)
    Call<JsonObject> fetchNewOrder(@Header("Authorization") String accessToken);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SAVE_RATING_END_POINT)
    Call<JsonObject> saveRating(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.APPLY_COUPON_END_POINT)
    Call<JsonObject> applyCoupon(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.REMOVE_COUPON_END_POINT)
    Call<JsonObject> removeCoupon(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.FETCH_NOTIFICATION_END_POINT)
    Call<JsonObject> fetchNotification(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SERVICES_END_POINT)
    Call<JsonObject> fetchServices(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SERVICES_BY_STORE_END_POINT)
    Call<JsonObject> fetchServicesByStore(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.COUPON_LIST_END_POINT)
    Call<JsonObject> fetchOffers(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.FETCH_MESSAGES_END_POINT)
    Call<JsonObject> fetchMessages(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Multipart
    @POST(ApiConstant.SEND_MESSAGE_END_POINT)
    Call<JsonObject> sendMessages(@Header("Authorization") String accessToken, @Part("orderid") RequestBody orderid, @Part("vendorid") RequestBody userid, @Part("message") RequestBody message, @Part MultipartBody.Part chatfile);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.FETCH_CFSTOKEN_END_POINT)
    Call<JsonObject> fetchCfsToken(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SAVE_PAYMENT_RESPONSE_END_POINT)
    Call<JsonObject> savePaymentResponse(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.FETCH_PAYTM_TOKEN_END_POINT)
    Call<JsonObject> fetchPaytmToken(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.PAYMENT_KEYS_END_POINT)
    Call<JsonObject> fetchPaymentKeys(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.CHANGE_PASSWORD_END_POINT)
    Call<JsonObject> changePassword(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.UPDATE_PROFILE_END_POINT)
    Call<JsonObject> updateProfile(@Header("Authorization") String accessToken, @Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SEND_OTP_END_POINT)
    Call<JsonObject> sendOtp(@Body String jsonObject);

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.UPDATE_PIN_END_POINT)
    Call<JsonObject> updatePin(@Body String jsonObject);
}