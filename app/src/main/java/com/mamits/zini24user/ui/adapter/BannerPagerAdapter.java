package com.mamits.zini24user.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mamits.zini24user.data.model.home.BannerListItem;
import com.mamits.zini24user.ui.fragment.BannerPagerFragment;

import java.util.List;

public class BannerPagerAdapter extends FragmentStatePagerAdapter {
    List<BannerListItem> mList;
    List<String> sList;
    Context context;

    public BannerPagerAdapter(Context mContext, FragmentManager fm, List<BannerListItem> mList) {
        super(fm);
        this.mList = mList;
        context = mContext;
    }

    public BannerPagerAdapter(Context mContext, List<String> mList, FragmentManager fm) {
        super(fm);
        this.sList = mList;
        context = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mList!=null){
            return BannerPagerFragment.newInstance(position, mList.get(position));
        }else {
            return BannerPagerFragment.newInstance(position, sList.get(position));
        }
    }

    @Override
    public int getCount() {
        if (mList!=null){
            return mList.size();
        }else {
            return sList.size();
        }

    }
}
