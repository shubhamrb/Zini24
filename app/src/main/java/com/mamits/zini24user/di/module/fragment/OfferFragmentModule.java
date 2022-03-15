package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.OfferFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class OfferFragmentModule {

    @Provides
    public OfferFragmentViewModel providesOfferViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new OfferFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
