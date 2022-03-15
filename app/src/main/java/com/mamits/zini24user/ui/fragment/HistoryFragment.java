package com.mamits.zini24user.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.TimerObj;
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.databinding.FragmentHistoryBinding;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.adapter.OrderHistoryAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.HistoryFragmentNavigator;
import com.mamits.zini24user.ui.utils.commonClasses.TimeUtils;
import com.mamits.zini24user.viewmodel.fragment.HistoryFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding, HistoryFragmentViewModel>
        implements HistoryFragmentNavigator, View.OnClickListener, OrderHistoryAdapter.onItemClickListener {

    private String TAG = "HistoryFragment";
    private FragmentHistoryBinding binding;

    @Inject
    HistoryFragmentViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private List<OrdersDataModel> ordersList;
    private OrderHistoryAdapter orderHistoryAdapter;
    private final int LIMIT = 10;
    private int START_PAGE = 0;
    private int CURRENT_PAGE = START_PAGE;
    private CountDownTimer timer;
    private int position = 0;

    @Override
    public HistoryFragmentViewModel getMyViewModel() {
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
            binding.btnNext.setOnClickListener(view1 -> {
                CURRENT_PAGE++;
                loadOrderHistory(CURRENT_PAGE);
            });
            permission();

            binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab != null) {
                        position = tab.getPosition();
                    }
                    if (orderHistoryAdapter != null) {
                        orderHistoryAdapter.clearList();
                    }
                    assert tab != null;
                    if (tab.getPosition() == 0) {
                        /*new order*/
                        mViewModel.getmNavigator().get().showProgressBars();
                        callNewOrderApi();
                    } else {

                        CURRENT_PAGE = START_PAGE;
                        loadOrderHistory(CURRENT_PAGE);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (tab != null) {
                        position = tab.getPosition();
                    }
                    if (orderHistoryAdapter != null) {
                        orderHistoryAdapter.clearList();
                    }
                    assert tab != null;
                    if (tab.getPosition() == 0) {
                        /*new order*/
                        mViewModel.getmNavigator().get().showProgressBars();
                        callNewOrderApi();
                    } else {
                        CURRENT_PAGE = START_PAGE;
                        loadOrderHistory(CURRENT_PAGE);
                    }
                }
            });

            setUpOrderHistoryList();
        }
    }

    private void callNewOrderApi() {
        mViewModel.fetchNewOrder((Activity) mContext);
    }

    private void setUpOrderHistoryList() {
        binding.recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), this);
        binding.recyclerViewOrderHistory.setAdapter(orderHistoryAdapter);
        mViewModel.getmNavigator().get().showProgressBars();
        callNewOrderApi();


    }

    private void permission() {
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(mContext, "Permission granted, Please download", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "This permission is required to download the file.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadOrderHistory(int current_page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start", current_page);
            jsonObject.put("pagelength", LIMIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchOrderHistory((Activity) mContext, jsonObject);
    }


    @Override
    public int getBindingVariable() {
        return BR.historyFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
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
    public void onSuccessOrderHistory(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type orderDataList = new TypeToken<List<OrdersDataModel>>() {
                }.getType();
                ordersList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);
                if (jsonObject.get("next").getAsBoolean()) {
                    binding.btnNext.setVisibility(View.VISIBLE);
                } else {
                    binding.btnNext.setVisibility(View.GONE);
                }
                for (int i = 0; i < ordersList.size(); i++) {
                    if (ordersList.get(i).getStatus() == 2) {
                        String currDateTime = "";
                        try {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            currDateTime = simpleDateFormat.format(c.getTime());
                        } catch (Exception e) {
                            currDateTime = "";
                        }

                        String estimatedDateTime = new TimeUtils().addHrMinuteToDateStr(
                                ordersList.get(i).getAccepted_at(),
                                ordersList.get(i).getTime_type().equals("hour"),
                                ordersList.get(i).getOrder_completion_time());

                        TimerObj timerObj = new TimeUtils().checkTimeDifference(currDateTime, estimatedDateTime);

                        ordersList.get(i).setTimerObj(timerObj);
                    }
                }
                orderHistoryAdapter.setList(ordersList);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessNewOrder(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type orderDataList = new TypeToken<List<OrdersDataModel>>() {
                }.getType();
                ordersList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);
                binding.btnNext.setVisibility(View.GONE);

                for (int i = 0; i < ordersList.size(); i++) {
                    if (ordersList.get(i).getStatus() == 2) {
                        String currDateTime = "";
                        try {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            currDateTime = simpleDateFormat.format(c.getTime());
                        } catch (Exception e) {
                            currDateTime = "";
                        }

                        String estimatedDateTime = new TimeUtils().addHrMinuteToDateStr(
                                ordersList.get(i).getAccepted_at(),
                                ordersList.get(i).getTime_type().equals("hour"),
                                ordersList.get(i).getOrder_completion_time());

                        TimerObj timerObj = new TimeUtils().checkTimeDifference(currDateTime, estimatedDateTime);

                        ordersList.get(i).setTimerObj(timerObj);
                    }
                }
                orderHistoryAdapter.refreshAdapter(ordersList);
                startTimer();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessSaveRating(JsonObject jsonObject, int pos, float rating) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {

                orderHistoryAdapter.refreshAdapter(rating, pos);
//                startTimer();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (position != 0) {
                    timer.cancel();
                }
            }

            public void onFinish() {
                if (position == 0) {
                    callNewOrderApi();
                }
            }
        };
        timer.start();
    }


    @Override
    public void downloadFile(String url) {
        if (ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            String title = URLUtil.guessFileName(url, null, null);
            request.setTitle(title);
            request.setDescription("Downloading...");
            String cookie = CookieManager.getInstance().getCookie(url);
            request.addRequestHeader("cookie", cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(mContext, "Downloading...", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void saveRating(int position, OrdersDataModel ordersDataModel) {
        mViewModel.saveRating((Activity) mContext, ordersDataModel.getId(), ordersDataModel.getStore_id(), ordersDataModel.getMyrating(), position);
    }

    @Override
    public void makePayment(OrdersDataModel obj) {
        if (obj.getPayment_status() == 1) {
            Toast.makeText(mContext, "You have already paid for this order!", Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("model", obj);
            Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_payment_summary, bundle);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}