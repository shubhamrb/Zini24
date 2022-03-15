package com.mamits.zini24user.di.builder;

import com.mamits.zini24user.di.module.activity.DashboardActivityModule;
import com.mamits.zini24user.di.module.activity.ForgotPasswordActivityModule;
import com.mamits.zini24user.di.module.activity.LoginActivityModule;
import com.mamits.zini24user.di.module.activity.MessageModule;
import com.mamits.zini24user.di.module.activity.OtpActivityModule;
import com.mamits.zini24user.di.module.activity.PaymentActivityModule;
import com.mamits.zini24user.di.module.activity.RegisterActivityModule;
import com.mamits.zini24user.di.module.activity.ResetPasswordModule;
import com.mamits.zini24user.di.module.activity.WebViewActivityModule;
import com.mamits.zini24user.di.module.fragment.AllSubcategoryFragmentModule;
import com.mamits.zini24user.di.module.fragment.ChangePasswordModule;
import com.mamits.zini24user.di.module.fragment.ChatFragmentModule;
import com.mamits.zini24user.di.module.fragment.FormFragmentModule;
import com.mamits.zini24user.di.module.fragment.HistoryFragmentModule;
import com.mamits.zini24user.di.module.fragment.HomeFragmentModule;
import com.mamits.zini24user.di.module.fragment.NearbyFragmentModule;
import com.mamits.zini24user.di.module.fragment.NotificationFragmentModule;
import com.mamits.zini24user.di.module.fragment.OfferFragmentModule;
import com.mamits.zini24user.di.module.fragment.PaymentDetailModule;
import com.mamits.zini24user.di.module.fragment.PaymentSummaryFragmentModule;
import com.mamits.zini24user.di.module.fragment.ProfileFragmentModule;
import com.mamits.zini24user.di.module.fragment.ServicesFragmentModule;
import com.mamits.zini24user.di.module.fragment.StoreDetailFragmentModule;
import com.mamits.zini24user.di.scope.ActivityScope;
import com.mamits.zini24user.di.scope.FragmentScope;
import com.mamits.zini24user.ui.activity.CouponsActivity;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.activity.ForgotPasswordActivity;
import com.mamits.zini24user.ui.activity.LoginActivity;
import com.mamits.zini24user.ui.activity.MessageActivity;
import com.mamits.zini24user.ui.activity.OtpActivity;
import com.mamits.zini24user.ui.activity.PaymentActivity;
import com.mamits.zini24user.ui.activity.RegisterActivity;
import com.mamits.zini24user.ui.activity.ResetPasswordActivity;
import com.mamits.zini24user.ui.activity.WebViewActivity;
import com.mamits.zini24user.ui.fragment.AllSubcategoryFragment;
import com.mamits.zini24user.ui.fragment.ChangePasswordFragment;
import com.mamits.zini24user.ui.fragment.ChatFragment;
import com.mamits.zini24user.ui.fragment.FormFragment;
import com.mamits.zini24user.ui.fragment.HistoryFragment;
import com.mamits.zini24user.ui.fragment.HomeFragment;
import com.mamits.zini24user.ui.fragment.InstructionFragment;
import com.mamits.zini24user.ui.fragment.NearbyFragment;
import com.mamits.zini24user.ui.fragment.NotificationFragment;
import com.mamits.zini24user.ui.fragment.OfferFragment;
import com.mamits.zini24user.ui.fragment.PaymentDetailFragment;
import com.mamits.zini24user.ui.fragment.PaymentSummaryFragment;
import com.mamits.zini24user.ui.fragment.ProfileFragment;
import com.mamits.zini24user.ui.fragment.ServicesFragment;
import com.mamits.zini24user.ui.fragment.StoreDetailFragment;
import com.mamits.zini24user.ui.fragment.UpdateProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    @ActivityScope
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {RegisterActivityModule.class})
    @ActivityScope
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = {ForgotPasswordActivityModule.class})
    @ActivityScope
    abstract ForgotPasswordActivity bindForgotActivity();

    @ContributesAndroidInjector(modules = {OtpActivityModule.class})
    @ActivityScope
    abstract OtpActivity bindOtpActivity();

    @ContributesAndroidInjector(modules = {DashboardActivityModule.class})
    @ActivityScope
    abstract DashboardActivity bindDashboardActivity();

    @ContributesAndroidInjector(modules = {WebViewActivityModule.class})
    @ActivityScope
    abstract WebViewActivity bindWebViewActivity();

    @ContributesAndroidInjector(modules = {HomeFragmentModule.class})
    @FragmentScope
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector(modules = {ChatFragmentModule.class})
    @FragmentScope
    abstract ChatFragment bindChatFragment();

    @ContributesAndroidInjector(modules = {HistoryFragmentModule.class})
    @FragmentScope
    abstract HistoryFragment bindHistoryFragment();

    @ContributesAndroidInjector(modules = {NearbyFragmentModule.class})
    @FragmentScope
    abstract NearbyFragment bindNearbyFragment();

    @ContributesAndroidInjector(modules = {OfferFragmentModule.class})
    @FragmentScope
    abstract OfferFragment bindOfferFragment();

    @ContributesAndroidInjector(modules = {AllSubcategoryFragmentModule.class})
    @FragmentScope
    abstract AllSubcategoryFragment bindAllSubcategoryFragment();

    @ContributesAndroidInjector(modules = {ServicesFragmentModule.class})
    @FragmentScope
    abstract ServicesFragment bindServicesFragment();

    @ContributesAndroidInjector(modules = {StoreDetailFragmentModule.class})
    @FragmentScope
    abstract StoreDetailFragment bindStoreDetailFragment();

    @ContributesAndroidInjector(modules = {MessageModule.class})
    @ActivityScope
    abstract MessageActivity bindMessageActivity();

    @ContributesAndroidInjector(modules = {NotificationFragmentModule.class})
    @FragmentScope
    abstract NotificationFragment bindNotificationFragment();

    @ContributesAndroidInjector(modules = {FormFragmentModule.class})
    @FragmentScope
    abstract FormFragment bindNFormFragment();

    @ContributesAndroidInjector(modules = {FormFragmentModule.class})
    @FragmentScope
    abstract InstructionFragment bindInstructionFragment();

    @ContributesAndroidInjector(modules = {ProfileFragmentModule.class})
    @FragmentScope
    abstract ProfileFragment bindProfileFragment();

    @ContributesAndroidInjector(modules = {PaymentSummaryFragmentModule.class})
    @FragmentScope
    abstract PaymentSummaryFragment bindPaymentSummaryFragment();

    @ContributesAndroidInjector(modules = {PaymentDetailModule.class})
    @FragmentScope
    abstract PaymentDetailFragment bindPaymentDetailFragment();

    @ContributesAndroidInjector(modules = {PaymentActivityModule.class})
    @ActivityScope
    abstract PaymentActivity bindPaymentActivity();

    @ContributesAndroidInjector(modules = {ChangePasswordModule.class})
    @FragmentScope
    abstract ChangePasswordFragment bindChangePasswordFragment();

    @ContributesAndroidInjector(modules = {ResetPasswordModule.class})
    @ActivityScope
    abstract ResetPasswordActivity bindResetPasswordActivity();

    @ContributesAndroidInjector(modules = {ProfileFragmentModule.class})
    @FragmentScope
    abstract UpdateProfileFragment bindUpdateProfileFragment();

    @ContributesAndroidInjector(modules = {OfferFragmentModule.class})
    @ActivityScope
    abstract CouponsActivity bindCouponsActivity();
}
