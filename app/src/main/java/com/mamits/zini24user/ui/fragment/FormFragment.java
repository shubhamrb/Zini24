package com.mamits.zini24user.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.BuildConfig;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.form.CustomFieldObject;
import com.mamits.zini24user.data.model.form.FormDataModel;
import com.mamits.zini24user.data.model.form.FormItem;
import com.mamits.zini24user.data.model.form.PlaceOderFormData;
import com.mamits.zini24user.data.model.form.PlaceOrderRequest;
import com.mamits.zini24user.data.model.form.ValueModel;
import com.mamits.zini24user.data.model.search.SearchDataModel;
import com.mamits.zini24user.databinding.FragmentFormBinding;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.adapter.FormAdapter;
import com.mamits.zini24user.ui.base.BaseFragment;
import com.mamits.zini24user.ui.navigator.fragment.FormFragmentNavigator;
import com.mamits.zini24user.ui.utils.CameraGalleryFragment;
import com.mamits.zini24user.ui.utils.FileUtils;
import com.mamits.zini24user.ui.utils.commonClasses.NetworkUtils;
import com.mamits.zini24user.ui.utils.commonClasses.URIPathHelper;
import com.mamits.zini24user.ui.utils.listeners.OnItemClickListener;
import com.mamits.zini24user.viewmodel.fragment.FormFragmentViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormFragment extends BaseFragment<FragmentFormBinding, FormFragmentViewModel>
        implements FormFragmentNavigator, View.OnClickListener, OnItemClickListener, CameraGalleryFragment.Listener {

    private String TAG = "FormFragment";
    private FragmentFormBinding binding;

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
                formDataModel = (FormDataModel) bundle.getSerializable("form");
                store_id = formDataModel.getStore_id();
                product_id = formDataModel.getProduct_id();

                setData();
                binding.chkboxTnc.setOnCheckedChangeListener((compoundButton, b) -> {
                    isCheckedTnc = b;
                });

                binding.textProceed.setOnClickListener(this);
                binding.textTerm.setOnClickListener(this);

                new ClickShrinkEffect(binding.textProceed);
                new ClickShrinkEffect(binding.textTerm);
            }
        }
    }

    private void setData() {
        binding.labelTopKiosk.setText(formDataModel.getName());
        binding.h3.setText(formDataModel.getDescription());
        binding.labelTopKiosk.setVisibility(View.VISIBLE);
        binding.h3.setVisibility(View.VISIBLE);

        setUpKioskList(formDataModel.getForm());
    }

    @Override
    public int getBindingVariable() {
        return BR.formView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_form;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_proceed:
                String errormsg = isValidData();
                if (errormsg.isEmpty()) {
                    List<PlaceOderFormData> formData = new ArrayList<>();

                    for (CustomFieldObject customFieldObj : customFormList) {
                        formData.add(
                                new PlaceOderFormData(
                                        customFieldObj.getName(),
                                        customFieldObj.getFieldType(),
                                        customFieldObj.getIsRequired(),
                                        customFieldObj.getAnsValue(),
                                        customFieldObj.getExt()
                                ));
                    }

                    String finalOrderPrice = "";
                    if (formDataModel.getProduct_type() == 1) {
                        finalOrderPrice = formDataModel.getSelectedPrice();
                    } else {
                        finalOrderPrice = formDataModel.getPrice();
                    }
                    PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(
                            String.valueOf(store_id),
                            formData,
                            finalOrderPrice,
                            String.valueOf(product_id),
                            "form"
                    );
                    callPlaceOrder(placeOrderRequest);
                } else {
                    Toast.makeText(mContext, errormsg, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.text_term:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://zini24user.com/terms-and-conditions"));
                startActivity(browserIntent);
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
        }
    }

    private void setUpKioskList(List<FormItem> formItems) {
        if (formItems.size() > 0) {
            customFormList.clear();

            for (FormItem formItem : formItems) {
                List<String> viewControlList = new ArrayList<>();
                for (ValueModel valueModel : formItem.getValue()) {
                    List<String> hideFieldList = valueModel.getHidefield();
                    if (hideFieldList != null && hideFieldList.size() > 0) {
                        viewControlList.addAll(hideFieldList);
                    }
                }
                customFormList.add(
                        new CustomFieldObject(
                                formItem.getId(),
                                formItem.getName(),
                                formItem.getField_type(),
                                formItem.getName(),
                                "3",
                                "20",
                                formItem.getIsRequired(),
                                "",
                                formItem.getValue(),
                                viewControlList
                        ));
            }

        }
        binding.recyclerViewForm.setLayoutManager(new LinearLayoutManager(getActivity()));
        formAdapter = new FormAdapter(getActivity(), customFormList, this);
        binding.recyclerViewForm.setAdapter(formAdapter);
    }

    @Override
    public void onClick(int pos, View view, CustomFieldObject obj) {
        if (obj != null) {
            switch (view.getId()) {
                case R.id.text_file_upload:
                    mSelectedPos = pos;
                    showAttachOptions();
                    break;
                case R.id.image_file_close:
                    customFormList.get(pos).setAnsValue("");
                    formAdapter.onRefreshAdapter(customFormList, false);
                    break;
                case R.id.edt_custom_field:
                case R.id.edt_custom_field_mult:
                    if (customFormList.get(pos).getId().equals(obj.getId())) {
                        customFormList.get(pos).setAnsValue(obj.getAnsValue());
                    }
                    break;
                case R.id.btn:
                    if (obj.getFieldType().equals("location")) {
                        mSelectedPos = pos;
                        mSelectedLocPos = pos;
                        //AutoComplete place
                        if (!Places.isInitialized()) {
                            Places.initialize(
                                    requireActivity(),
                                    getString(R.string.api_key),
                                    Locale.getDefault()
                            );
                        }
                        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(requireContext());
                        startActivityForResult(intent, 1111);
                    } else if (obj.getFieldType().equals("date")) {
                        //datePicker
                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dpd = new DatePickerDialog(
                                mContext, (datePicker, y, m, d) -> {
                            customFormList.get(pos).setAnsValue(d + "/" + m + "/" + y);
                            formAdapter.onRefreshAdapter(
                                    customFormList,
                                    false
                            );
                        }, year, month, day);
                        dpd.show();
                    }
                    break;
            }
        }
    }

    private void callPlaceOrder(PlaceOrderRequest placeOrderRequest) {
        if (isCheckedTnc) {
            mViewModel.placeOrder((Activity) mContext, placeOrderRequest);
        } else {
            Toast.makeText(mContext, "Please agree on terms & conditions", Toast.LENGTH_SHORT).show();
        }
    }

    private String isValidData() {
        String errorMsg = "";
        for (CustomFieldObject customFieldObj : customFormList) {
            if (customFieldObj.getFieldType().equals("file") && !customFieldObj.getAnsValue().isEmpty()) {
                if (!(customFieldObj.getAnsValue().startsWith("http://")) && !(customFieldObj.getAnsValue().startsWith("https://"))) {
                    return "The file didn't uploaded correctly, please try again";
                }
            }
            if (customFieldObj.getIsRequired().equals("Yes") && (customFieldObj.getAnsValue().isEmpty() && (customFieldObj.isVisible()))
            ) {
                return customFieldObj.getName();
            }
        }
        return errorMsg;
    }

    private void showAttachOptions() {
        CameraGalleryFragment instance = new CameraGalleryFragment(this, mContext);
        instance.show(getChildFragmentManager(), "camera_gallery");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1111:
                    if (data != null) {
                        Place place = Autocomplete.getPlaceFromIntent(data);
                        if (place.getLatLng() != null) {
                            customFormList.get(mSelectedPos).setAnsValue(place.getName());
                            formAdapter.onRefreshAdapter(
                                    customFormList,
                                    false
                            );
                        }
                    }
                    break;
                case 600:
                    String image = FileUtils.mCurrentPhotoPath;
                    try {
                        if (formAdapter != null) {
                            customFormList.get(mSelectedPos).setAnsValue(image);

                            formAdapter.onRefreshAdapter(
                                    customFormList,
                                    false);

                            callUploadLkDocuments(
                                    customFormList.get(mSelectedPos).getName(),
                                    customFormList.get(mSelectedPos).getAnsValue(),
                                    mSelectedPos
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 700:
                    try {
                        Uri contentStr;
                        if (data != null) {
                            contentStr = data.getData();
                            if (contentStr != null) {
                                String extension = URIPathHelper.getFileExtension(mContext, contentStr);
                                String fileRealPath = FileUtils.getPath(mContext, contentStr);
                                try {

                                    if (extension.equals("jpg"
                                    ) || extension.equals("jpeg"
                                    ) || extension.equals("png")
                                    ) {
                                        FileUtils.mCurrentPhotoPath = fileRealPath;
                                        if (formAdapter != null) {
                                            customFormList.get(mSelectedPos).setAnsValue(fileRealPath);
                                            formAdapter.onRefreshAdapter(
                                                    customFormList,
                                                    false
                                            );
                                            callUploadLkDocuments(
                                                    customFormList.get(mSelectedPos).getName(),
                                                    customFormList.get(mSelectedPos).getAnsValue(),
                                                    mSelectedPos
                                            );
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }

    private void callUploadLkDocuments(String docName, String mFilePath, int mSelectedPos) {
        if (NetworkUtils.isNetworkConnected(mContext)) {
            try {
                MultipartBody.Part multipartBody;
                String fileName = "";
                RequestBody requestDocs =
                        RequestBody.create(MediaType.parse("text/plain"), docName);
                if (!TextUtils.isEmpty(mFilePath)) {
                    File imageFile = new File(mFilePath);
                    String filePathName = URIPathHelper.getFileName(mContext, Uri.parse(mFilePath));

                    if (mFilePath.contains(".jpg") || mFilePath.contains(".png") || mFilePath.contains(".jpeg")) {
                        if (filePathName.contains(".jpg")) {
                            fileName = "Test.jpg";
                        } else if (filePathName.contains(".png")) {
                            fileName = "Test.png";
                        } else {
                            fileName = "Test.jpeg";
                        }
                    } else {
                        fileName =
                                mFilePath.contains(".pdf") ? "Test.pdf" :
                                        mFilePath.contains(".doc") ? "Test.doc" :
                                                mFilePath.contains(".xls") ? "Test.xls" :
                                                        mFilePath.contains(".xlsx") ? "Test.xls" :
                                                                "Test.txt";
                    }
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("mutlipart/form-data"), imageFile);
                    multipartBody =
                            MultipartBody.Part.createFormData("myfile", fileName, requestFile);


                    mViewModel.uploadFile((Activity) mContext, multipartBody, requestDocs);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSuccessFileUpload(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            try {
                JsonObject dataObject = jsonObject.get("data").getAsJsonObject();
                customFormList.get(mSelectedPos).setExt(dataObject.get("ext").getAsString());
                customFormList.get(mSelectedPos).setAnsValue(dataObject.get("url").getAsString());

                formAdapter.onRefreshAdapter(
                        customFormList,
                        false
                );

                Toast.makeText(mContext, jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccessPlaceOrder(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            try {
                Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_history);
                Toast.makeText(mContext, jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(int pos, View view, CustomFieldObject obj, String type) {

    }

    @Override
    public void onClick(int pos, View view, ValueModel obj) {
        if (obj != null) {
            switch (view.getId()) {
                case R.id.spn_text:
                    if (pos != -1 && pos < customFormList.size()) {
                        customFormList.get(pos).setAnsValue(obj.getValue());
                        List<String> visibilityControlFields = customFormList.get(pos).getVisibilityControlField();
                        for (int i = 0; i < customFormList.size(); i++) {
                            CustomFieldObject object = customFormList.get(i);
                            if (visibilityControlFields.contains(object.getId())) {
                                customFormList.get(i).setVisible(!obj.getHidefield().contains(object.getId()));
                                if (obj.getHidefield().contains(object.getId())){
                                    customFormList.get(i).setAnsValue("");
                                }
                            }
                        }
                        formAdapter.onRefreshAdapter(customFormList, true);
                    }
                    break;
                case R.id.chkbox_item:
                    String[] itemOptList = customFormList.get(pos).getAnsValue().split(",");

                    String selectedAns = getItemCheckedPos(itemOptList, obj);
                    customFormList.get(pos).setAnsValue(selectedAns);
                    if (obj.getHidefield() != null && obj.getHidefield().size() > 0) {
                        List<String> visibilityControlFields = customFormList.get(pos).getVisibilityControlField();

                        for (int i = 0; i < customFormList.size(); i++) {
                            CustomFieldObject object = customFormList.get(i);
                            if (visibilityControlFields.contains(object.getId())) {
                                customFormList.get(i).setVisible(!obj.getHidefield().contains(object.getId()));
                                if (obj.getHidefield().contains(object.getId())){
                                    customFormList.get(i).setAnsValue("");
                                }
                            }
                        }
                    }
                    formAdapter.onRefreshAdapter(
                            customFormList,
                            false
                    );
                    break;
                case R.id.rbtn_item:
                    customFormList.get(pos).setAnsValue(obj.getValue());
                    List<String> visibilityControlFields = customFormList.get(pos).getVisibilityControlField();

                    for (int i = 0; i < customFormList.size(); i++) {
                        CustomFieldObject object = customFormList.get(i);
                        if (visibilityControlFields.contains(object.getId())) {
                            customFormList.get(i).setVisible(!obj.getHidefield().contains(object.getId()));
                            if (obj.getHidefield().contains(object.getId())){
                                customFormList.get(i).setAnsValue("");
                            }
                        }
                    }
                    formAdapter.onRefreshAdapter(customFormList, false);
                    break;
            }

        }
    }

    @Override
    public void onClick(int pos, View view, SearchDataModel obj) {

    }

    private String getItemCheckedPos(String[] itemList, ValueModel obj) {
        StringBuilder ansValue = new StringBuilder();
        boolean isItemAlreadySelected = false;

        for (String s : itemList) {
            if (s.equals(obj.getValue())) {
                isItemAlreadySelected = true;
            } else {
                ansValue.append(",").append(s);
            }
        }
        if (!isItemAlreadySelected) {
            ansValue = new StringBuilder(ansValue + "," + obj.getValue());
        }
        return ansValue.toString();
    }

    @Override
    public void onCameraGalleryClicked(int position) {
        switch (position) {
            case 0:
                if (isCameraStoragePermissionGranted(mContext)) {
                    mPictureType = "CAMERA";
                    dispatchTakePictureIntent(mContext);
                } else {
                    checkCameraStoragePermissions(mContext);
                }
                break;
            case 1:
                if (isCameraStoragePermissionGranted(mContext)) {
                    mPictureType = "GALLERY";
                    choosePhotoFromGallary(mContext);
                } else {
                    checkCameraStoragePermissions(mContext);
                }
                break;
        }
    }

    private void checkCameraStoragePermissions(Context mContext) {
        ActivityCompat.requestPermissions(
                (Activity) mContext,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                200
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 200:
                if (mPictureType.equals("CAMERA")) {
                    if (isCameraStoragePermissionGranted(mContext)) {
                        mPictureType = "CAMERA";
                        dispatchTakePictureIntent(mContext);
                    } else {
                        checkCameraStoragePermissions(mContext);
                    }
                } else if (mPictureType.equals("GALLERY")) {
                    if (isCameraStoragePermissionGranted(mContext)) {
                        mPictureType = "GALLERY";
                        choosePhotoFromGallary(mContext);
                    } else {
                        checkCameraStoragePermissions(mContext);
                    }
                }
                break;
        }
    }

    private void choosePhotoFromGallary(Context mContext) {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        gallery.addCategory(Intent.CATEGORY_OPENABLE);
        gallery.setType("*/*");

        try {
            gallery.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(gallery, 700);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent(Context mContext) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            try {
                File photoFile = createImageFile(mContext, ".jpg");
                Uri photoURI =
                        FileProvider.getUriForFile(
                                mContext,
                                BuildConfig.APPLICATION_ID + ".fileprovider", photoFile
                        );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(
                        takePictureIntent,
                        600
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFile(Context mContext, String extension) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir;
        if (extension.contains(".jpg") || extension.contains(".png")) {
            storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName, extension,
                    storageDir
            );
            // Save a file: path for use with ACTION_VIEW intents
            FileUtils.mCurrentPhotoPath = image.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return image;
    }

    private boolean isCameraStoragePermissionGranted(Context mContext) {
        return ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED;
    }
}