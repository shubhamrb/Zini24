package com.mamits.zini24user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.login.LoginDataModel;
import com.mamits.zini24user.databinding.ActivityLoginBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.activity.LoginActivityNavigator;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.activity.LoginActivityViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginActivityViewModel>
        implements LoginActivityNavigator, View.OnClickListener {

    String TAG = "LoginActivity";
    @Inject
    LoginActivityViewModel mViewModel;
    ActivityLoginBinding binding;
    private Gson mGson;
    private boolean isPassVisible = false;

    @Override
    public int getBindingVariable() {
        return BR.loginView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        if (mViewModel.getmDataManger().getCurrentUserId() != -1) {
            Intent dashboardIntent = new Intent(this, DashboardActivity.class);
            startActivity(dashboardIntent);
            finishAffinity();
        }

        binding.btnSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnForgot.setOnClickListener(this);
        binding.btnPassToggle.setOnClickListener(this);

        new ClickShrinkEffect(binding.btnSignup);
        new ClickShrinkEffect(binding.btnLogin);
        new ClickShrinkEffect(binding.btnForgot);
        new ClickShrinkEffect(binding.btnPassToggle);
    }

    @Override
    protected LoginActivityViewModel getMyViewModel() {
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
    public void onSuccessUserLogin(JsonObject jsonObject) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                String number = binding.etNumber.getText().toString();
                String pass = binding.etPass.getText().toString();

                if (number.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your registered number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                doLogin(number, pass);
                break;
            case R.id.btn_forgot:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.btn_PassToggle:
                if (isPassVisible) {
                    isPassVisible=false;
                    binding.etPass.setTransformationMethod(new PasswordTransformationMethod());
                    binding.btnPassToggle.setImageResource(R.drawable.eye_open);
                } else {
                    isPassVisible=true;
                    binding.etPass.setTransformationMethod(null);
                    binding.btnPassToggle.setImageResource(R.drawable.eye_crossed);
                }
                binding.etPass.setSelection(binding.etPass.getText().length());
                break;
        }
    }

    private void doLogin(String number, String pass) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }

            if (task.isSuccessful()) {
                String token = task.getResult();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", number);
                    jsonObject.put("pin", pass);
                    jsonObject.put("api_key", AppConstant.API_KEY);
                    jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
                    jsonObject.put("device_token", token);
                    mViewModel.userLogin(this, jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}