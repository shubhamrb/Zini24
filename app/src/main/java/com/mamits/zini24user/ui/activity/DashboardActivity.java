package com.mamits.zini24user.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24user.BR;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.form.CustomFieldObject;
import com.mamits.zini24user.data.model.form.ValueModel;
import com.mamits.zini24user.data.model.search.SearchDataModel;
import com.mamits.zini24user.databinding.ActivityDashboardBinding;
import com.mamits.zini24user.ui.adapter.SearchAdapter;
import com.mamits.zini24user.ui.base.BaseActivity;
import com.mamits.zini24user.ui.navigator.activity.DashboardActivityNavigator;
import com.mamits.zini24user.ui.notification.NotificationService;
import com.mamits.zini24user.ui.utils.listeners.LocationUpdateListener;
import com.mamits.zini24user.ui.utils.listeners.OnItemClickListener;
import com.mamits.zini24user.viewmodel.activity.DashboardActivityViewModel;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding, DashboardActivityViewModel>
        implements DashboardActivityNavigator, View.OnClickListener, NavController.OnDestinationChangedListener, OnItemClickListener {

    String TAG = "DashboardActivity";
    @Inject
    DashboardActivityViewModel mViewModel;
    ActivityDashboardBinding binding;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavController mNavController;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationUpdateListener updateListener;
    private boolean isSearchOpen = false;
    private List<SearchDataModel> searchList;

    @Override
    public int getBindingVariable() {
        return BR.dashboardView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            String type = bundle.getString("type");
            mViewModel.getmDataManger().setNotificationType(type);
        }
        
        /*temp*/
        mViewModel.getmDataManger().setPaymentOpen(false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Glide.with(this).load(mViewModel.getmDataManger().getUserImage()).into(binding.toolbar.userPic);
        setUpNavigation();

        /*drawer layout*/
        mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, new Toolbar(this),
                R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

        };

        binding.drawerLayout.addDrawerListener(mDrawerToggle);
        binding.toolbar.btnMenu.setOnClickListener(this);
        binding.toolbar.llLocation.setOnClickListener(this);
        binding.toolbar.btnNotification.setOnClickListener(this);
        binding.navDrawer.btnClose.setOnClickListener(this);
        binding.navDrawer.btnLogout.setOnClickListener(this);
        binding.navDrawer.btnHome.setOnClickListener(this);
        binding.navDrawer.btnProfile.setOnClickListener(this);
        binding.navDrawer.btnHistory.setOnClickListener(this);
        binding.toolbar.btnSearch.setOnClickListener(this);

        new ClickShrinkEffect(binding.toolbar.btnMenu);
        new ClickShrinkEffect(binding.toolbar.llLocation);
        new ClickShrinkEffect(binding.toolbar.btnNotification);
        new ClickShrinkEffect(binding.navDrawer.btnClose);
        new ClickShrinkEffect(binding.navDrawer.btnLogout);
        new ClickShrinkEffect(binding.navDrawer.btnHome);
        new ClickShrinkEffect(binding.navDrawer.btnProfile);
        new ClickShrinkEffect(binding.navDrawer.btnHistory);
        new ClickShrinkEffect(binding.toolbar.btnSearch);

        binding.searchToolbar.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().length() > 0) {
                    binding.rvSearch.setVisibility(View.VISIBLE);
                    callHomeSearch(s.toString().trim());
                } else {
                    binding.rvSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        try {
            startService(new Intent(this, NotificationService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestLocationPermission();
    }

    private void callHomeSearch(String value) {
        mViewModel.searchService(this, value);
    }

    public boolean isListenerNull() {
        return updateListener == null;
    }

    public void updateLocation(LocationUpdateListener listener) {
        updateListener = listener;
    }

    private void requestLocationPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = null;
                            Boolean coarseLocationGranted = null;

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                fineLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                                coarseLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            }

                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                getLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                getLocation();
                            } else {
                                // No location access granted.
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            Log.i(TAG, "lat : " + addresses.get(0).getLatitude() + " , " + "long : " + addresses.get(0).getLongitude());
                            Log.i(TAG, "address : " + addresses.get(0).getAddressLine(0) + " , " + "city : " + addresses.get(0).getLocality());

                            binding.toolbar.textLocationName.setText(addresses.get(0).getAddressLine(0));
                            if (updateListener != null)
                                updateListener.updateLocation(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                            mViewModel.getmDataManger().setLatitude(addresses.get(0).getLatitude());
                            mViewModel.getmDataManger().setLongitude(addresses.get(0).getLongitude());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setUpNavigation() {
        try {
            mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
            mNavController.addOnDestinationChangedListener(this);
            NavigationUI.setupWithNavController(binding.navView, mNavController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected DashboardActivityViewModel getMyViewModel() {
        return mViewModel;
    }


    @Override
    public void onClick(View v) {
        try {
            NavDestination navDestination = mNavController.getCurrentDestination();
            switch (v.getId()) {
                case R.id.btn_logout:

                    new Handler().postDelayed(() -> {
                        try {
                            stopService(new Intent(this, NotificationService.class));
                            mViewModel.getmDataManger().clearAllPreference();
                            startActivity(new Intent(this, LoginActivity.class));
                            finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 200);
                    break;
                case R.id.btn_menu:
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                    break;
                case R.id.btn_close:
                    binding.drawerLayout.closeDrawers();
                    break;

                case R.id.ll_location:
                    initializeLocation();
                    break;
                case R.id.btn_notification:
                    mNavController.navigate(R.id.nav_notification);
                    break;
                case R.id.btn_home:
                    binding.drawerLayout.closeDrawers();
                    if (navDestination != null && navDestination.getId() != R.id.nav_home) {
                        mNavController.navigate(R.id.nav_home);
                    }
                    break;
                case R.id.btn_profile:
                    binding.drawerLayout.closeDrawers();
                    if (navDestination != null && navDestination.getId() != R.id.nav_profile) {
                        mNavController.navigate(R.id.nav_profile);
                    }
                    break;
                case R.id.btn_history:
                    binding.drawerLayout.closeDrawers();
                    if (navDestination != null && navDestination.getId() != R.id.nav_history) {
                        mNavController.navigate(R.id.nav_history);
                    }
                    break;
                case R.id.btn_nearby:
                    binding.drawerLayout.closeDrawers();
                    if (navDestination != null && navDestination.getId() != R.id.nav_nearby) {
                        mNavController.navigate(R.id.nav_nearby);
                    }
                    break;
                case R.id.btn_offers:
                    binding.drawerLayout.closeDrawers();
                    if (navDestination != null && navDestination.getId() != R.id.nav_offer) {
                        mNavController.navigate(R.id.nav_offer);
                    }
                    break;
                case R.id.btn_search:
                    isSearchOpen = true;
                    binding.toolbar.toolbar.setVisibility(View.GONE);
                    binding.searchToolbar.searchToolbar.setVisibility(View.VISIBLE);
                    binding.rvSearch.setVisibility(View.VISIBLE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeLocation() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    if (place.getLatLng() != null) {
                        binding.toolbar.textLocationName.setText(place.getName());
                        Log.i(TAG, "lat : " + place.getLatLng().latitude + " , " + "long : " + place.getLatLng().longitude);
                        Log.i(TAG, "address : " + place.getAddress() + " , " + "city : " + place.getName());

                        if (updateListener != null)
                            updateListener.updateLocation(place.getLatLng().latitude, place.getLatLng().longitude);
                        mViewModel.getmDataManger().setLatitude(place.getLatLng().latitude);
                        mViewModel.getmDataManger().setLongitude(place.getLatLng().longitude);
                    }
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpen) {
            isSearchOpen = false;
            binding.searchToolbar.searchToolbar.setVisibility(View.GONE);
            binding.rvSearch.setVisibility(View.GONE);
            binding.toolbar.toolbar.setVisibility(View.VISIBLE);
            return;
        }
        try {
            if (mNavController != null && mNavController.getCurrentDestination() != null && mNavController.getCurrentDestination().getId() != 0) {
                if (mNavController.getCurrentDestination().getId() == R.id.nav_home) {
                    super.onBackPressed();
                } else {
                    mNavController.popBackStack();
                }
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onSuccessServices(JsonObject jsonObject) {
        if (jsonObject.get("status").getAsBoolean()) {
            Type searchData = new TypeToken<List<SearchDataModel>>() {
            }.getType();
            searchList = new Gson().fromJson(jsonObject.get("data").getAsJsonArray().toString(), searchData);

            setUpSearchList();

        }
    }

    private void setUpSearchList() {
        if (searchList != null && searchList.size() > 0) {
            binding.rvSearch.setVisibility(View.VISIBLE);
            binding.rvSearch.setHasFixedSize(true);
            binding.rvSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            binding.rvSearch.setAdapter(new SearchAdapter(this, this, searchList));
        } else {
            binding.rvSearch.setVisibility(View.GONE);
        }
        if (binding.searchToolbar.etSearch.getText().toString().trim().isEmpty()) {
            binding.rvSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination
            destination, @Nullable Bundle arguments) {
        hideKeyboard();
    }

    @Override
    public void onClick(int pos, View view, CustomFieldObject obj) {

    }

    @Override
    public void onClick(int pos, View view, CustomFieldObject obj, String type) {

    }

    @Override
    public void onClick(int pos, View view, ValueModel obj) {

    }

    @Override
    public void onClick(int pos, View view, SearchDataModel obj) {
        if (obj != null) {
            hideKeyboard();
            if (isSearchOpen) {
                isSearchOpen = false;
                binding.searchToolbar.searchToolbar.setVisibility(View.GONE);
                binding.rvSearch.setVisibility(View.GONE);
                binding.toolbar.toolbar.setVisibility(View.VISIBLE);
            }
            Bundle bundle = new Bundle();

            switch (obj.getType()) {
                case "category":
                    bundle.putInt("cat_id", Integer.parseInt(obj.getValue()));
                    bundle.putString("name", obj.getName());
                    mNavController.navigate(R.id.nav_all_subcategory, bundle);
                    break;
                case "subcategory":
                    String[] value = obj.getValue().split("_");
                    bundle.putInt("cat_id", Integer.parseInt(value[0]));
                    bundle.putInt("sub_cat_id", Integer.parseInt(value[1]));
                    bundle.putString("name", obj.getLabel());
                    bundle.putString("desc", "");
                    mNavController.navigate(R.id.nav_services, bundle);
                    break;
                case "product":
                    bundle.putInt("product_id", Integer.parseInt(obj.getValue()));
                    mNavController.navigate(R.id.nav_stores, bundle);
                    break;
                case "store":
                    bundle.putSerializable("store_id", Integer.parseInt(obj.getValue()));
                    mNavController.navigate(R.id.nav_store_detail, bundle);
                    break;
            }
        }
    }
}