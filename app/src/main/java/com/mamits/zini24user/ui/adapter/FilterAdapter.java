package com.mamits.zini24user.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.ui.customviews.CustomTextView;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<String> list;
    private int mSelectedPosition = -1;
    private String selectedStr;
    private onClickListener listener;
    private String type;

    public FilterAdapter(Context context, List<String> list, onClickListener listener, String type) {
        this.mContext = context;
        this.list = list;
        this.listener = listener;
        this.type = type;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.filter_item, parent, false);
        return new CategoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (list.size() > 0) {
            holder.txt_name.setText(list.get(position));

            if (position != mSelectedPosition) {
                holder.txt_name.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                holder.card.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.dim_blue_e6dfff)));
            } else {
                holder.txt_name.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.card.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.color_orange)));
            }

            if (list.get(position).equals(selectedStr)) {
                holder.txt_name.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.card.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.color_orange)));
            }

            holder.itemView.setOnClickListener(view -> {
                mSelectedPosition = position;
                selectedStr = "";
                listener.onClick(position, list.get(position), type);
                notifyDataSetChanged();
            });
        }
    }

    public interface onClickListener {
        void onClick(int position, String data, String type);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_name;
        private CardView card;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            card = itemView.findViewById(R.id.card);
        }
    }
}
