package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.databinding.FragmentUpdateProfileBinding;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.ProfileFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.ProfileFragmentViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class UpdateProfileFragment extends BaseFragment<FragmentUpdateProfileBinding, ProfileFragmentViewModel> implements ProfileFragmentNavigator, View.OnClickListener {

    private String TAG = "UpdateProfileFragment";
    private FragmentUpdateProfileBinding binding;

    @Inject
    ProfileFragmentViewModel mViewModel;
    private Context mContext;
    private Gson mGson;

    @Override
    public ProfileFragmentViewModel getMyViewModel() {
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
            binding.etName.setText(mViewModel.getmDataManger().getUsername());
            binding.etEmail.setText(mViewModel.getmDataManger().getUserEmail());
            binding.tvNumber.setText(mViewModel.getmDataManger().getUserNumber());
            binding.btnSubmit.setOnClickListener(this);
            new ClickShrinkEffect(binding.btnSubmit);
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.updateProfileView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_update_profile;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String name = binding.etName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String number = binding.tvNumber.getText().toString();

                if (name.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter full name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (number.trim().length() == 0) {
                    Toast.makeText(mContext, "Please Confirm password.", Toast.LENGTH_SHORT).show();
                    return;
                }*/


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", number);
                    jsonObject.put("name", name);
                    jsonObject.put("email", email);

                    updateProfile(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void updateProfile(JSONObject couponObject) {
        mViewModel.updateProfile((Activity) mContext, couponObject);
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessUpdateProfile(JsonObject jsonObject) {
        if (jsonObject != null) {

            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                mViewModel.getmDataManger().settUserEmail(binding.etEmail.getText().toString());
                mViewModel.getmDataManger().setUsername(binding.etName.getText().toString());
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}