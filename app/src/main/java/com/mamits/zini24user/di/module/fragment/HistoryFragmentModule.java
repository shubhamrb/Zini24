package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.HistoryFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryFragmentModule {

    @Provides
    public HistoryFragmentViewModel providesHistoryViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new HistoryFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
