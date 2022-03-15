package com.mamits.zini24user.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.databinding.FragmentPaymentDetailBinding;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.PaymentDetailNavigator;
import com.mamits.zini24user.viewmodel.fragment.PaymentDetailViewModel;

import javax.inject.Inject;

public class PaymentDetailFragment extends BaseFragment<FragmentPaymentDetailBinding, PaymentDetailViewModel>
        implements PaymentDetailNavigator, View.OnClickListener {

    private String TAG = "PaymentDetailFragment";
    private FragmentPaymentDetailBinding binding;

    @Inject
    PaymentDetailViewModel mViewModel;
    private Context mContext;
    private OrdersDataModel model;

    @Override
    public PaymentDetailViewModel getMyViewModel() {
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
            Bundle bundle = getArguments();
            if (bundle != null) {
                model = (OrdersDataModel) bundle.getSerializable("model");

                if(!model.getIsPaytoShop().isEmpty() && model.getIsPaytoShop().equals("Yes")){
                    binding.txtMethodName.setText(String.format("Please visit %s shop & make payment of %s%s", model.getStoredetail().getName(), mContext.getString(R.string.rupee), model.getPayable_amount()));
                    binding.textStoreName.setVisibility(View.GONE);
                    binding.txtMethodDesc.setVisibility(View.GONE);
                    binding.imageQrCode.setVisibility(View.GONE);
                    binding.textInstruction.setVisibility(View.GONE);
                }else{
                    binding.txtMethodDesc.setText(String.format("Scan the QR Code to make payment of %s%s", mContext.getString(R.string.rupee), model.getPayable_amount()));
                    binding.textInstruction.setText(String.format("Scan the QR Code to make payment of %s%s", mContext.getString(R.string.rupee), model.getPayable_amount()));
                    binding.imageQrCode.setVisibility(View.VISIBLE);
                    binding.textStoreName.setVisibility(View.VISIBLE);
                    binding.txtMethodDesc.setVisibility(View.VISIBLE);
                    binding.textInstruction.setVisibility(View.VISIBLE);
                    binding.txtMethodName.setText(String.format("Upi number: %s", model.getStoredetail().getUpi_number()));
                }
                binding.textStoreName.setText(model.getStoredetail().getName());
                binding.textInstr.setText("Once payment is done kindly share it with\nthe kiosk on chat section.");
                Glide.with(mContext).load(model.getStoredetail().getQr_image()).into(binding.imageQrCode);
            }
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.paymentDetailView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_payment_detail;
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

}