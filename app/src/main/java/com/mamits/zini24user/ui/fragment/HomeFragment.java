package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.BannerListItem;
import com.mamits.zini24user.data.model.home.CategoryListItem;
import com.mamits.zini24user.data.model.home.HomeDataModel;
import com.mamits.zini24user.data.model.home.TransportModel;
import com.mamits.zini24user.data.model.product.ProductDataModel;
import com.mamits.zini24user.data.model.store.StoreListItem;
import com.mamits.zini24user.databinding.FragmentHomeBinding;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.adapter.BannerPagerAdapter;
import com.mamits.zini24user.ui.adapter.CategoryAdapter;
import com.mamits.zini24user.ui.adapter.KioskAdapter;
import com.mamits.zini24user.ui.adapter.LatestServicesAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.HomeFragmentNavigator;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.fragment.HomeFragmentViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentViewModel> implements HomeFragmentNavigator, View.OnClickListener {

    private final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private Gson mGson;
    @Inject
    HomeFragmentViewModel mViewModel;
    private Context mContext;
    private CategoryAdapter categoryAdapter;
    private KioskAdapter kioskAdapter;
    private List<CategoryListItem> catList;
    private List<ProductDataModel> latestServicesList;

    @Override
    public HomeFragmentViewModel getMyViewModel() {
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

            DashboardActivity activity = ((DashboardActivity) mContext);
            if (activity != null) {
                if (activity.isListenerNull()){
                    activity.updateLocation(this::setUpSlider);
                }else {
                    setUpSlider(mViewModel.getmDataManger().getLatitude(),mViewModel.getmDataManger().getLongitude());

                }

            }
            new ClickShrinkEffect(binding.btnViewAllCat);
            new ClickShrinkEffect(binding.btnViewAllTopKiosk);
            new ClickShrinkEffect(binding.serviceLayout.btnViewAllServices);
            binding.btnViewAllCat.setOnClickListener(this);
            binding.btnViewAllTopKiosk.setOnClickListener(this);
            binding.serviceLayout.btnViewAllServices.setOnClickListener(this);
        }
    }

    private void setUpSlider(double latitude, double longitude) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("userid", mViewModel.getmDataManger().getCurrentUserId());
            jsonObject.put("latitude", latitude != 0 ? latitude: "24.5695588");
            jsonObject.put("longitude", longitude != 0 ? longitude : "80.8645887");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.loadBanner((Activity) mContext, jsonObject);
    }


    @Override
    public int getBindingVariable() {
        return BR.homeFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        TransportModel model = new TransportModel();
        switch (v.getId()) {
            case R.id.btn_view_all_top_kiosk:
                Navigation.findNavController(((DashboardActivity) mContext)
                        .findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_nearby);
                break;
            case R.id.btn_view_all_cat:
                model.setCategoryList(catList);
                bundle.putSerializable("transportModel", model);
                bundle.putString("desc", "All categories available for you.");
                Navigation.findNavController(v).navigate(R.id.nav_all_subcategory, bundle);
                break;
            case R.id.btn_view_all_services:
                model.setProductList(latestServicesList);
                bundle.putSerializable("transportModel", model);
                bundle.putString("desc", "Check all the latest services from here.");
                Navigation.findNavController(v).navigate(R.id.nav_all_subcategory, bundle);
                break;
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
    public void onSuccessBanner(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type homeData = new TypeToken<HomeDataModel>() {
            }.getType();
            HomeDataModel homeDataModel = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), homeData);

            setUpTopBanner(homeDataModel.getBannerlist());
            setUpBottomBanner(homeDataModel.getBottom_bannerlist());
            setUpCategoryList(homeDataModel.getCategorylist());
            setUpServicesList(homeDataModel.getProductlist());
            setUpKioskList(homeDataModel.getStorelist());

        }
    }

    private void setUpTopBanner(List<BannerListItem> bannerlist) {
        /*top banner*/
        BannerPagerAdapter adapter = new BannerPagerAdapter(mContext, getChildFragmentManager(), bannerlist);
        binding.viewpagerBanner.setAdapter(adapter);
        binding.dotsIndicator.setViewPager(binding.viewpagerBanner);
        binding.viewpagerBanner.startAutoScroll(5000);
    }

    private void setUpBottomBanner(List<BannerListItem> bannerlist) {
        /*bottom banner*/
        BannerPagerAdapter adapter = new BannerPagerAdapter(mContext, getChildFragmentManager(), bannerlist);
        binding.bottomViewpagerBanner.setAdapter(adapter);
        binding.bottomDotsIndicator.setViewPager(binding.bottomViewpagerBanner);
        binding.bottomViewpagerBanner.startAutoScroll(5000);
    }

    private void setUpKioskList(List<StoreListItem> storeList) {
        binding.recyclerViewTopKiosk.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (storeList.size()>4){
            kioskAdapter = new KioskAdapter(getActivity(), storeList.subList(0, 4),-1);
        }else {
            kioskAdapter = new KioskAdapter(getActivity(), storeList,-1);
        }
        binding.recyclerViewTopKiosk.setAdapter(kioskAdapter);
    }

    private void setUpCategoryList(List<CategoryListItem> categorylist) {
        catList = categorylist;
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(getActivity(), catList);
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void setUpServicesList(List<ProductDataModel> servicesList) {
        latestServicesList = servicesList;
        binding.serviceLayout.recyclerViewServices.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        LatestServicesAdapter latestServicesAdapter = new LatestServicesAdapter(mContext, servicesList);
        binding.serviceLayout.recyclerViewServices.setAdapter(latestServicesAdapter);
    }

}