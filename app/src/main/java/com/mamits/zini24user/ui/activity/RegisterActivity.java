package com.mamits.zini24user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.signup.SignUpDataModel;
import com.mamits.zini24user.databinding.ActivityRegisterBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.navigator.activity.RegisterActivityNavigator;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.activity.RegisterActivityViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterActivityViewModel>
        implements RegisterActivityNavigator, View.OnClickListener {

    String TAG = "RegisterActivity";
    @Inject
    RegisterActivityViewModel mViewModel;
    ActivityRegisterBinding binding;
    private CustomTextView txt_resend;
    private Gson mGson;
    private String name, number, pass;

    @Override
    public int getBindingVariable() {
        return BR.registerView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);

        new ClickShrinkEffect(binding.btnLogin);
        new ClickShrinkEffect(binding.btnSignup);
    }

    @Override
    protected RegisterActivityViewModel getMyViewModel() {
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
    public void onSuccessUserSignUp(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                mGson = new Gson();
                SignUpDataModel model = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), SignUpDataModel.class);

                Intent intent = new Intent(this, OtpActivity.class);
                intent.putExtra("mobile", number);
                intent.putExtra("name", name);
                intent.putExtra("pass", pass);
                intent.putExtra("data", model);
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
            case R.id.btn_login:
                finish();
                break;
            case R.id.btn_signup:
                name = binding.etName.getText().toString();
                number = binding.etNumber.getText().toString();
                pass = binding.etPass.getText().toString();

                if (name.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (number.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your registered number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                doSignUp(name, number, pass);
                break;
        }
    }

    private void doSignUp(String name, String number, String pass) {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (token != null && token.length() != 0) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("mobile", number);
                    jsonObject.put("pin", pass);
                    jsonObject.put("api_key", AppConstant.API_KEY);
                    jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
                    jsonObject.put("device_token", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mViewModel.userSignUp(this, jsonObject.toString());
            }
        });
    }
}