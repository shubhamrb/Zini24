package com.mamits.zini24user.di.module.fragment;


import com.mamits.zini24user.data.datamanager.IDataManager;
import com.mamits.zini24user.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24user.viewmodel.fragment.ProfileFragmentViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileFragmentModule {

    @Provides
    public ProfileFragmentViewModel providesProfileViewModel(IDataManager mDataManger, ISchedulerProvider mSchedulerProvider) {
        return new ProfileFragmentViewModel(mDataManger, mSchedulerProvider);
    }

}
