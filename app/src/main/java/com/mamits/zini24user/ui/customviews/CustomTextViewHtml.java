package com.mamits.zini24user.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.ui.utils.commonClasses.AppCode;


public class CustomTextViewHtml extends AppCompatTextView {
    public CustomTextViewHtml(Context context) {
        super(context);
    }

    public CustomTextViewHtml(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextViewHtml(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

//    public CustomTextViewHtml(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(attrs);
//    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontName = a.getString(R.styleable.CustomTextView_fontName);

            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/"+fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        CharSequence texts = text;
        if (text.length() > 0) {

            texts = String.valueOf(texts.charAt(0)).toUpperCase()+ texts.subSequence(1, texts.length());
            texts = AppCode.getSpannedText(texts.toString().trim());
        }
        super.setText(texts, type);
    }

}
