package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.CategoryListItem;
import com.mamits.zini24user.data.model.home.SubcategoryListItem;
import com.mamits.zini24user.data.model.home.TransportModel;
import com.mamits.zini24user.data.model.product.ProductDataModel;
import com.mamits.zini24user.databinding.FragmentAllSubcategoryBinding;
import com.mamits.zini24user.ui.adapter.GridCategoryAdapter;
import com.mamits.zini24user.ui.adapter.LatestServicesAdapter;
import com.mamits.zini24user.ui.adapter.SubCategoryAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.AllSubcategoryFragmentNavigator;
import com.mamits.zini24user.ui.utils.commonClasses.CommonUtils;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.fragment.AllSubcategoryFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class AllSubcategoryFragment extends BaseFragment<FragmentAllSubcategoryBinding, AllSubcategoryFragmentViewModel> implements AllSubcategoryFragmentNavigator, View.OnClickListener {

    private final String TAG = "AllSubcategoryFragment";
    private FragmentAllSubcategoryBinding binding;
    private Gson mGson;
    @Inject
    AllSubcategoryFragmentViewModel mViewModel;
    private Context mContext;
    private SubCategoryAdapter subCategoryAdapter;
    int cat_id;

    @Override
    public AllSubcategoryFragmentViewModel getMyViewModel() {
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
                String name = bundle.getString("name");
                cat_id = bundle.getInt("cat_id", -1);
                String desc = bundle.getString("desc");
                binding.txtDes.setText(desc);

                TransportModel transportModel = (TransportModel) bundle.getSerializable("transportModel");

                if (cat_id != -1) {
                    binding.txtCatName.setText(name);
                    fetchSubCategory();
                } else {
                    if (transportModel.getCategoryList() != null) {
                        binding.txtCatName.setText("Apply online");
                        setUpCategoryList(transportModel.getCategoryList());
                    } else if (transportModel.getProductList() != null) {
                        binding.txtCatName.setText("Latest Services");
                        setUpServicesList(transportModel.getProductList());
                    }
                }

            }

        }
    }

    private void fetchSubCategory() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("category_id", cat_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchSubcategory((Activity) mContext, jsonObject);
    }

    private void setUpCategoryList(List<CategoryListItem> categoryList) {
        int mNoOfColumns =
                CommonUtils.calculateNoOfColumns(mContext, 100);
        GridLayoutManager manager = new GridLayoutManager(mContext, mNoOfColumns, GridLayoutManager.VERTICAL, false);

        binding.recyclerViewSubCategory.setLayoutManager(manager);
        GridCategoryAdapter categoryAdapter = new GridCategoryAdapter(mContext, categoryList);
        binding.recyclerViewSubCategory.setAdapter(categoryAdapter);
    }

    private void setUpSubCategoryList(List<SubcategoryListItem> subcategories) {
        int mNoOfColumns =
                CommonUtils.calculateNoOfColumns(mContext, 100);
        GridLayoutManager manager = new GridLayoutManager(mContext, mNoOfColumns, GridLayoutManager.VERTICAL, false);

        binding.recyclerViewSubCategory.setLayoutManager(manager);
        subCategoryAdapter = new SubCategoryAdapter(mContext, subcategories, cat_id, -1,false);
        binding.recyclerViewSubCategory.setAdapter(subCategoryAdapter);
    }

    private void setUpServicesList(List<ProductDataModel> productList) {
        int mNoOfColumns =
                CommonUtils.calculateNoOfColumns(mContext, 100);
        GridLayoutManager manager = new GridLayoutManager(mContext, mNoOfColumns, GridLayoutManager.VERTICAL, false);

        binding.recyclerViewSubCategory.setLayoutManager(manager);
        LatestServicesAdapter latestServicesAdapter = new LatestServicesAdapter(mContext, productList);
        binding.recyclerViewSubCategory.setAdapter(latestServicesAdapter);
    }


    @Override
    public int getBindingVariable() {
        return BR.allSubcategoryFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_subcategory;
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
    public void onSuccessSubcategory(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type homeData = new TypeToken<List<SubcategoryListItem>>() {
            }.getType();
            List<SubcategoryListItem> storeList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), homeData);

            setUpSubCategoryList(storeList);

        }
    }
}