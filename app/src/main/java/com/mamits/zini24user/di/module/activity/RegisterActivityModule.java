package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.RegisterActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterActivityModule {

    @Provides
    public RegisterActivityViewModel providesRegisterActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new RegisterActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
