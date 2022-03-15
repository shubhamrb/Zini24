package com.mamits.zini24user.ui.utils.commonClasses;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoadMore {

    private RecyclerView mRecyclerView;

    private Context mContext = null;
    private boolean loading = false;
    private int lastVisibleItem = 0;
    private int totalItemCount = 0;
    private int visibleThreshold = 2;
    private OnLoadMoreListener onLoadMoreListener = null;

    public LoadMore(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        if (mRecyclerView.getLayoutManager() != null) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView
                    .getLayoutManager();

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    Log.v("TAG", "last  " + (totalItemCount <= lastVisibleItem + visibleThreshold));
                    if (loading
                            && totalItemCount <= lastVisibleItem + visibleThreshold
                    ) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = false;
                    }
                }
            });
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoadingMore(boolean status) {
        loading = status;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
