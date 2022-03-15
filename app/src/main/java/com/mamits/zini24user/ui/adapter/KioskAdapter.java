package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.store.StoreListItem;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KioskAdapter extends RecyclerView.Adapter<KioskAdapter.CategoryViewHolder> {

    private Context mContext;
    private Activity activity;
    private List<StoreListItem> list;
    private int product_id;

    public KioskAdapter(Context context, List<StoreListItem> arr, int product_id) {
        this.mContext = context;
        activity = ((Activity) context);
        list = arr;
        this.product_id = product_id;
    }

    public KioskAdapter(Context context, int product_id) {
        this.mContext = context;
        activity = ((Activity) context);
        list = new ArrayList<>();
        this.product_id = product_id;
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
        View root = LayoutInflater.from(mContext).inflate(R.layout.kiosk_item_list, parent, false);
        return new CategoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (list.size() > 0) {
            StoreListItem model = list.get(position);
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 80);
            Glide.with(mContext).asBitmap()
                    .apply(myOptions).load(model.getImage()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_kiosk);
            holder.txt_kiosk_name.setText(model.getName());
            holder.txt_dest.setText(String.format(Locale.getDefault(), "%s km away from you", model.getDistance()));
            holder.txt_rating.setText(String.format(Locale.getDefault(), "%.1f", model.getRatting()));
            switch (model.getIsAvailable()) {
                case 0:
                    holder.txt_status.setText("Closed");
                    holder.img_status.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.circle_red));
                    break;
                case 1:
                    holder.txt_status.setText("Open now");
                    holder.img_status.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.circle_green));
                    break;
            }

            new ClickShrinkEffect(holder.itemView);

            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("store_id", model.getId());
                bundle.putInt("product_id", product_id);
                bundle.putBoolean("fromStore", true);
                Navigation.findNavController(view).navigate(R.id.nav_store_detail, bundle);
            });
        }
    }

    public void setList(List<StoreListItem> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void sortList(List<StoreListItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_kiosk_name, txt_dest, txt_rating, txt_status;
        private ImageView img_kiosk;
        private View img_status;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_kiosk_name = itemView.findViewById(R.id.txt_kiosk_name);
            txt_dest = itemView.findViewById(R.id.txt_dest);
            txt_rating = itemView.findViewById(R.id.txt_rating);
            txt_status = itemView.findViewById(R.id.txt_status);
            img_kiosk = itemView.findViewById(R.id.img_kiosk);
            img_status = itemView.findViewById(R.id.img_status);
        }
    }
}
