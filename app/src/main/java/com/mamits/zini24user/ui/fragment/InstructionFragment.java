package com.mamits.zini24user.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.form.CustomFieldObject;
import com.mamits.zini24user.data.model.form.FormDataModel;
import com.mamits.zini24user.data.model.orders.OrderDetailDataModel;
import com.mamits.zini24user.databinding.FragmentInstructionBinding;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.adapter.FormAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.FormFragmentNavigator;
import com.mamits.zini24user.ui.utils.constants.AppConstant;
import com.mamits.zini24user.viewmodel.fragment.FormFragmentViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InstructionFragment extends BaseFragment<FragmentInstructionBinding, FormFragmentViewModel>
        implements FormFragmentNavigator, View.OnClickListener {

    private String TAG = "InstructionFragment";
    private FragmentInstructionBinding binding;

    @Inject
    FormFragmentViewModel mViewModel;
    private Context mContext;
    private FormAdapter formAdapter;
    private Gson mGson;
    private int product_id = -1, store_id = -1;
    private int mSelectedPos = -1;
    private int mSelectedLocPos = -1;
    private List<CustomFieldObject> customFormList = new ArrayList<>();
    private String mPictureType = "";
    private boolean isCheckedTnc = false;
    private FormDataModel formDataModel;

    @Override
    public FormFragmentViewModel getMyViewModel() {
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
                store_id = bundle.getInt("store_id", -1);
                product_id = bundle.getInt("product_id", -1);
                callProductDetail();

                binding.textProceed.setOnClickListener(this);
                new ClickShrinkEffect(binding.textProceed);
            }
        }
    }

    private void callProductDetail() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("store_id", store_id);
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.fetchProductDetail((Activity) mContext, jsonObject);
    }

    @Override
    public int getBindingVariable() {
        return BR.instructionView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_instruction;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_proceed:
                Bundle bundle = new Bundle();
                bundle.putSerializable("form", formDataModel);
                Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment))
                        .navigate(R.id.nav_form, bundle);
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
    public void onSuccessProductDetail(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type formData = new TypeToken<FormDataModel>() {
            }.getType();

            formDataModel = mGson.fromJson(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().toString(), formData);

            binding.textTitle.setText(formDataModel.getDescription());
            binding.textDesc.setText(formDataModel.getShort_description());

            if (formDataModel.getProduct_type() == 1) {
                List<String> dropdownItemList = new ArrayList<>();
                boolean isAllNullPrice = true;
                for (OrderDetailDataModel it : formDataModel.getVariation_price()) {
                    if (it.getValue() != null) {
                        isAllNullPrice = false;
                    }
                    dropdownItemList.add(it.getName() + "\\u20B9" + (it.getValue() != null ? it.getValue() : " Best price after acceptance "));
                }

                if (isAllNullPrice) {
                    binding.textPrice.setVisibility(View.VISIBLE);
                    binding.spnOpt.setVisibility(View.GONE);
                    binding.textSpnTitle.setText("Price");
                    binding.textPrice.setText("After order Confirmation Get Best Price");
                    formDataModel.setSelectedPrice("0.00");
                } else {
                    binding.textPrice.setVisibility(View.GONE);
                    binding.spnOpt.setVisibility(View.VISIBLE);
                    binding.textSpnTitle.setText("Select Price");
                    ArrayAdapter adapter = new ArrayAdapter(
                            mContext,
                            android.R.layout.simple_spinner_item,
                            dropdownItemList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spnOpt.setAdapter(adapter);
                    binding.spnOpt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                            if (formDataModel.getVariation_price().get(pos).getValue() != null) {
                                formDataModel.setSelectedPrice(formDataModel.getVariation_price().get(pos).getValue());
                            } else {
                                formDataModel.setSelectedPrice("0.00");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            } else {
                binding.textPrice.setVisibility(View.VISIBLE);
                binding.spnOpt.setVisibility(View.GONE);
                binding.textSpnTitle.setText("Price");
                binding.textPrice.setText((formDataModel.getPrice().isEmpty()
                        || formDataModel.getPrice().equals("0.00")) ? "After order Confirmation Get Best Price" : formDataModel.getPrice());
            }
        }
    }


    @Override
    public void onSuccessFileUpload(JsonObject jsonObject) {
    }

    @Override
    public void onSuccessPlaceOrder(JsonObject jsonObject) {
    }

}