package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.ServicesFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesFragmentModule {

    @Provides
    public ServicesFragmentViewModel providesServicesViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ServicesFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
