package com.mamits.zini24user.data.local.pref;

import android.content.SharedPreferences;

import com.mamits.zini24user.ui.utils.constants.AppConstant;

import javax.inject.Inject;


public class PreferenceHelper implements IPreferenceHelper {

    private final SharedPreferences mSharedPreferences;

    @Inject
    public PreferenceHelper(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    @Override
    public void setAccessToken(String accessToken) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getAccessToken() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setCurrentUserId(int userId) {
        mSharedPreferences.edit().putInt(AppConstant.PREF_KEY_USER_ID, userId).apply();
    }

    @Override
    public int getCurrentUserId() {
        return mSharedPreferences.getInt(AppConstant.PREF_KEY_USER_ID, -1);
    }

    @Override
    public String getUsername() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setUsername(String username) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_NAME, username).apply();
    }

    @Override
    public String getUserNumber() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_NUMBER, null);
    }

    @Override
    public void settUserNumber(String number) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_NUMBER, number).apply();
    }

    @Override
    public String getUserEmail() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_EMAIL, null);
    }

    @Override
    public void settUserEmail(String email) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_EMAIL, email).apply();
    }

    @Override
    public double getLatitude() {
        return Double.longBitsToDouble(mSharedPreferences.getLong(AppConstant.PREF_LATITUDE, 0));
    }

    @Override
    public void setLatitude(double latitude) {
        mSharedPreferences.edit().putLong(AppConstant.PREF_LATITUDE, Double.doubleToLongBits(latitude)).apply();
    }

    @Override
    public double getLongitude() {
        return Double.longBitsToDouble(mSharedPreferences.getLong(AppConstant.PREF_LONGITUDE, 0));
    }

    @Override
    public void setLongitude(double longitude) {
        mSharedPreferences.edit().putLong(AppConstant.PREF_LONGITUDE, Double.doubleToLongBits(longitude)).apply();
    }

    @Override
    public boolean isPaymentOpen() {
        return mSharedPreferences.getBoolean(AppConstant.PREF_PAYMENT_ACTIVITY_OPEN, false);
    }

    @Override
    public void setPaymentOpen(boolean isOpen) {
        mSharedPreferences.edit().putBoolean(AppConstant.PREF_PAYMENT_ACTIVITY_OPEN, isOpen).apply();
    }

    @Override
    public void clearAllPreference() {
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public String getNotificationType() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_NOTIFICATION_TYPE, null);
    }

    @Override
    public void setNotificationType(String type) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_NOTIFICATION_TYPE, type).apply();
    }
}
