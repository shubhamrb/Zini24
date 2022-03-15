package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.ResetPasswordViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ResetPasswordModule {

    @Provides
    public ResetPasswordViewModel providesResetViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ResetPasswordViewModel(mDataManger, mSchedulerProvider);
    }

}
