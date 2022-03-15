package com.mamits.zini24user.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.mamits.zini24user.R;


public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
        init(null);

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public void setFontName(String fontName){

        try {
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/" + fontName);
                setTypeface(myTypeface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontName = a.getString(R.styleable.CustomTextView_fontName);

            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }
}
