package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.store.FilterDataModel;
import com.mamits.zini24user.data.model.store.StoreListItem;
import com.mamits.zini24user.databinding.FragmentNearbyBinding;
import com.mamits.zini24user.ui.adapter.FilterAdapter;
import com.mamits.zini24user.ui.adapter.KioskAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.navigator.fragment.NearbyFragmentNavigator;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.fragment.NearbyFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class NearbyFragment extends BaseFragment<FragmentNearbyBinding,
        NearbyFragmentViewModel> implements NearbyFragmentNavigator, View.OnClickListener, FilterAdapter.onClickListener {

    private String TAG = "NearbyFragment";
    private FragmentNearbyBinding binding;

    @Inject
    NearbyFragmentViewModel mViewModel;
    private Context mContext;
    private KioskAdapter kioskAdapter;
    private Gson mGson;
    private int product_id = -1;
    private double latitude, longitude;
    private List<StoreListItem> storeList;
    private final int START_PAGE = 0;
    private int CURRENT_PAGE = START_PAGE;
    private final int LIMIT = 15;
    private Bundle bundle;
    private BottomSheetDialog filterDialog;
    private List<String> locationList;
    private List<String> priceList;
    private List<String> ratingList;
    private boolean isLocationVisible = true, isPriceVisible = false, isRatingVisible = false;
    private FilterDataModel filterDataModel;

    @Override
    public NearbyFragmentViewModel getMyViewModel() {
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


            bundle = getArguments();
            if (bundle != null) {
                product_id = bundle.getInt("product_id", -1);
            }

            binding.btnNext.setOnClickListener(view1 -> {
                CURRENT_PAGE++;
                if (bundle != null) {
                    callStoreByProduct(CURRENT_PAGE);
                } else {
                    loadNearbyStores(CURRENT_PAGE);
                }
            });

            setUpKioskList();

            binding.btnFilter.setOnClickListener(view1 -> {
                showFilterDialog();
            });
        }
    }

    private void showFilterDialog() {
        if (filterDialog == null) {
            filterDataModel = new FilterDataModel();
            filterDialog = new BottomSheetDialog(mContext);
            filterDialog.setContentView(R.layout.filter_bottomsheet);
            filterDialog.setCanceledOnTouchOutside(false);

            RecyclerView rv_location_filter = filterDialog.findViewById(R.id.rv_location_filter);
            RecyclerView rv_price_filter = filterDialog.findViewById(R.id.rv_price_filter);
            RecyclerView rv_rating_filter = filterDialog.findViewById(R.id.rv_rating_filter);

            RelativeLayout rl_location = filterDialog.findViewById(R.id.rl_location);
            RelativeLayout rl_price = filterDialog.findViewById(R.id.rl_price);
            RelativeLayout rl_rating = filterDialog.findViewById(R.id.rl_rating);

            CustomTextView back = filterDialog.findViewById(R.id.back);
            CustomTextView done = filterDialog.findViewById(R.id.done);
            isLocationVisible = true;
            isPriceVisible = false;
            isRatingVisible = false;

            locationList = new ArrayList<>();
            priceList = new ArrayList<>();
            ratingList = new ArrayList<>();

            locationList.add("All");
            locationList.add("Within 5km");
            locationList.add("Within 10km");
            locationList.add("Within 15km");

            priceList.add("All");
            priceList.add("Low to high");
            priceList.add("High to low");

            ratingList.add("All");
            ratingList.add("No rating");
            ratingList.add("1 star");
            ratingList.add("2 star");
            ratingList.add("3 star");
            ratingList.add("4 star");
            ratingList.add("5 star");

            back.setOnClickListener(view -> {
                if (filterDialog != null) {
                    filterDialog.dismiss();
                    filterDialog = null;
                }
            });

            FilterAdapter locationAdapter = new FilterAdapter(mContext, locationList, this, "location");
            FlexboxLayoutManager locationManager = new FlexboxLayoutManager(mContext);
            locationManager.setFlexDirection(FlexDirection.ROW);
            assert rv_location_filter != null;
            rv_location_filter.setLayoutManager(locationManager);
            rv_location_filter.setAdapter(locationAdapter);

            FilterAdapter priceAdapter = new FilterAdapter(mContext, priceList, this, "price");
            FlexboxLayoutManager priceManager = new FlexboxLayoutManager(mContext);
            priceManager.setFlexDirection(FlexDirection.ROW);
            assert rv_price_filter != null;
            rv_price_filter.setLayoutManager(priceManager);
            rv_price_filter.setAdapter(priceAdapter);

            FilterAdapter ratingAdapter = new FilterAdapter(mContext, ratingList, this, "rating");
            FlexboxLayoutManager ratingManager = new FlexboxLayoutManager(mContext);
            ratingManager.setFlexDirection(FlexDirection.ROW);
            assert rv_rating_filter != null;
            rv_rating_filter.setLayoutManager(ratingManager);
            rv_rating_filter.setAdapter(ratingAdapter);

            rl_location.setOnClickListener(view -> {
                if (isLocationVisible) {
                    rv_location_filter.setVisibility(View.GONE);
                    isLocationVisible = false;
                } else {
                    rv_location_filter.setVisibility(View.VISIBLE);
                    isLocationVisible = true;
                }
            });

            rl_price.setOnClickListener(view -> {
                if (isPriceVisible) {
                    rv_price_filter.setVisibility(View.GONE);
                    isPriceVisible = false;
                } else {
                    rv_price_filter.setVisibility(View.VISIBLE);
                    isPriceVisible = true;
                }
            });

            rl_rating.setOnClickListener(view -> {
                if (isRatingVisible) {
                    rv_rating_filter.setVisibility(View.GONE);
                    isRatingVisible = false;
                } else {
                    rv_rating_filter.setVisibility(View.VISIBLE);
                    isRatingVisible = true;
                }
            });

            done.setOnClickListener(view -> {
                if (filterDialog != null) {
                    filterDialog.dismiss();
                    filterDialog = null;
                    applyFilter();
                }
            });

            filterDialog.setOnDismissListener(dialog -> {
                filterDialog = null;
            });
            filterDialog.show();
        }
    }

    private void applyFilter() {
        if (filterDataModel != null && storeList != null && storeList.size() > 0) {

            List<StoreListItem> filteredList = new ArrayList<>(storeList);

            for (StoreListItem it : storeList) {
                if (!filterDataModel.getMlocation().equals("All")) {
                    var dist = 0f;
                    if (it.getDistance() != null) {
                        dist = Float.parseFloat(it.getDistance());
                    }
                    switch (filterDataModel.getMlocation()) {
                        case "Within 5km":
                            if (dist > 5) {
                                filteredList.remove(it);
                            }
                            break;
                        case "Within 10km":
                            if (dist > 10) {
                                filteredList.remove(it);
                            }
                            break;
                        case "Within 15km":
                            if (dist > 15) {
                                filteredList.remove(it);
                            }
                            break;
                    }
                }
                if (!filterDataModel.getMrating().equals("All")) {
                    var rating = 0f;
                    rating = it.getRatting();

                    switch (filterDataModel.getMrating()) {
                        case "No rating":
                            if (rating != 0f) {
                                filteredList.remove(it);
                            }
                            break;
                        case "1 star":
                            if (rating > 2f || rating <= 0f) {
                                filteredList.remove(it);
                            }
                            break;
                        case "2 star":
                            if (rating < 2f || rating > 2.9f) {
                                filteredList.remove(it);
                            }
                            break;
                        case "3 star":
                            if (rating < 3 || rating > 3.9f) {
                                filteredList.remove(it);
                            }
                            break;
                        case "4 star":
                            if (rating < 4 || rating > 4.9f) {
                                filteredList.remove(it);
                            }
                            break;
                        case "5 star":
                            if (rating != 5f) {
                                filteredList.remove(it);
                            }
                            break;
                    }

                }
            }

            if (!filterDataModel.getMprice().equals("All")) {
                switch (filterDataModel.getMprice()) {
                    case "Low to high":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Collections.sort(filteredList, Comparator.comparingDouble(StoreListItem::getPrice));
                        } else {
                            Collections.sort(filteredList, (m1, m2) -> Double.compare(m1.getPrice(), m2.getPrice()));
                        }
                        break;
                    case "High to low":
                        Collections.sort(filteredList, (m1, m2) -> Double.compare(m2.getPrice(), m1.getPrice()));
                        break;
                }
            }
            if (filteredList.size() == 0) {
                binding.btnNext.setVisibility(View.GONE);
            }

            kioskAdapter.sortList(filteredList);
        }

    }


    private void callStoreByProduct(int current_page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("latitude", latitude != 0 ? latitude : "24.5695588");
            jsonObject.put("longitude", longitude != 0 ? longitude : "80.8645887");
            jsonObject.put("product_id", product_id);
            jsonObject.put("start", current_page);
            jsonObject.put("pagelength", LIMIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchStoreByProduct((Activity) mContext, jsonObject);
    }

    private void loadNearbyStores(int current_page) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("latitude", latitude != 0 ? latitude : "24.5695588");
            jsonObject.put("longitude", longitude != 0 ? longitude : "80.8645887");
            jsonObject.put("start", current_page);
            jsonObject.put("pagelength", LIMIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchNearbyStores((Activity) mContext, jsonObject);
    }

    @Override
    public int getBindingVariable() {
        return BR.nearbyFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nearby;
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
    public void onSuccessNearbyStores(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type homeData = new TypeToken<List<StoreListItem>>() {
            }.getType();
            storeList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), homeData);

            if (product_id != -1) {
                binding.labelTopKiosk.setText(String.format(Locale.getDefault(), "Search Result (%d)", storeList.size()));
            } else {
            }
            binding.labelTopKiosk.setVisibility(View.VISIBLE);
            if (jsonObject.get("next").getAsBoolean()) {
                binding.btnNext.setVisibility(View.VISIBLE);
            } else {
                binding.btnNext.setVisibility(View.GONE);
            }
            kioskAdapter.setList(storeList);
        }
    }

    @Override
    public void onSuccessStoresByProduct(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type homeData = new TypeToken<List<StoreListItem>>() {
            }.getType();
            storeList = mGson.fromJson(jsonObject.get("data").getAsJsonObject().get("stores").getAsJsonArray().toString(), homeData);

            if (product_id != -1) {
                binding.labelTopKiosk.setText(String.format(Locale.getDefault(), "Search Result (%d)", storeList.size()));
            }
            binding.labelTopKiosk.setVisibility(View.VISIBLE);

            if (jsonObject.get("next").getAsBoolean()) {
                binding.btnNext.setVisibility(View.VISIBLE);
            } else {
                binding.btnNext.setVisibility(View.GONE);
            }
            if (jsonObject.get("next").getAsBoolean()) {
                binding.btnNext.setVisibility(View.VISIBLE);
            } else {
                binding.btnNext.setVisibility(View.GONE);
            }
            kioskAdapter.setList(storeList);
        }
    }

    private void setUpKioskList() {
        binding.recyclerViewTopKiosk.setLayoutManager(new LinearLayoutManager(getActivity()));
        kioskAdapter = new KioskAdapter(getActivity(), product_id);
        binding.recyclerViewTopKiosk.setAdapter(kioskAdapter);

        if (bundle != null) {
            callStoreByProduct(CURRENT_PAGE);
        } else {
            loadNearbyStores(CURRENT_PAGE);
        }
    }

    @Override
    public void onClick(int position, String obj, String type) {
        switch (type) {
            case "location":
                filterDataModel.setMlocation(obj);
                break;
            case "price":
                filterDataModel.setMprice(obj);
                break;
            case "rating":
                filterDataModel.setMrating(obj);
                break;

        }
    }
}