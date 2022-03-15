package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.offer.OfferDataModel;
import com.mamits.zini24user.databinding.FragmentOfferBinding;
import com.mamits.zini24user.ui.adapter.OffersAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.OfferFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.OfferFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class OfferFragment extends BaseFragment<FragmentOfferBinding, OfferFragmentViewModel> implements OfferFragmentNavigator, View.OnClickListener {

    private String TAG = "OfferFragment";
    private FragmentOfferBinding binding;

    @Inject
    OfferFragmentViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private double latitude, longitude;

    private final int LIMIT = 10;
    private int START_PAGE = 0;
    private int CURRENT_PAGE = START_PAGE;
    private OffersAdapter offersAdapter;

    @Override
    public OfferFragmentViewModel getMyViewModel() {
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
            latitude = mViewModel.getmDataManger().getLatitude();
            longitude = mViewModel.getmDataManger().getLongitude();

            binding.btnNext.setOnClickListener(view1 -> {
                CURRENT_PAGE++;
                loadOffers(CURRENT_PAGE);
            });

            setUpOffersList();
        }
    }

    private void setUpOffersList() {
        binding.recyclerViewOffers.setLayoutManager(new LinearLayoutManager(getActivity()));
        offersAdapter = new OffersAdapter(getActivity());
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchOffers((Activity) mContext, jsonObject);
    }


    @Override
    public int getBindingVariable() {
        return BR.offerFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_offer;
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
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
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
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }


}