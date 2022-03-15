package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.product.ProductDataModel;
import com.mamits.zini24user.databinding.FragmentServicesBinding;
import com.mamits.zini24user.ui.adapter.ServicesAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.ServicesFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.ServicesFragmentViewModel;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class ServicesFragment extends BaseFragment<FragmentServicesBinding, ServicesFragmentViewModel> implements ServicesFragmentNavigator, View.OnClickListener {

    private final String TAG = "ServicesFragment";
    private FragmentServicesBinding binding;
    private Gson mGson;
    @Inject
    ServicesFragmentViewModel mViewModel;
    private Context mContext;
    private ServicesAdapter servicesAdapter;
    private int cat_id, sub_cat_id, store_id;
    private boolean fromStore;

    @Override
    public ServicesFragmentViewModel getMyViewModel() {
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
                fromStore = bundle.getBoolean("fromStore", false);
                cat_id = bundle.getInt("cat_id", -1);
                sub_cat_id = bundle.getInt("sub_cat_id", -1);
                store_id = bundle.getInt("store_id", -1);

                String name = bundle.getString("name","");
                String desc = bundle.getString("desc","");
                binding.txtCatName.setText(name);
                binding.txtDes.setText(desc);

                if (sub_cat_id != -1) {
                    loadServices();
                } else {
                    //load services by store
                    loadServicesByStore();
                }

            }

        }
    }

    private void loadServices() {
        mViewModel.fetchServices((Activity) mContext, cat_id, sub_cat_id);
    }

    private void loadServicesByStore() {
        mViewModel.fetchServicesByStore((Activity) mContext, cat_id, store_id);
    }

    @Override
    public int getBindingVariable() {
        return BR.servicesFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_services;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
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
    public void onSuccessServices(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type list = new TypeToken<List<ProductDataModel>>() {
                }.getType();
                List<ProductDataModel> servicesList = mGson.fromJson(jsonObject.get("data").getAsJsonObject().get("product_list").getAsJsonArray().toString(), list);

                setUpServicesList(servicesList);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessServicesByStore(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type list = new TypeToken<List<ProductDataModel>>() {
                }.getType();
                List<ProductDataModel> servicesList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), list);

                setUpServicesList(servicesList);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpServicesList(List<ProductDataModel> servicesList) {
        binding.recyclerViewServices.setLayoutManager(new LinearLayoutManager(mContext));
        servicesAdapter = new ServicesAdapter(mContext, servicesList,fromStore,store_id);
        binding.recyclerViewServices.setAdapter(servicesAdapter);
    }
}