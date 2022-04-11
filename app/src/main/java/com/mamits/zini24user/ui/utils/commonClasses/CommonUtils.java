package com.mamits.zini24user.ui.utils.commonClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;

import com.basusingh.beautifulprogressdialog.BeautifulProgressDialog;
import com.mamits.zini24user.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class CommonUtils {
    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public interface ErrorClass {
        String CODE = "code";
        String STATUS = "status";
        String MESSAGE = "message";
        String DEVELOPER_MESSAGE = "developerMessage";
    }

    public interface TimeOut {
        int IMAGE_UPLOAD_CONNECTION_TIMEOUT = 120;
        int IMAGE_UPLOAD_SOCKET_TIMEOUT = 120;
        int SOCKET_TIME_OUT = 120;
        int CONNECTION_TIME_OUT = 120;
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    public static String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();
        ;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);
        return properties.getProperty(key);
    }


    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
    public static BeautifulProgressDialog showProgressLoading(Activity activity) {
        BeautifulProgressDialog progressDialog = new BeautifulProgressDialog(activity,
                BeautifulProgressDialog.withGIF,
                null);
        Uri myUri = Uri.fromFile(new File("//android_asset/gif/ziniloader.gif"));
        progressDialog.setGifLocation(myUri);
        progressDialog.setCancelable(false);
        progressDialog.setCancelableOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    public static String getDuration(String url) {
        String duration = null;

        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            if (url.contains("http")) {
                retriever.setDataSource(url, new HashMap<>());
            } else {
                retriever.setDataSource(url);
            }
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            if (time != null && !time.isEmpty()) {
                long timeInMillisec = Long.parseLong(time);
                retriever.release();
                duration = convertMillieToHMmSs(timeInMillisec);
            } else {
                duration = "00:00";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static boolean isValidFile(String url) {
        /*3 min validation*/
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            if (url.contains("http")) {
                retriever.setDataSource(url, new HashMap<>());
            } else {
                retriever.setDataSource(url);
            }
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            if (time != null && !time.isEmpty()) {
                long timeInMillisec = Long.parseLong(time);
                retriever.release();
                return timeInMillisec <= 180000;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String convertMillieToHMmSs(long millie) {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            return String.format("%02d:%02d", minute, second);
        }

    }

    public static boolean getTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        formatter.setLenient(false);
        Date curDate = new Date();
        String dss = formatter.format(curDate.getTime());
        Log.e("current_date==>12", dss + "");

        Date oldDate = null;
        Date cDate = null;

        try {
            oldDate = formatter.parse(time);
            cDate = formatter.parse(dss);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (oldDate.after(cDate)) {
            Log.e("your_date_is_outdated", "1");
            return true;
        } else {
            Log.e("your_date_is_outdated", "2");

            return false;
        }
    }

    public static void setLocale(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new LocaleList(locale));
        } else {
            configuration.locale = locale;
        }

        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }


}
