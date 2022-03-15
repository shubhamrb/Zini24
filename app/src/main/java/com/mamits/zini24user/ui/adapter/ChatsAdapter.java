package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.ui.activity.MessageActivity;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.utils.DateConvertor;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.OrdersViewHolder> {

    private Context mContext;
    public List<OrdersDataModel> list;
    private Activity activity;


    public ChatsAdapter(Context mContex) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.chat_item_list, parent, false);
        return new OrdersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        if (list.size() > 0) {
            OrdersDataModel model = list.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date d1 = formatter.parse(model.getCreated_at());
                String[] date = new DateConvertor().getDate(d1.getTime(), DateConvertor.FORMAT_dd_MM_yyyy_HH_mm_ss).split(" ");
                holder.txt_date.setText(date[0]);
                holder.txt_time.setText(date[1]);

            } catch (Exception e) {
                holder.txt_date.setText(model.getCreated_at());
                e.printStackTrace();
            }

            holder.txt_user_name.setText(model.getProducts().getName());

            holder.txt_order_id.setText(String.format("Order ID : %s", model.getOrder_id()));
            /*switch (model.getStatus()) {
                case 1:
                    holder.txt_status.setText("Pending");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.yellow_ffb302));
                    break;
                case 2:
                    holder.txt_status.setText("Accept");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.green_39ae00));
                    break;
                case 3:
                    holder.txt_status.setText("Reject");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.red_ff2502));
                    break;
                case 4:
                    holder.txt_status.setText("Cancel");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.red_ff2502));
                    break;
                case 5:
                    holder.txt_status.setText("Complete");
                    holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.green_39ae00));
                    break;
            }*/

            Glide.with(mContext).load(model.getProducts().getProduct_image()).into(holder.img);
            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(v -> {
                int status = model.getStatus();
                if (status == 1 || status == 3 || status == 4) {
                    return;
                }
                gotoMessenger(position);
            });
        }
    }

    public void setList(List<OrdersDataModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    private void gotoMessenger(int position) {

        mContext.startActivity(new Intent(mContext, MessageActivity.class)
                .putExtra("userid", list.get(position).getStoredetail().getUser_id())
                .putExtra("orderid", list.get(position).getId())
                .putExtra("status", list.get(position).getStatus())
                .putExtra("name", list.get(position).getProducts().getName())
        );
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_date, txt_user_name, txt_time, txt_order_id;
        private ImageView img;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_user_name = itemView.findViewById(R.id.txt_user_name);
            txt_time = itemView.findViewById(R.id.txt_time);
            img = itemView.findViewById(R.id.img_order);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);

        }
    }
}
