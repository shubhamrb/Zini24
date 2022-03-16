package com.mamits.zini24user.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.databinding.ActivityWebViewBinding;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.activity.WebViewActivityNavigator;
import com.mamits.zini24user.viewmodel.activity.WebViewActivityViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class WebViewActivity extends BaseActivity<ActivityWebViewBinding, WebViewActivityViewModel>
        implements WebViewActivityNavigator, View.OnClickListener {

    String TAG = "WebViewActivity";
    @Inject
    WebViewActivityViewModel mViewModel;
    ActivityWebViewBinding binding;

    private String mType = "";
    private String cam_file_data = null;
    private String file_type = "*/*";
    private boolean multiple_files = false;
    private int file_req_code = 1;
    private ValueCallback<Uri[]> file_path;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    public int getBindingVariable() {
        return BR.webView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        permission();

        if (getIntent().hasExtra("type")) {
            mType = getIntent().getStringExtra("type");
            if (mType.equals("help")) {
                callHelpSupportWebview();
            } else if (mType.equals("enquiry")){
                callEnquiryWebView();
            }else if (mType.equals("privacy")){
                loadUrl("https://zini24.com/privacy-policy");
            }else if (mType.equals("tc")){
                loadUrl("https://zini24.com/terms-and-conditions");
            }else if (mType.equals("about")){
                loadUrl("https://zini24.com/aboutsus");
            }
        }
    }

    private void callHelpSupportWebview() {
        mViewModel.fetchHelp(this);
    }

    private void callEnquiryWebView() {
        mViewModel.fetchEnquiry(this);
    }

    private void permission() {
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this, "Permission granted, Please download", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "This permission is required to download the file.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected WebViewActivityViewModel getMyViewModel() {
        return mViewModel;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void showLoader() {
        showLoading();
    }

    @Override
    public void hideLoader() {
        hideLoading();
    }

    @Override
    public void checkValidation(int type, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable it) {
        it.printStackTrace();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessHelp(String mLink) {
        loadUrl(mLink);
    }

    private void loadUrl(String mLink) {
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webSettings.setMixedContentMode(0);
        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        binding.webview.setWebViewClient(new Callback());

        binding.webview.loadDataWithBaseURL(null, mLink, "text/html", "utf-8", null);

        binding.webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(WebViewActivity.this);

                newWebView.getSettings().setJavaScriptEnabled(true);
                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        if (url.contains("https://apnaonlines.com?filepath=")) {
                            String fileurl = url.replace("https://apnaonlines.com?filepath=", "");
                            //Download file
                            donwloadFile(fileurl);
                        } else {
                            view.loadUrl(url);
                        }
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }
                });

                resultMsg.sendToTarget();
                return true;
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (file_permission()) {
                    file_path = filePathCallback;
                    Intent takePictureIntent = null;
                    Intent takeVideoIntent = null;

                    boolean includeVideo = false;
                    boolean includePhoto = false;

                    for (String acceptTypes : fileChooserParams.getAcceptTypes()) {
                        String[] splitTypes = acceptTypes.split(", ?+");


//                                .dropLastWhile {
//                            it.isEmpty();
//                        }.toTypedArray() ;
//
//                        for (acceptType in splitTypes) {
//                            when(acceptType) {
//                                "*/*" ->{
//                                    includePhoto = true
//                                    includeVideo = true
//                                    break @paramCheck
//                                }
//                                "image/*" ->includePhoto = true
//                                "video/*" ->includeVideo = true
//                            }
//                        }
                    }

                    if (fileChooserParams.getAcceptTypes().length == 0) {
                        includePhoto = true;
                        includeVideo = true;
                    }

                    if (includePhoto) {
                        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            File photoFile;
                            photoFile = create_image();
                            takePictureIntent.putExtra("PhotoPath", cam_file_data);

                            if (photoFile != null) {
                                cam_file_data = "file:" + photoFile.getAbsolutePath();
                                takePictureIntent.putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(photoFile)
                                );
                            } else {
                                cam_file_data = null;
                                takePictureIntent = null;
                            }
                        }
                    }

                    if (includeVideo) {
                        takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                            File videoFile;
                            videoFile = create_video();

                            if (videoFile != null) {
                                cam_file_data = "file:" + videoFile.getAbsolutePath();
                                takeVideoIntent.putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(videoFile)
                                );
                            } else {
                                cam_file_data = null;
                                takeVideoIntent = null;
                            }
                        }
                    }

                    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    contentSelectionIntent.setType(file_type);
                    if (multiple_files) {
                        contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    }

                    Intent[] intentArray = null;
                    if (takePictureIntent != null && takeVideoIntent != null) {
                        intentArray = new Intent[]{takePictureIntent, takeVideoIntent};
                    } else if (takePictureIntent != null) {
                        intentArray = new Intent[]{takePictureIntent};
                    } else if (takeVideoIntent != null) {
                        intentArray = new Intent[]{takeVideoIntent};
                    } else {
                        intentArray = new Intent[]{};
                    }

                    Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                    chooserIntent.putExtra(Intent.EXTRA_TITLE, "File chooser");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, file_req_code);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private File create_image() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private File create_video() {
        String file_name = new SimpleDateFormat("yyyy_mm_ss", Locale.getDefault()).format(new Date());
        String new_name = "file_" + file_name + "_";
        File sd_directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(new_name, ".3gp", sd_directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean file_permission() {
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(
                WebViewActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                WebViewActivity.this,
                Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                    WebViewActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Uri[] results = null;

        if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == file_req_code) {
                file_path.onReceiveValue(null);
                return;
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == file_req_code) {
                if (null == file_path) {
                    return;
                }

                ClipData clipData;
                String stringData;
                try {
                    clipData = intent.getClipData();
                    stringData = intent.getDataString();
                } catch (Exception e) {
                    clipData = null;
                    stringData = null;
                }

                if (clipData == null && stringData == null && cam_file_data != null) {
                    results = new Uri[]{Uri.parse(cam_file_data)};
                } else {
                    if (clipData != null) { // checking if multiple files selected or not
                        int numSelectedFiles = clipData.getItemCount();
                        results = new Uri[numSelectedFiles];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            results[i] = clipData.getItemAt(i).getUri();
                        }
                    } else {
                        results = new Uri[]{Uri.parse(stringData)};
                    }
                }
            }
        }
        file_path.onReceiveValue(results);
        file_path = null;
    }

    private class Callback extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //Toast.makeText(WebViewActivity.this, "Failed loading page!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            relative_progress ?.visibility = View.GONE
//            progressDialogDismiss();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("https://apnaonlines.com/?filepath=")) {
                String fileurl = url.replace("https://apnaonlines.com/?filepath=", "");
                //Download file
                donwloadFile(fileurl);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

    }

    private void donwloadFile(String url) {
        if (isCameraStoragePermissionGranted()) {
            beginDownload(url);
        } else {
            checkCameraStoragePermissions();
        }
    }

    private void checkCameraStoragePermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                200
        );
    }

    public void beginDownload(String url) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
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

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(this, "Downloading...", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean isCameraStoragePermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED;
    }
}