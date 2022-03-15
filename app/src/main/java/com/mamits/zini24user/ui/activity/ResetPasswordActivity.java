package com.mamits.zini24user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.databinding.ActivityResetPasswordBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.activity.ResetPasswordNavigator;
import com.mamits.zini24user.viewmodel.activity.ResetPasswordViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import javax.inject.Inject;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding, ResetPasswordViewModel>
        implements ResetPasswordNavigator, View.OnClickListener {

    String TAG = "ResetPasswordActivity";
    @Inject
    ResetPasswordViewModel mViewModel;
    ActivityResetPasswordBinding binding;
    private Gson mGson;
    private String number;

    @Override
    public int getBindingVariable() {
        return BR.resetView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);
        if (getIntent().hasExtra("number")) {
            number = getIntent().getStringExtra("number");
        }
        binding.btnReset.setOnClickListener(this);
        new ClickShrinkEffect(binding.btnReset);
    }

    @Override
    protected ResetPasswordViewModel getMyViewModel() {
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
    public void onSuccessPasswordUpdated(JsonObject jsonObject, String number) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            case R.id.btn_reset:
                String newPin = binding.etNew.getText().toString();
                String cnfPin = binding.etConfirm.getText().toString();

                if (newPin.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your new password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cnfPin.trim().length() ==0) {
                    Toast.makeText(this, "Please confirm password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPin.equals(cnfPin)) {
                    Toast.makeText(this, "Password didn't match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                updatePin(number, newPin);
                break;
        }
    }

    private void updatePin(String number, String newPin) {
        mViewModel.updatePin(this, number, newPin);

    }

}