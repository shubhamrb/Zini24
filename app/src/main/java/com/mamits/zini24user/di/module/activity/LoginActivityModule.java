package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.LoginActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    @Provides
    public LoginActivityViewModel providesLoginActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new LoginActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
