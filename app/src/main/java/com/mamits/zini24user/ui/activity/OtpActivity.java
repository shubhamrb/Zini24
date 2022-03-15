package com.mamits.zini24user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.login.LoginDataModel;
import com.mamits.zini24user.data.model.signup.SignUpDataModel;
import com.mamits.zini24user.databinding.ActivityOtpBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.activity.OtpActivityNavigator;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.activity.OtpActivityViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpActivityViewModel>
        implements OtpActivityNavigator, View.OnClickListener {

    String TAG = "OtpActivity";
    @Inject
    OtpActivityViewModel mViewModel;
    ActivityOtpBinding binding;
    private String mobile, name, pass;
    private SignUpDataModel model;
    private Gson mGson;

    @Override
    public int getBindingVariable() {
        return BR.otpView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile = bundle.getString("mobile");
            name = bundle.getString("name");
            pass = bundle.getString("pass");
            model = (SignUpDataModel) bundle.getSerializable("data");
            binding.txtNumber.setText(mobile);
            startTimer();
        }
        binding.btnVerifyOtp.setOnClickListener(this);
        binding.btnResend.setOnClickListener(this);

        new ClickShrinkEffect(binding.btnVerifyOtp);
        new ClickShrinkEffect(binding.btnResend);
    }

    @Override
    protected OtpActivityViewModel getMyViewModel() {
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
    public void onSuccessVerifyOtp(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                LoginDataModel model = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), LoginDataModel.class);
                mViewModel.getmDataManger().setAccessToken(model.getToken());
                mViewModel.getmDataManger().setCurrentUserId(model.getUser().getId());
                mViewModel.getmDataManger().setUsername(model.getUser().getName());
                mViewModel.getmDataManger().settUserNumber(model.getUser().getPhone());
                mViewModel.getmDataManger().settUserEmail(model.getUser().getEmail());

                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onSuccessVerifyOtp(JsonObject jsonObject, String number) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,ResetPasswordActivity.class).putExtra("number",number));
                finish();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public void onSuccessResendOtp(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                mGson = new Gson();
                model = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), SignUpDataModel.class);
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
            case R.id.btn_verify_otp:
                String otp = binding.etOtp.getText().toString();
                if (otp.trim().length() == 0) {
                    Toast.makeText(this, "Please enter the OTP.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model!=null && !otp.equals(model.getOtp())) {
                    Toast.makeText(this, "Invalid OTP.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model!=null){
                    verifyOtp(otp);
                }else {
                    mViewModel.verifyOtp(this, mobile,otp);
                }
                break;
            case R.id.btn_resend:
                if (binding.btnResend.getText().toString().equalsIgnoreCase("resend")) {
                    if (model!=null){
                        resendOtp();
                    }else {
                        mViewModel.sendOtp(this, mobile);
                    }
                }

                break;
        }
    }

    private void resendOtp() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (token != null && token.length() != 0) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", mobile);
                    jsonObject.put("name", name);
                    jsonObject.put("pin", pass);
                    jsonObject.put("api_key", AppConstant.API_KEY);
                    jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
                    jsonObject.put("device_token", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    mViewModel.resendOtp(this, jsonObject.toString());

            }
        });
    }

    @Override
    public void onSuccessSendOtp(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String number = jsonObject.get("data").getAsJsonObject().get("phone_number").getAsString();
                Toast.makeText(this, "OTP sent to your registered number.", Toast.LENGTH_SHORT).show();
                startTimer();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void verifyOtp(String otp) {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (token != null && token.length() != 0) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("mobile", mobile);
                    jsonObject.put("pin", pass);
                    jsonObject.put("otp", otp);
                    jsonObject.put("api_key", AppConstant.API_KEY);
                    jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
                    jsonObject.put("device_token", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mViewModel.verifyOtp(this, jsonObject.toString());
            }
        });
    }

    private void startTimer() {
        new CountDownTimer(59000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.btnResend.setText("Resend OTP in " + millisUntilFinished / 1000);
                binding.btnResend.setTextColor(getResources().getColor(R.color.black));
            }

            public void onFinish() {
                binding.btnResend.setText("Resend");
                binding.btnResend.setTextColor(getResources().getColor(R.color.color_orange));
            }

        }.start();
    }
}