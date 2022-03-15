package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.notification.NotificationModel;
import com.mamits.zini24user.databinding.FragmentNotificationBinding;
import com.mamits.zini24user.ui.adapter.NotificationAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.NotificationFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.NotificationFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class NotificationFragment extends BaseFragment<FragmentNotificationBinding, NotificationFragmentViewModel> implements NotificationFragmentNavigator, View.OnClickListener {

    private String TAG = "NotificationFragment";
    private FragmentNotificationBinding binding;

    @Inject
    NotificationFragmentViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private final int LIMIT = 10;
    private int START_PAGE = 0;
    private int CURRENT_PAGE = START_PAGE;
    private NotificationAdapter notificationAdapter;

    @Override
    public NotificationFragmentViewModel getMyViewModel() {
        return mViewModel;
    }

    @Override
    protected void initView(View view, boolean isRefresh) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);
        if (getActivity() != null) {
            mContext = getActivity();
        } else if (getBaseActivity() != null) {
            mContext = getBaseActivity();
        } else if (view.getContext() != null) {
            mContext = view.getContext();
        }
        if (isRefresh) {
            mGson = new Gson();
            binding.btnNext.setOnClickListener(view1 -> {
                CURRENT_PAGE++;
                loadNotification(CURRENT_PAGE);
            });
            setUpNotificationList();
        }
    }

    private void setUpNotificationList() {
        binding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationAdapter = new NotificationAdapter(getActivity());
        binding.recyclerViewNotification.setAdapter(notificationAdapter);
        loadNotification(CURRENT_PAGE);
    }

    private void loadNotification(int current_page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start", current_page);
            jsonObject.put("pagelength", LIMIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchNotification((Activity) mContext, jsonObject);
    }


    @Override
    public int getBindingVariable() {
        return BR.notificationView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }
    @Override
    public void checkValidation(int errorCode, String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessNotification(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type notification = new TypeToken<List<NotificationModel>>() {
                }.getType();
                List<NotificationModel> notificationList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), notification);

                if (jsonObject.get("next").getAsBoolean()) {
                    binding.btnNext.setVisibility(View.VISIBLE);
                } else {
                    binding.btnNext.setVisibility(View.GONE);
                }
                notificationAdapter.setList(notificationList);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }


}