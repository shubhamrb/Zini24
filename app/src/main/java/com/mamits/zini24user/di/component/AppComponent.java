package com.mamits.zini24user.di.component;



import com.mamits.zini24user.ZiniApplication;
import com.mamits.zini24user.di.builder.ActivityBuilder;
import com.mamits.zini24user.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, AndroidSupportInjectionModule.class, ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<ZiniApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<ZiniApplication> {
    }
}
