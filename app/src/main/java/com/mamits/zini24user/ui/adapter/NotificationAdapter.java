package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.notification.NotificationModel;
import com.mamits.zini24user.ui.activity.DashboardActivity;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.utils.DateConvertor;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context mContext;
    public List<NotificationModel> list;
    private Activity activity;


    public NotificationAdapter(Context mContex) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        if (list.size() > 0) {
            NotificationModel model = list.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date d1 = formatter.parse(model.getCreated_at());
                String[] date = new DateConvertor().getDate(d1.getTime(), DateConvertor.FORMAT_dd_MM_yyyy_HH_mm_ss).split(" ");
                holder.txt_date.setText(date[0]);
                holder.txt_time.setText(date[1]);

            } catch (Exception e) {
                String[] date = model.getCreated_at().split(" ");
                holder.txt_date.setText(date[0]);
                holder.txt_time.setText(date[1]);
                e.printStackTrace();
            }

            holder.txt_notification.setText(model.getMessage());
            holder.txt_status.setText(model.getNoti_type());

            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(v -> {
                Navigation.findNavController(((DashboardActivity) mContext)
                        .findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_history);
            });
        }
    }

    public void setList(List<NotificationModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_date, txt_time, txt_notification, txt_status;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_notification = itemView.findViewById(R.id.txt_notification);
            txt_status = itemView.findViewById(R.id.txt_status);
        }
    }
}
