package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
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
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.List;

public class LatestServicesAdapter extends RecyclerView.Adapter<LatestServicesAdapter.ServicesViewHolder> {

    private Context mContext;
    public List<ProductDataModel> list;
    private Activity activity;


    public LatestServicesAdapter(Context mContex, List<ProductDataModel> subcategories) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = subcategories;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.subcategory_item_list, parent, false);
        return new ServicesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        if (list.size() > 0) {
            ProductDataModel model = list.get(position);
            holder.txt_sub_cat_name.setText(model.getName());
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 100);
            Glide.with(mContext).asBitmap()
                    .apply(myOptions).load(model.getImage()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_sub_cat);

            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", model.getId());
                Navigation.findNavController(view).navigate(R.id.nav_stores, bundle);
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ServicesViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_sub_cat_name;
        private ImageView img_sub_cat;

        public ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_sub_cat_name = itemView.findViewById(R.id.txt_sub_cat_name);
            img_sub_cat = itemView.findViewById(R.id.img_sub_cat);

        }
    }
}
