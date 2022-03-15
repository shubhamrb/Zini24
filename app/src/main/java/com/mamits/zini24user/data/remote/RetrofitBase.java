package com.mamits.zini24user.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamits.zini24user.BuildConfig;
import com.mamits.zini24user.data.model.ErrorObject;
import com.mamits.zini24user.ui.utils.commonClasses.CommonUtils;
import com.mamits.zini24user.ui.utils.commonClasses.HttpUtil;
import com.mamits.zini24user.ui.utils.listeners.RetrofitListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBase {
    public Retrofit retrofit;
    protected Context context;

    public RetrofitBase(Context context, boolean addTimeout) {
        this.context = context;


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder().addInterceptor(interceptor);
        if (addTimeout) {
            httpClientBuilder.readTimeout(CommonUtils.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS);
            httpClientBuilder.connectTimeout(CommonUtils.TimeOut.CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        } else {
            httpClientBuilder.readTimeout(CommonUtils.TimeOut.IMAGE_UPLOAD_SOCKET_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.connectTimeout(CommonUtils.TimeOut.IMAGE_UPLOAD_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        }

        OkHttpClient httpClient = httpClientBuilder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }



    void validateResponse(Response response, RetrofitListener retrofitListener, int apiFlag) {
        if (response.code() == 200) {
            ResponseBody responseBody = (ResponseBody) response.body();
            try {
                retrofitListener.onResponseSuccess(responseBody.string(), apiFlag);
            } catch (IOException e) {
                error(response, retrofitListener, apiFlag);
            }
        } else {
            error(response, retrofitListener, apiFlag);
        }
    }

    private void error(Response response, RetrofitListener retrofitListener, int apiFlag) {
        Gson gson = new Gson();
        ErrorObject errorPojo;
        try {
            errorPojo = gson.fromJson((response.errorBody()).string(), ErrorObject.class);
            if (errorPojo == null) {
                errorPojo = HttpUtil.getServerErrorPojo(context);
            }
            retrofitListener.onResponseError(errorPojo, null, apiFlag);
        } catch (Exception e) {
            retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), null, apiFlag);
        }
    }
}
