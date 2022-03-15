package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.NearbyFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class NearbyFragmentModule {

    @Provides
    public NearbyFragmentViewModel providesNearbyViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new NearbyFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
