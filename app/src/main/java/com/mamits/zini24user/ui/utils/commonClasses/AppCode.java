package com.mamits.zini24user.ui.utils.commonClasses;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class AppCode {


    public static Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("Fdsfdsfs==>1", text);
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            Log.e("Fdsfdsfs", text);
            return Html.fromHtml(text);
        }
    }
}
