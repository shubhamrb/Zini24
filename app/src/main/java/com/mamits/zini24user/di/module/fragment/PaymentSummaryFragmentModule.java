package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.PaymentSummaryFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentSummaryFragmentModule {

    @Provides
    public PaymentSummaryFragmentViewModel providesPaymentSummaryViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new PaymentSummaryFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
