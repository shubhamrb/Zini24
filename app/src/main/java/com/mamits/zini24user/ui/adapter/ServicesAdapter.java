package com.mamits.zini24user.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.product.ProductDataModel;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.customviews.CustomTextViewHtml;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    private Context mContext;
    private List<ProductDataModel> list;
    private boolean fromStore;
    private int store_id;

    public ServicesAdapter(Context mContex, List<ProductDataModel> subcategories, boolean fromStore, int store_id) {
        this.mContext = mContex;
        list = subcategories;
        this.fromStore = fromStore;
        this.store_id = store_id;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.service_item_list, parent, false);
        return new ServicesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        if (list.size() > 0) {
            ProductDataModel model = list.get(position);
            holder.txt_service_name.setText(model.getName());
            holder.txt_des.setText(model.getShort_description());
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 100);
            Glide.with(mContext).asBitmap()
                    .apply(myOptions).load(model.getImage()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_service_cat);

            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", model.getId());
                if (!fromStore) {
                    Navigation.findNavController(view).navigate(R.id.nav_stores, bundle);
                } else {
                    bundle.putInt("store_id", store_id);
                    Navigation.findNavController(view).navigate(R.id.nav_store_detail, bundle);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ServicesViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_service_name;
        private CustomTextViewHtml txt_des;
        private ImageView img_service_cat;

        public ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_service_name = itemView.findViewById(R.id.txt_service_name);
            img_service_cat = itemView.findViewById(R.id.img_service_cat);
            txt_des = itemView.findViewById(R.id.txt_des);

        }
    }
}
