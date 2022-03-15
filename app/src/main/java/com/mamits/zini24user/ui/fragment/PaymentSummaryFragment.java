package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.offer.OfferDataModel;
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.data.model.payment.KeysDataModel;
import com.mamits.zini24user.databinding.FragmentPaymentSummaryBinding;
import com.mamits.zini24user.ui.activity.CouponsActivity;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.activity.PaymentActivity;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.navigator.fragment.PaymentSummaryFragmentNavigator;
import com.mamits.zini24user.ui.utils.DateConvertor;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.fragment.PaymentSummaryFragmentViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class PaymentSummaryFragment extends BaseFragment<FragmentPaymentSummaryBinding, PaymentSummaryFragmentViewModel>
        implements PaymentSummaryFragmentNavigator, View.OnClickListener {

    private String TAG = "PaymentSummaryFragment";
    private FragmentPaymentSummaryBinding binding;

    @Inject
    PaymentSummaryFragmentViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private OrdersDataModel model;
    private String mPayableAmt, mCouponAmt;
    private boolean isCouponApplied = false;
    private double latitude, longitude;
    private BottomSheetDialog paymentDialog;
    private KeysDataModel keyModel;

    @Override
    public PaymentSummaryFragmentViewModel getMyViewModel() {
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
            Bundle bundle = getArguments();
            if (bundle != null) {
                latitude = mViewModel.getmDataManger().getLatitude();
                longitude = mViewModel.getmDataManger().getLongitude();
                model = (OrdersDataModel) bundle.getSerializable("model");

                if (model.getPayable_amount() != null) {
                    mPayableAmt = model.getPayable_amount();
                } else {
                    mPayableAmt = "0.0";
                }
                if (model.getOffer_amount() != null) {
                    mCouponAmt = model.getOffer_amount();
                } else {
                    mCouponAmt = "0.0";
                }
                callCouponList();
                fetchPaymetKeys();
                Glide.with(mContext).load(model.getProducts().getProduct_image()).into(binding.imgServiceCat);

                binding.txtServiceName.setText(model.getProducts().getName());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Date d1 = formatter.parse(model.getCreated_at());
                    String[] date = new DateConvertor().getDate(d1.getTime(), DateConvertor.FORMAT_dd_MM_yyyy_HH_mm_ss).split(" ");
                    binding.txtDes.setText(String.format("Applied on %s", date[0]));

                } catch (Exception e) {
                    binding.txtDes.setText(String.format("Applied on %s", model.getCreated_at()));
                    e.printStackTrace();
                }
                binding.txtOrderId.setText(String.format("#%s", model.getOrder_id()));
                binding.txtUserName.setText(model.getStoredetail().getName());
                binding.txtNumber.setText(model.getStoredetail().getMobile_number());
                binding.textName.setText(model.getProducts().getName());
                binding.textPrice.setText(model.getOrder_amount());

                binding.etCoupon.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!s.toString().isEmpty()) {
                            binding.btnDeleteCoupon.setVisibility(View.VISIBLE);
                        } else {
                            binding.btnDeleteCoupon.setVisibility(View.GONE);
                            if (isCouponApplied) {
                                callRemoveCoupon();
                            }
                            mCouponAmt = "0.0";
                            mPayableAmt = model.getPayable_amount();
                            calculateTotal();
                        }
                    }
                });

                binding.btnDeleteCoupon.setOnClickListener(view1 -> {
                    if (isCouponApplied) {
                        callRemoveCoupon();
                    } else {
                        binding.etCoupon.setText("");
                    }
                });

                binding.btnApplyCoupon.setOnClickListener(view1 -> {
                    String coupon = binding.etCoupon.getText().toString();
                    if (coupon.isEmpty()) {
                        Toast.makeText(mContext, "Please Enter coupon.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    applyCoupon(coupon);
                });

                binding.selectCoupon.setOnClickListener(view1 -> {
                    Intent intent = new Intent(mContext, CouponsActivity.class);
                    intent.putExtra("order", model);
                    startActivityForResult(intent, 1001);
                });
                binding.textProceed.setOnClickListener(view1 -> {
                    showPaymentDialog();
                });

                new ClickShrinkEffect(binding.btnDeleteCoupon);
                new ClickShrinkEffect(binding.btnApplyCoupon);
                new ClickShrinkEffect(binding.textProceed);

                calculateTotal();

            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("data")) {
                OfferDataModel dataItem = (OfferDataModel) data.getSerializableExtra("data");
                binding.etCoupon.setText(dataItem.getCoupon());
                binding.btnApplyCoupon.performClick();
            }
        }
    }

    private void fetchPaymetKeys() {
        mViewModel.fetchPaymentKeys((Activity) mContext);
    }

    private void showPaymentDialog() {
        if (paymentDialog == null) {
            paymentDialog = new BottomSheetDialog(mContext);
            paymentDialog.setContentView(R.layout.payment_method_sheet);
            paymentDialog.setCanceledOnTouchOutside(false);

            CustomTextView txt_amount = paymentDialog.findViewById(R.id.txt_amount);
            RelativeLayout btn_cashfree = paymentDialog.findViewById(R.id.btn_cashfree);
            RelativeLayout btn_shop = paymentDialog.findViewById(R.id.btn_shop);
            RelativeLayout btn_upi = paymentDialog.findViewById(R.id.btn_upi);
            txt_amount.setText(String.format("Pay ₹ %s Using", Float.parseFloat(mPayableAmt)));

            btn_cashfree.setOnClickListener(view -> {
                if (paymentDialog!=null){
                    paymentDialog.dismiss();
                }
                OrdersDataModel orderdetail = model;
                orderdetail.setPayable_amount(mPayableAmt);
                /*cashfree payment*/

                makePayment(0);

            });

            btn_shop.setOnClickListener(view -> {
                if (paymentDialog!=null){
                    paymentDialog.dismiss();
                }
                goToPaymentDetail("Yes");
            });

            btn_upi.setOnClickListener(view -> {
                if (paymentDialog!=null){
                    paymentDialog.dismiss();
                }
                goToPaymentDetail("No");
            });
            paymentDialog.setOnDismissListener(dialog -> {
                paymentDialog = null;
            });
            paymentDialog.show();
        }
    }

    private void makePayment(int position) {
        try {
            double amount = Double.parseDouble(model.getPayable_amount());
            if (amount == 0) {
                Log.e(TAG, "Amount is 0.");
                return;
            }
            Intent payIntent = new Intent(mContext, PaymentActivity.class);
            payIntent.putExtra("amount", String.valueOf(model.getPayable_amount()));
            if (position == 0) {
                paymentDialog.dismiss();
                /*cashfree*/

                payIntent.putExtra("appid", keyModel.getAppid());//"936476e4b0e75a0300a64fc14639"
                payIntent.putExtra("order_id", model.getOrder_id());
            } else {
                /*paytm*/
                payIntent.putExtra("m_id", keyModel.getPaytm_merchant_mid());
            }

            startActivity(payIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToPaymentDetail(String isShop) {
        OrdersDataModel orderdetail = model;
        orderdetail.setPayable_amount(mPayableAmt);
        orderdetail.setIsPaytoShop(isShop);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", orderdetail);
        Navigation.findNavController(((DashboardActivity)mContext).findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_payment_detail, bundle);
    }

    private void callRemoveCoupon() {
        mViewModel.removeCoupon((Activity) mContext, model.getId(), mCouponAmt, mPayableAmt);
    }

    private void callCouponList() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("orderid", String.valueOf(model.getId()));
            jsonObject.put("latitude", latitude != 0 ? latitude : "24.5695588");
            jsonObject.put("longitude", longitude != 0 ? longitude : "80.8645887");
            jsonObject.put("start", 0);
            jsonObject.put("pagelength", 100);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchCouponList((Activity) mContext, jsonObject);

    }

    private void applyCoupon(String coupon) {
        mViewModel.applyCoupon((Activity) mContext, coupon, model.getId(), model.getOrder_amount());
    }

    private void calculateTotal() {
        binding.textCouponPrice.setText(String.format("- %s %s", mContext.getString(R.string.rupee), mCouponAmt));
        binding.textTotalPrice.setText(String.format("- %s %s", mContext.getString(R.string.rupee), mPayableAmt));
    }


    @Override
    public int getBindingVariable() {
        return BR.paymentSummaryFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_payment_summary;
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
    public void onSuccessCouponList(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type offerDataList = new TypeToken<List<OfferDataModel>>() {
                }.getType();
                List<OfferDataModel> offersList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), offerDataList);

                for (OfferDataModel it : offersList) {
                    if (it.getId() == model.getOffer_id()) {
                        binding.etCoupon.setText(it.getCoupon());

                        isCouponApplied = it.getCoupon() != null && !it.getCoupon().isEmpty();

                        if (model.getOffer_amount() != null && !model.getOffer_amount().isEmpty()) {
                            mCouponAmt = model.getOffer_amount();
                            mPayableAmt = model.getPayable_amount();
                            binding.textCouponPrice.setText(String.format("- %s%s", mContext.getString(R.string.rupee), it.getDiscount_amount()));
                            calculateTotal();
                        }
                    }

                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessApplyCoupon(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mCouponAmt = jsonObject.get("data").getAsJsonObject().get("discountamount").getAsString();
                mPayableAmt = jsonObject.get("data").getAsJsonObject().get("finalamountpay").getAsString();
                isCouponApplied = true;
                calculateTotal();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
            }
            String message = jsonObject.get("message").getAsString();
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessRemoveCoupon(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                isCouponApplied = false;
                binding.etCoupon.setText("");
                if (!mPayableAmt.isEmpty() && !mCouponAmt.isEmpty()) {
                    mPayableAmt = model.getOrder_amount();
                    mCouponAmt = "0.00";
                    calculateTotal();
                }
            }
            String message = jsonObject.get("message").getAsString();
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessPaymentKeys(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                keyModel = mGson.fromJson(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().toString(), KeysDataModel.class);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}