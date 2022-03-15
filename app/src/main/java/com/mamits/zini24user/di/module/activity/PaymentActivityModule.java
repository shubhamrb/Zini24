package com.mamits.zini24user.di.module.activity;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.PaymentActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentActivityModule {

    @Provides
    public PaymentActivityViewModel providesPaymentActivityViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider){
    return  new PaymentActivityViewModel(mDataManger,mSchedulerProvider);
    }

}
