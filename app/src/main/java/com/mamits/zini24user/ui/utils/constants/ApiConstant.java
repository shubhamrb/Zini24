package com.mamits.zini24user.ui.utils.constants;


import com.mamits.zini24user.BuildConfig;

public class ApiConstant {
    public static final String LOGIN_END_POINT = BuildConfig.BASE_URL + "api/signin";
    public static final String SIGNUP_END_POINT = BuildConfig.BASE_URL + "api/signup";
    public static final String VERIFY_OTP_END_POINT = BuildConfig.BASE_URL + "api/verifyMobileOtp";
    public static final String VERIFY_FORGOT_OTP_END_POINT = BuildConfig.BASE_URL + "api/forgotpassword";
    public static final String HOME_DATA_END_POINT = BuildConfig.BASE_URL + "api/getHomeData";
    public static final String NEARBY_STORES_END_POINT = BuildConfig.BASE_URL + "api/getStoreAroundYou";
    public static final String STORES_BY_PRODUCT_END_POINT = BuildConfig.BASE_URL + "api/getStoreByProduct";
    public static final String ORDER_HISTORY_END_POINT = BuildConfig.BASE_URL + "api/auth/getOrderHistory";
    public static final String NEW_ORDER_END_POINT = BuildConfig.BASE_URL + "api/auth/getLatestOrder";
    public static final String SERVICES_END_POINT = BuildConfig.BASE_URL + "api/getProductByCategory";
    public static final String SERVICES_BY_STORE_END_POINT = BuildConfig.BASE_URL + "api/getProductByStore";
    public static final String STORE_DETAIL_END_POINT = BuildConfig.BASE_URL + "api/getStoreDetail";
    public static final String COUPON_LIST_END_POINT = BuildConfig.BASE_URL + "api/auth/couponList";
    public static final String FETCH_MESSAGES_END_POINT = BuildConfig.BASE_URL + "api/auth/getChatList";
    public static final String SEND_MESSAGE_END_POINT = BuildConfig.BASE_URL + "api/auth/saveChat";
    public static final String FETCH_NOTIFICATION_END_POINT = BuildConfig.BASE_URL + "api/auth/getNotificationList";
    public static final String FETCH_PRODUCT_DETAIL_END_POINT = BuildConfig.BASE_URL + "api/getProductDetail";
    public static final String UPLOAD_FILE_END_POINT = BuildConfig.BASE_URL + "api/auth/uploadFile";
    public static final String PLACE_ORDER_END_POINT = BuildConfig.BASE_URL + "api/auth/placeOrder";
    public static final String SEARCH_SERVICES_END_POINT = BuildConfig.BASE_URL + "api/search";
    public static final String FETCH_SUBCATEGORY_END_POINT = BuildConfig.BASE_URL + "api/getSubCategories";
    public static final String HELP_END_POINT = BuildConfig.BASE_URL + "api/auth/gethelpsupport";
    public static final String ENQUIRY_END_POINT = BuildConfig.BASE_URL + "api/auth/getenquirylist";
    public static final String SAVE_RATING_END_POINT = BuildConfig.BASE_URL + "api/auth/saveRatting";
    public static final String APPLY_COUPON_END_POINT = BuildConfig.BASE_URL + "api/auth/applycoupon";
    public static final String REMOVE_COUPON_END_POINT = BuildConfig.BASE_URL + "api/auth/removecoupon";
    public static final String FETCH_CFSTOKEN_END_POINT = BuildConfig.BASE_URL + "api/auth/cashfreetoken";
    public static final String SAVE_PAYMENT_RESPONSE_END_POINT = BuildConfig.BASE_URL + "api/auth/paymentdone";
    public static final String FETCH_PAYTM_TOKEN_END_POINT = BuildConfig.BASE_URL + "api/auth/paytmtoken";
    public static final String PAYMENT_KEYS_END_POINT = BuildConfig.BASE_URL + "api/getSetting";
    public static final String CHANGE_PASSWORD_END_POINT = BuildConfig.BASE_URL + "api/auth/changePassword";
    public static final String UPDATE_PROFILE_END_POINT = BuildConfig.BASE_URL + "api/auth/update_profile";
    public static final String SEND_OTP_END_POINT = BuildConfig.BASE_URL + "api/sendOtp";
    public static final String UPDATE_PIN_END_POINT = BuildConfig.BASE_URL + "api/updatepassword";
}
