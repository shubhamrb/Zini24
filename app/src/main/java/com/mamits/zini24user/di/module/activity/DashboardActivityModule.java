package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.DashboardActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DashboardActivityModule {

    @Provides
    public DashboardActivityViewModel providesDashActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new DashboardActivityViewModel(mDataManger, mSchedulerProvider);
    }

}
