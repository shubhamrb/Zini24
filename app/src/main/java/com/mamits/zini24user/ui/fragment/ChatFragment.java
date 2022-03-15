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
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.databinding.FragmentChatBinding;
import com.mamits.zini24user.ui.adapter.ChatsAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.ChatFragmentNavigator;
import com.mamits.zini24user.viewmodel.fragment.ChatFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatFragmentViewModel> implements ChatFragmentNavigator, View.OnClickListener {

    private String TAG = "ChatFragment";
    private FragmentChatBinding binding;
    private final int LIMIT = 10;
    private int START_PAGE = 0;
    private int CURRENT_PAGE = START_PAGE;
    @Inject
    ChatFragmentViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private ChatsAdapter chatsAdapter;

    @Override
    public ChatFragmentViewModel getMyViewModel() {
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
                loadChats(CURRENT_PAGE);
            });

            setUpChatList();
        }
    }

    private void setUpChatList() {
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatsAdapter = new ChatsAdapter(getActivity());
        binding.recyclerViewChat.setAdapter(chatsAdapter);

        loadChats(CURRENT_PAGE);
    }

    private void loadChats(int current_page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start", current_page);
            jsonObject.put("pagelength", LIMIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchChats((Activity) mContext, jsonObject);
    }


    @Override
    public int getBindingVariable() {
        return BR.homeFragmentView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
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
    public void onSuccessChats(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type orderDataList = new TypeToken<List<OrdersDataModel>>() {
                }.getType();
                List<OrdersDataModel> inboxList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);

                if (jsonObject.get("next").getAsBoolean()) {
                    binding.btnNext.setVisibility(View.VISIBLE);
                } else {
                    binding.btnNext.setVisibility(View.GONE);
                }
                chatsAdapter.setList(inboxList);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }


}