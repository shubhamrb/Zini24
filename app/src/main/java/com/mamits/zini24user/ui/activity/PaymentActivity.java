package com.mamits.zini24user.ui.activity;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cashfree.pg.CFPaymentService;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.databinding.ActivityPaymentBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.activity.PaymentActivityNavigator;
import com.mamits.zini24user.viewmodel.activity.PaymentActivityViewModel;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentActivityViewModel>
        implements PaymentActivityNavigator, View.OnClickListener, PaytmPaymentTransactionCallback {
    String TAG = "PaymentActivity";
    @Inject
    PaymentActivityViewModel mViewModel;
    ActivityPaymentBinding binding;
    private String orderId, customerName, customerPhone, customerEmail, amount, appid, m_id;
    private static final String STAGE_CALLBACK_URL_PAYTM = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
    private static final String PROD_CALLBACK_URL_PAYTM = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";

    @Override
    public int getBindingVariable() {
        return BR.paymentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        binding.btnHome.setOnClickListener(this);

        new ClickShrinkEffect(binding.btnHome);

        if (!mViewModel.getmDataManger().isPaymentOpen()) {
            mViewModel.getmDataManger().setPaymentOpen(true);

            amount = getIntent().getStringExtra("amount");
            customerName = mViewModel.getmDataManger().getUsername();
            customerPhone = mViewModel.getmDataManger().getUserNumber();
//            customerEmail = mViewModel.getmDataManger().getUserEmail();
            customerEmail = "support@zini24user.in";

            if (getIntent().hasExtra("appid")) {
                appid = getIntent().getStringExtra("appid");
                orderId = getIntent().getStringExtra("order_id");
            } else if (getIntent().hasExtra("m_id")) {
                m_id = getIntent().getStringExtra("m_id");
            }
            startPayment();
        }

    }

    private void startPayment() {
        if (appid != null) {
            getCfsToken();
        } else if (m_id != null) {
            getPaytmToken();
        }

    }

    private void getPaytmToken() {
        mViewModel.fetchPaytmToken(this, orderId, amount, customerPhone, customerEmail);
    }

    private void getCfsToken() {
        mViewModel.fetchCfsToken(this, orderId, amount);
    }

    @Override
    protected PaymentActivityViewModel getMyViewModel() {
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
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void throwable(Throwable it) {
        it.printStackTrace();
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessCfsToken(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String token = jsonObject.get("data").getAsString();

                if (token != null && !token.equals("")) {
                    doPayment(token);
                }
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                binding.btnHome.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSuccessSavePaymentResponse(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Log.e(TAG, message);
            }
        }
    }

    private void doPayment(String token) {
        if (appid != null) {
            /*cashfree*/
            Map<String, String> params = getInputParams();
            if (params != null) {
                Log.e(TAG, "params : " + params);
                CFPaymentService.getCFPaymentServiceInstance().doPayment(this, params, token, "PROD");
            } else {
                Log.e(TAG, "PAYMENT : " + "keys not found");
                finish();
            }
        } else {
            /*paytm*/
            PaytmOrder paytmOrder = new PaytmOrder(orderId, m_id, token, amount, PROD_CALLBACK_URL_PAYTM + orderId);
            TransactionManager transactionManager = new TransactionManager(paytmOrder, this);
            transactionManager.setAppInvokeEnabled(false);
            transactionManager.startTransaction(this, 101);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.progressBar.setVisibility(View.GONE);
        binding.btnHome.setVisibility(View.VISIBLE);
        if (requestCode == CFPaymentService.REQ_CODE && data != null) {
            try {
                Bundle bundle = data.getExtras();
                StringBuilder builder = new StringBuilder();
                if (bundle != null) {
                    String orderId = bundle.getString("orderId");
                    builder.append("ORDER ID").append(" : #").append(orderId).append("\n");

                    String status = bundle.getString("txStatus");

                    if (status.toLowerCase().contains("success")) {
                        JSONObject jsonObject = new JSONObject();

                        String orderAmount = bundle.getString("orderAmount");
                        String referenceId = bundle.getString("referenceId");
                        String paymentMode = bundle.getString("paymentMode");
                        String txMsg = bundle.getString("txMsg");
                        String txTime = bundle.getString("txTime");
                        String paymentMethod = bundle.getString("paymentMethod");

                        jsonObject.put("orderId", orderId);
                        jsonObject.put("orderAmount", orderAmount);
                        jsonObject.put("referenceId", referenceId);
                        jsonObject.put("txStatus", status);
                        jsonObject.put("paymentMode", paymentMode);
                        jsonObject.put("txMsg", txMsg);
                        jsonObject.put("txTime", txTime);
                        jsonObject.put("paymentMethod", paymentMethod != null ? paymentMethod : "Online");

                        savePaymentResponse(jsonObject);

                        binding.imgStatus.setImageResource(R.drawable.checked);
                        builder.append("PAYMENT STATUS").append(" : ").append("SUCCESS");
                    }
                    else {
                        binding.imgStatus.setImageResource(R.drawable.cancel);
                        builder.append("PAYMENT STATUS").append(" : ").append("FAILED");
                    }

                    binding.imgStatus.setVisibility(View.VISIBLE);


                    binding.txtMessage.setText(builder.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == 101 && data != null) {
            binding.txtMessage.setText(data.getStringExtra("nativeSdkForMerchantMessage") + "\n" + data.getStringExtra("response"));
        }
    }

    private void savePaymentResponse(JSONObject jsonObject) {
        mViewModel.savePaymentResponse(this, jsonObject);
    }

    private Map<String, String> getInputParams() {

        if (amount != null && appid != null) {
            Map<String, String> params = new HashMap<>();

            params.put(PARAM_APP_ID, appid);
            params.put(PARAM_ORDER_ID, orderId);
            params.put(PARAM_ORDER_AMOUNT, amount);
            params.put(PARAM_CUSTOMER_NAME, customerName);
            params.put(PARAM_CUSTOMER_PHONE, customerPhone);
            params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
            params.put(PARAM_ORDER_CURRENCY, "INR");
            params.put(PARAM_NOTIFY_URL, "https://test.gocashfree.com/notify");
            return params;
        } else {
            return null;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                mViewModel.getmDataManger().setPaymentOpen(false);
                startActivity(new Intent(this, DashboardActivity.class));
                finishAffinity();
                break;
        }
    }

    @Override
    public void onTransactionResponse(@Nullable Bundle bundle) {
        if (bundle != null) {
            binding.txtMessage.setText(bundle.toString());
        }
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void networkNotAvailable() {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : network not available");
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorProceed(String s) {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : " + s);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : " + s);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void someUIErrorOccurred(String s) {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : " + s);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : " + s + " " + s1);
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : User cancelled");
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgStatus.setImageResource(R.drawable.cancel);
        binding.imgStatus.setVisibility(View.VISIBLE);
        binding.txtMessage.setText("PAYMENT ERROR : " + s + " " + bundle.toString());
        binding.btnHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
    }
}