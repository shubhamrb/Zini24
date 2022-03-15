package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.NotificationFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationFragmentModule {

    @Provides
    public NotificationFragmentViewModel providesNotificationViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new NotificationFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
