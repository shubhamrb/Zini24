package com.mamits.zini24user.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.CategoryListItem;
import com.mamits.zini24user.data.model.home.SubcategoryListItem;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.customviews.CustomTextViewHtml;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    public List<CategoryListItem> list;

    public CategoryAdapter(Context mContex, List<CategoryListItem> categoryList) {
        this.mContext = mContex;
        list = categoryList;
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
        View root = LayoutInflater.from(mContext).inflate(R.layout.category_item_list, parent, false);
        return new CategoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (list.size() > 0) {
            CategoryListItem model = list.get(position);
            holder.txt_cat_name.setText(model.getName());

            holder.txt_des.setText(model.getDescription());
            holder.background_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(model.getBg_color())));

            setUpSubcategoryList(holder, list.get(position).getSubcategories(), list.get(position).getId());

            new ClickShrinkEffect(holder.itemView);
            new ClickShrinkEffect(holder.btn_view_all);

            holder.btn_view_all.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("cat_id", model.getId());
                bundle.putString("name", model.getName());
                bundle.putString("desc", model.getShort_description());
                Navigation.findNavController(view).navigate(R.id.nav_all_subcategory, bundle);
            });
        }
    }

    private void setUpSubcategoryList(CategoryViewHolder holder, List<SubcategoryListItem> subcategories, int cat_id) {
        holder.recyclerView_sub_category.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        holder.subCategoryAdapter = new SubCategoryAdapter(mContext, subcategories, cat_id,-1,false);
        holder.recyclerView_sub_category.setAdapter(holder.subCategoryAdapter);
    }


    @Override
    public int getItemCount() {
        return Math.min(list.size(), 4);
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public SubCategoryAdapter subCategoryAdapter;
        private RecyclerView recyclerView_sub_category;
        private CustomTextView txt_cat_name;
        private CustomTextViewHtml txt_des;
        private LinearLayout btn_view_all;
        private CardView background_card;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView_sub_category = itemView.findViewById(R.id.recyclerView_sub_category);
            txt_cat_name = itemView.findViewById(R.id.txt_cat_name);
            btn_view_all = itemView.findViewById(R.id.btn_view_all);
            background_card = itemView.findViewById(R.id.background_card);
            txt_des = itemView.findViewById(R.id.txt_des);

        }
    }
}
