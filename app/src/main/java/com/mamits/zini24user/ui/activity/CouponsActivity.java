package com.mamits.zini24user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.offer.OfferDataModel;
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.databinding.ActivityCouponsBinding;
import com.mamits.zini24user.ui.adapter.OffersAdapter;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.fragment.OfferFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.OfferFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class CouponsActivity extends BaseActivity<ActivityCouponsBinding, OfferFragmentViewModel>
        implements OfferFragmentNavigator, View.OnClickListener, OffersAdapter.couponSelectListener {

    String TAG = "CouponsActivity";
    @Inject
    OfferFragmentViewModel mViewModel;
    ActivityCouponsBinding binding;
    private Gson mGson;
    private double latitude, longitude;

    private final int LIMIT = 10;
    private int START_PAGE = 0;
    private int CURRENT_PAGE = START_PAGE;
    private OffersAdapter offersAdapter;
    private OrdersDataModel model;

    @Override
    public int getBindingVariable() {
        return BR.couponsView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupons;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        mGson = new Gson();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            model = (OrdersDataModel) bundle.getSerializable("order");
        }
        latitude = mViewModel.getmDataManger().getLatitude();
        longitude = mViewModel.getmDataManger().getLongitude();

        binding.btnNext.setOnClickListener(view1 -> {
            CURRENT_PAGE++;
            loadOffers(CURRENT_PAGE);
        });

        setUpOffersList();
    }

    private void setUpOffersList() {
        binding.recyclerViewOffers.setLayoutManager(new LinearLayoutManager(this));
        offersAdapter = new OffersAdapter(this, this);
        binding.recyclerViewOffers.setAdapter(offersAdapter);
        loadOffers(CURRENT_PAGE);
    }

    private void loadOffers(int current_page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("latitude", latitude != 0 ? latitude : "24.5695588");
            jsonObject.put("longitude", longitude != 0 ? longitude : "80.8645887");
            jsonObject.put("start", current_page);
            jsonObject.put("pagelength", LIMIT);
            jsonObject.put("orderid", model.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchOffers(this, jsonObject);
    }

    @Override
    protected OfferFragmentViewModel getMyViewModel() {
        return mViewModel;
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
    public void onSuccessOffers(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type offerDataList = new TypeToken<List<OfferDataModel>>() {
                }.getType();
                List<OfferDataModel> offersList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), offerDataList);
                if (jsonObject.get("next").getAsBoolean()) {
                    binding.btnNext.setVisibility(View.VISIBLE);
                } else {
                    binding.btnNext.setVisibility(View.GONE);
                }
                offersAdapter.setList(offersList);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showProgressBars() {
        showLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBars() {
        hideLoading();
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSelect(OfferDataModel data) {
        Intent intent = new Intent();
        intent.putExtra("data", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}