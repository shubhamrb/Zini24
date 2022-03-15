package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.ChatFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatFragmentModule {

    @Provides
    public ChatFragmentViewModel providesChatViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ChatFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
