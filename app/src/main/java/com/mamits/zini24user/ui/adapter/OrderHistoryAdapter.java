package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.TimerObj;
import com.mamits.zini24user.data.model.orders.OrdersDataModel;
import com.mamits.zini24user.ui.activity.MessageActivity;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.utils.DateConvertor;
import com.mamits.zini24user.ui.utils.commonClasses.TimeUtils;
import com.realpacific.clickshrinkeffect.ClickShrinkEffect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrdersViewHolder> {

    private Context mContext;
    public List<OrdersDataModel> list;
    private Activity activity;
    HashMap<Integer, CountDownTimer> timerMap = new HashMap<>();
    private onItemClickListener listener;

    public OrderHistoryAdapter(Context mContex, onItemClickListener listener) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.orders_item_list, parent, false);
        return new OrdersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (!payloads.isEmpty()) {
            for (Object it : payloads) {
                if (it instanceof TimerObj) {
                    TimerObj obj = (TimerObj) it;
                    holder.text_timer.setText(obj.getHour() + " : " + obj.getMin() + " : " + obj.getSec() + " hrs");
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        if (list.size() > 0) {
            OrdersDataModel model = list.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date d1 = formatter.parse(model.getCreated_at());
                String[] date = new DateConvertor().getDate(d1.getTime(), DateConvertor.FORMAT_dd_MM_yyyy_HH_mm_ss).split(" ");
                holder.txt_date.setText(String.format("as on %s", date[0]));

            } catch (Exception e) {
                holder.txt_date.setText(model.getCreated_at());
                e.printStackTrace();
            }

            holder.txt_user_name.setText(model.getProducts().getName());

            /*reset views*/
            holder.ll_timer.setVisibility(View.GONE);
            holder.ll_rating.setVisibility(View.GONE);

            holder.txt_order_id.setText(String.format("Order ID : %s", model.getOrder_id()));
            switch (model.getStatus()) {
                case 1:
                    holder.txt_status.setText("PENDING");
                    holder.card_status.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.yellow_ffb302)));
                    break;
                case 2:
                    holder.txt_status.setText("ACCEPTED");
                    holder.card_status.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.green_39ae00)));
                    holder.ll_timer.setVisibility(View.VISIBLE);
                    holder.btn_download.setText("Make Payment");
                    holder.btn_download.setVisibility(View.VISIBLE);

                    setTimer(list.get(position), position);
                    break;
                case 3:
                    holder.txt_status.setText("REJECTED");
                    holder.card_status.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.red_ff2502)));
                    break;
                case 4:
                    holder.txt_status.setText("CANCELED");
                    holder.card_status.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.red_ff2502)));
                    break;
                case 5:
                    holder.txt_status.setText("COMPLETED");
                    holder.card_status.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.green_39ae00)));
                    holder.ll_timer.setVisibility(View.VISIBLE);
                    holder.ll_rating.setVisibility(View.VISIBLE);
                    if (list.get(position).getPayment_file() != null && !list.get(position).getPayment_file().isEmpty()) {
                        holder.btn_download.setText("Download Files");
                        holder.btn_download.setVisibility(View.VISIBLE);
                    } else {
                        holder.btn_download.setVisibility(View.GONE);
                    }
                    if (list.get(position).getRating_status() == 1) {
                        holder.btn_save_rating.setVisibility(View.GONE);
                        if (list.get(position).getUserratting() != 0) {
                            holder.ratingBar.setRating(list.get(position).getUserratting());
                            holder.ratingBar.setIsIndicator(true);
                            holder.rating_label.setText("Your ratings");
                        }
                    } else {
                        holder.rating_label.setText("Leave a rating");
                        holder.ratingBar.setIsIndicator(false);
                        holder.btn_save_rating.setVisibility(View.VISIBLE);
                    }

                    break;
            }

            Glide.with(mContext).load(model.getProducts().getProduct_image()).into(holder.img);
            new ClickShrinkEffect(holder.itemView);
            holder.itemView.setOnClickListener(v -> {
                int status = model.getStatus();
                if (status == 1 || status == 3 || status == 4) {
                    return;
                }
                //gotoOrderDetail(v, position);
            });

            new ClickShrinkEffect(holder.rl_chat);
            holder.rl_chat.setOnClickListener(view -> {
                if (list.get(position).getStatus() == 2 || list.get(position).getStatus() == 5) {
                    mContext.startActivity(new Intent(mContext, MessageActivity.class)
                            .putExtra("userid", list.get(position).getUser_id())
                            .putExtra("orderid", list.get(position).getId())
                            .putExtra("status", list.get(position).getStatus())
                            .putExtra("name", list.get(position).getProducts().getName())
                    );
                }
            });
            new ClickShrinkEffect(holder.rl_call);
            holder.rl_call.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + list.get(position).getStoredetail().getMobile_number()));
                activity.startActivity(intent);
            });
            new ClickShrinkEffect(holder.rl_whatsapp);
            holder.rl_whatsapp.setOnClickListener(view -> {
                String wNo = list.get(position).getStoredetail().getWhatsapp_no();
                if (!wNo.startsWith("+91")) {
                    wNo = "+91" + wNo;
                }
                String url = "https://api.whatsapp.com/send?phone=" + wNo;

                try {
                    PackageManager pm = activity.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    activity.startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            new ClickShrinkEffect(holder.btn_download);
            holder.btn_download.setOnClickListener(view -> {
                if (holder.btn_download.getText().toString().contains("Download")) {
                    listener.downloadFile(list.get(position).getPayment_file());
                } else {
                    listener.makePayment(list.get(position));
                }
            });

            new ClickShrinkEffect(holder.btn_save_rating);
            holder.btn_save_rating.setOnClickListener(view -> {
                if (holder.ratingBar.getRating() > 0) {
                    list.get(position).setMyrating(holder.ratingBar.getRating());
                    listener.saveRating(
                            position,
                            list.get(position));
                } else {
                    Toast.makeText(mContext, "Rating can not be 0.", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public void setList(List<OrdersDataModel> ordersList) {
        this.list.addAll(ordersList);
        notifyDataSetChanged();
    }

    public void refreshAdapter(float rating, int pos) {
        list.get(pos).setRating_status(1);
        list.get(pos).setUserratting(rating);
        notifyItemChanged(pos);
    }

    public void refreshAdapter(List<OrdersDataModel> ordersList) {
        list = ordersList;
        notifyDataSetChanged();
    }

    public void clearList() {
        list.clear();
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void downloadFile(String url);

        void saveRating(int position, OrdersDataModel ordersDataModel);

        void makePayment(OrdersDataModel ordersDataModel);
    }

    private void setTimer(OrdersDataModel orderHistoryDataItem, int pos) {
        CountDownTimer timer;

        if (orderHistoryDataItem.getTimerObj() != null && orderHistoryDataItem.getTimerObj().getTotalMillis() > 0) {
            timer = timerMap.get(pos);
            if (timer == null) {
                timer = new CountDownTimer(orderHistoryDataItem.getTimerObj().getTotalMillis(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        TimerObj timessf =
                                TimeUtils.getTimeObjFromMillis(millisUntilFinished);

                        if (pos < list.size()) {
                            list.get(pos).setTimerObj(timessf);
                            notifyItemChanged(pos, timessf);
                        }

                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
            timerMap.put(pos, timer);
        }
    }

   /* private void gotoOrderDetail(View v, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("userid", list.get(position).getUsers().getId());
        bundle.putInt("orderid", list.get(position).getId());
        bundle.putInt("status", list.get(position).getStatus());
        bundle.putString("name", list.get(position).getUsers().getName());
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_out_right)
                .setExitAnim(R.anim.slide_in).setPopEnterAnim(0).setPopExitAnim(R.anim.slide_out1)
                .build();
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_message, bundle, options);
    }*/


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_date, txt_user_name, txt_order_id, txt_status, text_timer, btn_download, rating_label;
        private ImageView img;
        private CardView card_status, btn_save_rating;
        private LinearLayout ll_timer, ll_rating;
        private RelativeLayout rl_call, rl_whatsapp, rl_chat;
        private RatingBar ratingBar;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_user_name = itemView.findViewById(R.id.txt_user_name);
            img = itemView.findViewById(R.id.img_order);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_status = itemView.findViewById(R.id.txt_status);
            card_status = itemView.findViewById(R.id.card_status);
            ll_timer = itemView.findViewById(R.id.ll_timer);
            ll_rating = itemView.findViewById(R.id.ll_rating);
            text_timer = itemView.findViewById(R.id.text_timer);
            rl_call = itemView.findViewById(R.id.rl_call);
            rl_whatsapp = itemView.findViewById(R.id.rl_whatsapp);
            rl_chat = itemView.findViewById(R.id.rl_chat);
            btn_download = itemView.findViewById(R.id.btn_download);
            btn_save_rating = itemView.findViewById(R.id.btn_save_rating);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            rating_label = itemView.findViewById(R.id.rating_label);

        }
    }
}