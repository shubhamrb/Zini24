package com.mamits.zini24user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.databinding.ActivityForgotPasswordBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.navigator.activity.ForgotPasswordActivityNavigator;
import com.mamits.zini24user.viewmodel.activity.ForgotPasswordActivityViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordActivityViewModel>
        implements ForgotPasswordActivityNavigator, View.OnClickListener {

    String TAG = "LoginActivity";
    @Inject
    ForgotPasswordActivityViewModel mViewModel;
    ActivityForgotPasswordBinding binding;
    private CustomTextView txt_resend;

    @Override
    public int getBindingVariable() {
        return BR.forgotView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        new ClickShrinkEffect(binding.btnSendOtp);
        binding.btnSendOtp.setOnClickListener(this);
    }

    @Override
    protected ForgotPasswordActivityViewModel getMyViewModel() {
        return mViewModel;
    }


    @Override
    public void showLoader() {
        showLoading();
    }

    @Override
    public void hideLoader() {
        hideLoading();
    }

    @Override
    public void checkValidation(int type, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable it) {
        it.printStackTrace();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccessSendOtp(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String number = jsonObject.get("data").getAsJsonObject().get("phone_number").getAsString();
                Toast.makeText(this, "OTP sent to your registered number.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, OtpActivity.class);
                intent.putExtra("mobile", number);
                startActivity(intent);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_otp:
                String number = binding.etNumber.getText().toString();
                if (number.length() < 10) {
                    Toast.makeText(this, "Invalid mobile no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendOtp(number);
                break;
        }
    }

    private void sendOtp(String number) {
        mViewModel.sendOtp(this, number);
    }

}