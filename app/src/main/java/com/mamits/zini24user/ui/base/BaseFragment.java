package com.mamits.zini24user.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.mamits.zini24user.ui.utils.commonClasses.CommonUtils;
import com.mamits.zini24user.viewmodel.base.BaseViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends DaggerFragment {

    private BaseActivity mActivity;
    private View mRootView;

    private T mViewDataBinding;
    private ProgressDialog mProgressDialog;
    private boolean viewCreated = false;

    boolean hasInitializedRootView = false;
    private View rootView =null;


    @Inject
    protected V mViewModel;


    public abstract V getMyViewModel();

    ;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    protected abstract void initView(View view,boolean isRefresh);


    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return getPersistentView(inflater,container,savedInstanceState);
    }
    public void hidesLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }


    private  View getPersistentView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if (mRootView == null) {
            // Inflate the layout for this fragment
            mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            mRootView = mViewDataBinding.getRoot();
            initView(mRootView,true);
        } else {
            initView(mRootView,false);
        }

        return mRootView;

    }
    public void showsLoading() {
        hidesLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(mActivity);
    }

    @Override
    public void onDetach() {
        mActivity = null;

        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasInitializedRootView) {
            hasInitializedRootView = true;
            if (mViewModel == null) {
                mViewModel = getMyViewModel();
                mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
            }else {
                mViewDataBinding.setVariable(getBindingVariable(), getMyViewModel());
            }

            mViewDataBinding.setLifecycleOwner(this);
            mViewDataBinding.executePendingBindings();

        }

    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
