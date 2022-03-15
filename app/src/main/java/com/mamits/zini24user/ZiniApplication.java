package com.mamits.zini24user;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;
import com.mamits.zini24user.di.component.DaggerAppComponent;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import okhttp3.OkHttpClient;

public class ZiniApplication extends DaggerApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.getDefault());
        }
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        DaggerAppComponent mAppComponent = (DaggerAppComponent) DaggerAppComponent.builder().create(this);
        mAppComponent.inject(this);
        return mAppComponent;
    }

}
