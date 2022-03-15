package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.OtpActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class OtpActivityModule {

    @Provides
    public OtpActivityViewModel providesOtpActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new OtpActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
