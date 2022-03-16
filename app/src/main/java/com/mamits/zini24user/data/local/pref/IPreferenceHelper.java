package com.mamits.zini24user.data.local.pref;


public interface IPreferenceHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    void setCurrentUserId(int userId);

    int getCurrentUserId();

    String getUsername();

    void setUsername(String username);

    String getUserNumber();

    void settUserNumber(String number);

    String getUserEmail();

    void settUserEmail(String email);

    double getLatitude();

    void setLatitude(double latitude);

    double getLongitude();

    void setLongitude(double longitude);

    boolean isPaymentOpen();

    void setPaymentOpen(boolean isOpen);

    void clearAllPreference();

    String getNotificationType();

    void setNotificationType(String type);
}
