package com.mamits.zini24user.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.BuildConfig;
import com.mamits.zini24user.R;
import com.mamits.zini24user.databinding.FragmentProfileBinding;
import com.mamits.zini24user.ui.activity.WebViewActivity;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.ProfileFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.ProfileFragmentViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import javax.inject.Inject;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileFragmentViewModel> implements ProfileFragmentNavigator, View.OnClickListener {

    private String TAG = "ProfileFragment";
    private FragmentProfileBinding binding;

    @Inject
    ProfileFragmentViewModel mViewModel;
    private Context mContext;

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
            setData();

            binding.btnShare.setOnClickListener(view1 -> {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zini24");
                    String shareMessage = "\nLet me recommend you Zini24 application\n\n";
                    shareMessage =
                            shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID.trim();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            binding.btnPrivacy.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("type", "privacy");
                mContext.startActivity(intent);
            });

            binding.btnTc.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("type", "tc");
                mContext.startActivity(intent);
            });

            binding.btnAbout.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("type", "about");
                mContext.startActivity(intent);
            });

            binding.btnHelp.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("type", "help");
                mContext.startActivity(intent);
            });

            binding.btnEnquiry.setOnClickListener(view1 -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("type", "enquiry");
                mContext.startActivity(intent);
            });

            binding.btnChangePass.setOnClickListener(view1 -> {
                Navigation.findNavController(view).navigate(R.id.nav_change_pass);
            });
            binding.btnEdit.setOnClickListener(view1 -> {
                Navigation.findNavController(view).navigate(R.id.nav_update_profile);
            });

            new ClickShrinkEffect(binding.btnChangePass);
            new ClickShrinkEffect(binding.btnEnquiry);
            new ClickShrinkEffect(binding.btnHelp);
            new ClickShrinkEffect(binding.btnShare);
            new ClickShrinkEffect(binding.btnEdit);
        }
    }

    private void setData() {
        binding.txtUserName.setText(mViewModel.getmDataManger().getUsername());
        binding.txtEmail.setText(mViewModel.getmDataManger().getUserEmail());
        binding.txtNumber.setText(mViewModel.getmDataManger().getUserNumber());
    }

    @Override
    public int getBindingVariable() {
        return BR.profileView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
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

    }

}