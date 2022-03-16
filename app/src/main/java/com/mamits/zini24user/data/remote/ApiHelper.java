package com.mamits.zini24user.data.remote;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.mamits.zini24user.data.model.form.PlaceOrderRequest;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.ui.utils.listeners.ResponseListener;
import com.mamits.zini24user.ui.utils.listeners.StringResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ApiHelper implements IApiHelper {

    @Inject
    public ApiHelper() {
    }

    @Override
    public void userLogin(Activity mActivity, String jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.userLogin(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });


    }

    @Override
    public void userSignUp(Activity mActivity, String jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.userSignUp(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });


    }

    @Override
    public void verifyOtp(Activity mActivity, String jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.verifyOtp(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });


    }


    @Override
    public void verifyOtp(Activity mActivity, String number, String otp, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.verifyForgotOtp(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }


    @Override
    public void fetchBanner(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchHomeData("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchNearbyStores(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchNearbyStores("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchStoreByProduct(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchStoreByProduct("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchSubcategory(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchSubcategory("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchProductDetail(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchProductDetail("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void uploadFile(Activity activity, String accessToken, MultipartBody.Part multipartBody, RequestBody requestDocs, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.uploadFile("Bearer " + accessToken, multipartBody, requestDocs).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void placeOrder(Activity activity, String accessToken, PlaceOrderRequest placeOrderRequest, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.placeOrder("Bearer " + accessToken, placeOrderRequest).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void searchServices(Activity activity, String accessToken, String value, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search", value);
            jsonObject.put("api_key", AppConstant.API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.searchServices("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchHelp(Activity activity, String accessToken, StringResponseListener stringResponseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchHelp("Bearer " + accessToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.body() != null) {
                    stringResponseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                stringResponseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchEnquiry(Activity activity, String accessToken, StringResponseListener stringResponseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchEnquiry("Bearer " + accessToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.body() != null) {
                    stringResponseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                stringResponseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchStoreDetail(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchStoreDetail("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchOrderHistory(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchOrderHistory("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchNewOrder(Activity activity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchNewOrder("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void saveRating(Activity activity, String accessToken, int order_id, int store_id, float rating, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("orderid", String.valueOf(order_id));
            jsonObject.put("storeid", String.valueOf(store_id));
            jsonObject.put("rating", String.valueOf(rating));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.saveRating("Bearer " + accessToken,jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void applyCoupon(Activity activity, String accessToken, String coupon, int order_id, String order_amount, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("orderid", String.valueOf(order_id));
            jsonObject.put("coupon", coupon);
            jsonObject.put("orderamount",order_amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.applyCoupon("Bearer " + accessToken,jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void removeCoupon(Activity activity, String accessToken, int order_id, String mCouponAmt, String mPayableAmt, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("orderid", String.valueOf(order_id));
            jsonObject.put("discountamount", mCouponAmt);
            jsonObject.put("finalamountpay",mPayableAmt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.removeCoupon("Bearer " + accessToken,jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchNotification(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchNotification("Bearer " + accessToken,jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchOffers(Activity activity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchOffers("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchServices(Activity activity, String accessToken, int cat_id, int sub_cat_id, ResponseListener responseListener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("category_id", String.valueOf(cat_id));
            jsonObject.put("subcategory_id", String.valueOf(sub_cat_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchServices("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchServicesByStore(Activity activity, String accessToken, int cat_id, int store_id, ResponseListener responseListener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("category", String.valueOf(cat_id));
            jsonObject.put("store_id", String.valueOf(store_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitInterface call = new RetrofitBase(activity, true).retrofit.create(RetrofitInterface.class);

        call.fetchServicesByStore("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchMessage(Activity mActivity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchMessages("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void sendMessage(Activity mActivity, String accessToken, int user_id, int order_id, String message, File uploadedFile, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        try {
            MultipartBody.Part requestImage = null;
            if (uploadedFile != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), uploadedFile);
                requestImage = MultipartBody.Part.createFormData("chatfile", uploadedFile.getName(), requestFile);
            }


            RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(order_id));
            RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
            RequestBody msg = RequestBody.create(MediaType.parse("multipart/form-data"), message);

            call.sendMessages("Bearer " + accessToken, vendorid, userid, msg, requestImage).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    if (response.body() != null) {
                        responseListener.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    responseListener.onFailed(t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchCfsToken(Activity mActivity, String accessToken, String orderId, String amount, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("orderAmount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchCfsToken("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchPaytmToken(Activity mActivity, String accessToken, String orderId, String amount, String customerPhone, String customerEmail, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("orderAmount", amount);
            jsonObject.put("mobile", customerPhone);
            jsonObject.put("email", customerEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchPaytmToken("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void savePaymentResponse(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.savePaymentResponse("Bearer " + accessToken,object.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchPaymentKeys(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchPaymentKeys("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void changePassword(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.changePassword("Bearer " + accessToken, object.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void updateProfile(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.updateProfile("Bearer " + accessToken, object.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void sendOtp(Activity mActivity, String number, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.sendOtp(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void updatePin(Activity mActivity, String number, String newPin, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
            jsonObject.put("password", newPin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.updatePin(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

}
