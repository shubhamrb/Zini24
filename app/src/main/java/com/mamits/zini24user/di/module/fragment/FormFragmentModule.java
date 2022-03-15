package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.FormFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FormFragmentModule {

    @Provides
    public FormFragmentViewModel providesFormViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new FormFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
