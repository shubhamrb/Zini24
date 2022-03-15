package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.ChangePasswordViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ChangePasswordModule {

    @Provides
    public ChangePasswordViewModel providesChangePasswordViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ChangePasswordViewModel(mDataManger, mSchedulerProvider);
    }

}
