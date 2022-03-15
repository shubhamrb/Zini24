package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.WebViewActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class WebViewActivityModule {

    @Provides
    public WebViewActivityViewModel providesWebViewActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new WebViewActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
