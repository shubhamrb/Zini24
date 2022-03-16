package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.SubcategoryListItem;
import com.mamits.zini24user.data.model.store.StoreDetailModel;
import com.mamits.zini24user.databinding.FragmentStoreDetailBinding;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.adapter.BannerPagerAdapter;
import com.mamits.zini24user.ui.adapter.KioskAdapter;
import com.mamits.zini24user.ui.adapter.SubCategoryAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.StoreDetailFragmentNavigator;
import com.mamits.zini24user.ui.utils.commonClasses.CommonUtils;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.fragment.StoreDetailFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class StoreDetailFragment extends BaseFragment<FragmentStoreDetailBinding, StoreDetailFragmentViewModel> implements StoreDetailFragmentNavigator, View.OnClickListener {

    private String TAG = "StoreDetailFragment";
    private FragmentStoreDetailBinding binding;

    @Inject
    StoreDetailFragmentViewModel mViewModel;
    private Context mContext;
    private KioskAdapter kioskAdapter;
    private Gson mGson;
    private int store_id=-1;
    private int product_id = -1;
    private double latitude, longitude;
    private boolean fromStore;

    @Override
    public StoreDetailFragmentViewModel getMyViewModel() {
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

            Bundle bundle = getArguments();
            if (bundle != null) {
                fromStore = bundle.getBoolean("fromStore", false);
                store_id = bundle.getInt("store_id", -1);
                if (store_id != -1) {
                    callStoreDetail(store_id);
                }
                product_id = bundle.getInt("product_id", -1);
                if (product_id != -1) {
                    //show proceed button
                    binding.textProceed.setVisibility(View.VISIBLE);
                    binding.btnSlider.setVisibility(View.VISIBLE);
                } else {
                    //hide proceed button
                    binding.btnSlider.setVisibility(View.GONE);
                    binding.textProceed.setVisibility(View.GONE);
                }
            }
            binding.btnSlider.setAnimDuration(300);
            binding.btnSlider.setOnSlideCompleteListener(slideToActView -> {
                binding.textProceed.setVisibility(View.GONE);
                binding.btnSlider.resetSlider();
                goToInstruction(store_id, product_id);
            });

            binding.btnSlider.setOnSlideResetListener(slideToActView -> {
                binding.textProceed.setVisibility(View.VISIBLE);
            });
            binding.textProceed.setOnClickListener(view1 -> {
                goToInstruction(store_id, product_id);
            });

            binding.btnShare.setOnClickListener(view1 -> {
                if (store_id != -1) {
                    var url = "https://zini24.com/stores/" + store_id;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(intent, "Select"));
                }
            });

        }
    }

    private void goToInstruction(int store_id, int product_id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("store_id", store_id);
        bundle.putInt("product_id", product_id);
        Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment))
                .navigate(R.id.nav_instruction, bundle);
    }

    private void callStoreDetail(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("latitude", latitude != 0 ? latitude : "24.5695588");
            jsonObject.put("longitude", longitude != 0 ? longitude : "80.8645887");
            jsonObject.put("store_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchStoreDetail((Activity) mContext, jsonObject);
    }

    @Override
    public int getBindingVariable() {
        return BR.storeDetailView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_store_detail;
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
    public void onSuccessStoreDetail(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type storeData = new TypeToken<StoreDetailModel>() {
            }.getType();
            StoreDetailModel storeDetail = mGson.fromJson(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().toString(), storeData);

            setData(storeDetail);
        }
    }

    private void setUpTopBanner(List<String> bannerlist) {
        /*top banner*/
        BannerPagerAdapter adapter = new BannerPagerAdapter(mContext, bannerlist, getChildFragmentManager());
        binding.viewpagerBanner.setAdapter(adapter);
        binding.viewpagerBanner.startAutoScroll(5000);
    }

    private void setData(StoreDetailModel storeDetail) {
        binding.txtStoreName.setText(storeDetail.getName());
        binding.txtDest.setText(storeDetail.getDistance() + " km away from you");
        binding.txtDesc.setText(storeDetail.getDescription());
        /*Glide.with(mContext).asBitmap().load(storeDetail.getStorelogo()).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.imageStore);*/
        setUpTopBanner(storeDetail.getBanner_image());
        switch (storeDetail.getIs_available()) {
            case 0:
                binding.txtStatus.setText("Closed");
                binding.imgStatus.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.circle_red));
                break;
            case 1:
                binding.txtStatus.setText("Open now");
                binding.imgStatus.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.circle_green));
                break;
        }
        binding.txtRating.setText(String.format(Locale.getDefault(), "%d", storeDetail.getRatting()));
        binding.ratingbarRating.setRating(storeDetail.getRatting());
        binding.txtTotalRating.setText(String.format(Locale.getDefault(), "%d Ratings", storeDetail.getTotalrating()));
        setUpCategoryList(storeDetail.getCategory(), storeDetail.getId());
    }

    private void setUpCategoryList(List<SubcategoryListItem> subcategories, int store_id) {
        int mNoOfColumns =
                CommonUtils.calculateNoOfColumns(mContext, 100);
        GridLayoutManager manager = new GridLayoutManager(mContext, mNoOfColumns, GridLayoutManager.VERTICAL, false);

        binding.recyclerViewSubCategory.setLayoutManager(manager);
        SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(mContext, subcategories, -1, store_id, fromStore);
        binding.recyclerViewSubCategory.setAdapter(categoryAdapter);
    }
}