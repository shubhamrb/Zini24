package com.mamits.zini24user.di.module.activity;

import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.activity.MessageViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {

    @Provides
    public MessageViewModel providesMessage(IDataManager iDataManager, ISchedulerProvider iSchedulerProvider) {
        return new MessageViewModel(iDataManager, iSchedulerProvider);
    }
}
