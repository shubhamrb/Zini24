package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.AllSubcategoryFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AllSubcategoryFragmentModule {

    @Provides
    public AllSubcategoryFragmentViewModel providesAllSubcategoryViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new AllSubcategoryFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
