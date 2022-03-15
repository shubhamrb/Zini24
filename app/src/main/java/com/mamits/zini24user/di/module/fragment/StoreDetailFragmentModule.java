package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.StoreDetailFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class StoreDetailFragmentModule {

    @Provides
    public StoreDetailFragmentViewModel providesStoreDetailViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new StoreDetailFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
