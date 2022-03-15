package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.ForgotPasswordActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ForgotPasswordActivityModule {

    @Provides
    public ForgotPasswordActivityViewModel providesForgotActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ForgotPasswordActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
