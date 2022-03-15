package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.PaymentDetailViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentDetailModule {

    @Provides
    public PaymentDetailViewModel providesPaymentDetailViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new PaymentDetailViewModel(mDataManger, mSchedulerProvider);
    }

}
