package com.mamits.zini24user.viewmodel.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<N> extends ViewModel {

    private final IDataManager mDataManger;

    private final ObservableBoolean mIsLoading = new ObservableBoolean();

    private final ISchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    public BaseViewModel(IDataManager dataManager,
                         ISchedulerProvider schedulerProvider) {
        this.mDataManger = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getmCompositeDisposable() {
        mCompositeDisposable = new CompositeDisposable();
        return mCompositeDisposable;
    }

    public IDataManager getmDataManger() {
        return mDataManger;
    }

    public ObservableBoolean getmIsLoading() {
        return mIsLoading;
    }

    public WeakReference<N> getmNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }


    public ISchedulerProvider getmSchedulerProvider() {
        return mSchedulerProvider;
    }

}
