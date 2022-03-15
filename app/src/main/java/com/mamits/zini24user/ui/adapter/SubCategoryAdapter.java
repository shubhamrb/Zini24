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
import com.mamits.zini24user.data.model.home.SubcategoryListItem;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private Context mContext;
    public List<SubcategoryListItem> list;
    private int cat_id, store_id;
    private boolean fromStore;

    public SubCategoryAdapter(Context mContex, List<SubcategoryListItem> subcategories, int cat_id, int store_id, boolean fromStore) {
        this.mContext = mContex;
        list = subcategories;
        this.cat_id = cat_id;
        this.store_id = store_id;
        this.fromStore = fromStore;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.subcategory_item_list, parent, false);
        return new SubCategoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        if (list.size() > 0) {
            SubcategoryListItem model = list.get(position);
            holder.txt_sub_cat_name.setText(model.getName());
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 100);
            Glide.with(mContext).asBitmap()
                    .apply(myOptions).load(model.getImage()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_sub_cat);

            new ClickShrinkEffect(holder.itemView);

            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                if (cat_id != -1) {
                    bundle.putInt("cat_id", cat_id);
                    bundle.putInt("sub_cat_id", model.getId());
                } else {
                    bundle.putInt("cat_id", model.getId());
                    bundle.putInt("store_id", store_id);
                }
                bundle.putString("name", model.getName());
                bundle.putString("desc", model.getDescription());
                bundle.putBoolean("fromStore", fromStore);
                Navigation.findNavController(view).navigate(R.id.nav_services, bundle);
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_sub_cat_name;
        private ImageView img_sub_cat;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_sub_cat_name = itemView.findViewById(R.id.txt_sub_cat_name);
            img_sub_cat = itemView.findViewById(R.id.img_sub_cat);

        }
    }
}
