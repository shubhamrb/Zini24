package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.HomeFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFragmentModule {

    @Provides
    public HomeFragmentViewModel providesHomeViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new HomeFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
