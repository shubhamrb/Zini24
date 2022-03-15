package com.mamits.zini24user.ui.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

public class CustomInputEditText extends TextInputEditText {

    private Drawable drawableRight = null;
    private Drawable drawableLeft = null;
    private Drawable drawableTop = null;
    private Drawable drawableBottom = null;

    private int actionX = 0;
    private int actionY = 0;


    private String TAG = "CustomEditView";


    public CustomInputEditText(Context context) {
        super(context);
    }

    public CustomInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left != null) {
            drawableLeft = left;
        }
        if (right != null) {
            drawableRight = right;
        }
        if (top != null) {
            drawableTop = top;
        }
        if (bottom != null) {
            drawableBottom = bottom;
        }
        super.setCompoundDrawables(left, top, right, bottom);

    }


}
