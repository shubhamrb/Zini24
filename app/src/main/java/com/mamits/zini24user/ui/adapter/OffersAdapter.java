package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.offer.OfferDataModel;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.customviews.CustomTextViewHtml;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.util.ArrayList;
import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    private Context mContext;
    public List<OfferDataModel> list;
    private Activity activity;
    private couponSelectListener listener;

    public OffersAdapter(Context mContex) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
    }

    public OffersAdapter(Context mContex, couponSelectListener listener) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.offers_item_list, parent, false);
        return new OffersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        if (list.size() > 0) {
            OfferDataModel model = list.get(position);

            holder.txt_offer_name.setText(model.getDescription());
            holder.txt_coupon.setText(model.getCoupon());

            holder.txt_off_rate.setText(String.format("%s%% off", model.getDiscount_amount()));

            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(v -> {
                if (listener == null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("store_id", model.getStore_id());
                    Navigation.findNavController(v).navigate(R.id.nav_store_detail, bundle);
                } else {
                    listener.onSelect(list.get(position));
                }
            });
        }
    }

    public interface couponSelectListener {
        void onSelect(OfferDataModel data);
    }

    public void setList(List<OfferDataModel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class OffersViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_off_rate, txt_coupon;
        private CustomTextViewHtml txt_offer_name;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_offer_name = itemView.findViewById(R.id.txt_offer_name);
            txt_off_rate = itemView.findViewById(R.id.txt_off_rate);
            txt_coupon = itemView.findViewById(R.id.txt_coupon);
        }
    }
}
