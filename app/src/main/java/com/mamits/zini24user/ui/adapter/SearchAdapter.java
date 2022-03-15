package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.search.SearchDataModel;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.utils.listeners.OnItemClickListener;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context mContext;
    public List<SearchDataModel> list;
    private Activity activity;
    private OnItemClickListener listener;

    public SearchAdapter(Context mContex, OnItemClickListener listener, List<SearchDataModel> searchList) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = searchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        if (list.size() > 0) {
            SearchDataModel model = list.get(position);
            holder.text_service_name.setText(!model.getName().isEmpty() ? model.getName() : model.getLabel());

            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(v -> {
                listener.onClick(position, holder.text_service_name, list.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView text_service_name;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            text_service_name = itemView.findViewById(R.id.text_service_name);
        }
    }
}
