package com.mamits.zini24user.ui.utils.listeners;

import android.view.View;

import com.mamits.zini24user.data.model.form.CustomFieldObject;
import com.mamits.zini24user.data.model.form.ValueModel;
import com.mamits.zini24user.data.model.search.SearchDataModel;

public interface OnItemClickListener {
    void onClick(int pos, View view, CustomFieldObject obj);
    void onClick(int pos, View view, CustomFieldObject obj, String type);
    void onClick(int pos, View view, ValueModel obj);

    void onClick(int pos, View view, SearchDataModel obj);
}
