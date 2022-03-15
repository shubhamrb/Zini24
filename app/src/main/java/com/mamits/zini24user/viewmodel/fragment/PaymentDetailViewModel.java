package com.mamits.zini24user.viewmodel.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.navigator.fragment.PaymentDetailNavigator;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.base.BaseViewModel;

public class PaymentDetailViewModel extends BaseViewModel<PaymentDetailNavigator> {

    public PaymentDetailViewModel(IDataManager dataManager, ISchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
