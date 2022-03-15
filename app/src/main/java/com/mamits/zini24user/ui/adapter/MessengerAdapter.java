package com.mamits.zini24user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.chat.MessageDataModel;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.viewmodel.activity.MessageViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessengerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private View root;
    private List<MessageDataModel> mMessages;
    private MessageViewModel mViewModel;
    private static List<String> imgs = null;

    public MessengerAdapter(Context mContext, MessageViewModel mViewModel) {
        this.mContext = mContext;
        mMessages = new ArrayList<>();
        this.mViewModel = mViewModel;
        imgs = new ArrayList<>();
        imgs.add("png");
        imgs.add("jpg");
        imgs.add("jpeg");
        imgs.add("gif");
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getFrom_user() == mViewModel.getmDataManger().getCurrentUserId() ? 0 : 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            root = LayoutInflater.from(mContext).inflate(R.layout.message_from_item, parent, false);
            return new ChatFromViewHolder(root);
        } else {
            root = LayoutInflater.from(mContext).inflate(R.layout.message_to_item, parent, false);
            return new ChatToViewHolder(root);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mMessages.size() > 0) {
            MessageDataModel message = mMessages.get(position);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat finalFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

            String msgTime = "";
            Date date;
            try {
                date = sdf.parse(message.getCreated_at());
                if (date != null) {
                    msgTime = finalFormat.format(date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (holder.getItemViewType()) {
                case 0:
                    ChatFromViewHolder chatFromViewHolder = (ChatFromViewHolder) holder;
                    chatFromViewHolder.txt_msg.setVisibility(View.GONE);
                    chatFromViewHolder.card_img_msg.setVisibility(View.GONE);
                    chatFromViewHolder.txt_file_name.setVisibility(View.GONE);

                    if (message.getMessage() != null && !message.getMessage().equals("")) {
                        chatFromViewHolder.txt_msg.setText(message.getMessage());
                        chatFromViewHolder.txt_msg.setVisibility(View.VISIBLE);
                    }
                    if (message.getAttachment() != null && !message.getAttachment().equals("")) {

                        if (!imgs.contains(message.getFile_type().toLowerCase())) {
                            String[] path = message.getAttachment().split("/");
                            String name = path[path.length - 1];
                            try {
                                chatFromViewHolder.txt_file_name.setText(name.substring(name.lastIndexOf(".")));
                            } catch (Exception e) {
                                chatFromViewHolder.txt_file_name.setText("");
                                e.printStackTrace();
                            }
                            chatFromViewHolder.txt_file_name.setVisibility(View.VISIBLE);
                            chatFromViewHolder.img_msg.setImageResource(R.drawable.rectangle_box);
                            chatFromViewHolder.card_img_msg.setVisibility(View.VISIBLE);

                        } else {
                            Glide.with(mContext).load(message.getAttachment())
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            chatFromViewHolder.progress_bar.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            chatFromViewHolder.progress_bar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    }).into(chatFromViewHolder.img_msg);

                        }

                        chatFromViewHolder.card_img_msg.setVisibility(View.VISIBLE);
                    }

                    chatFromViewHolder.txt_time.setText(msgTime);

                    chatFromViewHolder.card_img_msg.setOnClickListener(v -> {
                        if (message.getAttachment() != null && !message.getAttachment().equals("")) {
                            openFile(message.getAttachment());
                        }
                    });
                    break;
                case 2:
                    ChatToViewHolder chatToViewHolder = (ChatToViewHolder) holder;
                    chatToViewHolder.txt_msg.setVisibility(View.GONE);
                    chatToViewHolder.card_img_msg.setVisibility(View.GONE);
                    chatToViewHolder.txt_file_name.setVisibility(View.GONE);

                    if (message.getMessage() != null && !message.getMessage().equals("")) {
                        chatToViewHolder.txt_msg.setText(message.getMessage());
                        chatToViewHolder.txt_msg.setVisibility(View.VISIBLE);
                    }
                    if (message.getAttachment() != null && !message.getAttachment().equals("")) {
                        if (!imgs.contains(message.getFile_type().toLowerCase())) {
                            String[] path = message.getAttachment().split("/");
                            String name = path[path.length - 1];
                            try {
                                chatToViewHolder.txt_file_name.setText(name.substring(name.lastIndexOf(".")).toUpperCase());
                            } catch (Exception e) {
                                chatToViewHolder.txt_file_name.setText("");
                                e.printStackTrace();
                            }
                            chatToViewHolder.txt_file_name.setVisibility(View.VISIBLE);
                            chatToViewHolder.img_msg.setImageResource(R.drawable.rectangle_box);
                            chatToViewHolder.card_img_msg.setVisibility(View.VISIBLE);

                        } else {
                            Glide.with(mContext).load(message.getAttachment())
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            chatToViewHolder.progress_bar.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            chatToViewHolder.progress_bar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(chatToViewHolder.img_msg);
                        }


                        chatToViewHolder.card_img_msg.setVisibility(View.VISIBLE);
                    }

                    chatToViewHolder.txt_time.setText(msgTime);

                    chatToViewHolder.card_img_msg.setOnClickListener(v -> {
                        if (message.getAttachment() != null && !message.getAttachment().equals("")) {
                            openFile(message.getAttachment());
                        }
                    });
                    break;
            }

        }
    }

    private void openFile(String attachment) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment));
        mContext.startActivity(browserIntent);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public void setList(List<MessageDataModel> mMessages) {
        this.mMessages = mMessages;
        notifyDataSetChanged();
    }

    public static class ChatFromViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_msg, txt_time, txt_file_name;
        private ImageView img_msg;
        private CardView card_img_msg;
        private ProgressBar progress_bar;

        public ChatFromViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_file_name = itemView.findViewById(R.id.txt_file_name);
            txt_msg = itemView.findViewById(R.id.txt_msg);
            txt_time = itemView.findViewById(R.id.txt_time);
            img_msg = itemView.findViewById(R.id.img_msg);
            card_img_msg = itemView.findViewById(R.id.card_img_msg);
            progress_bar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public static class ChatToViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_msg, txt_time, txt_file_name;
        private ImageView img_msg;
        private CardView card_img_msg;
        private ProgressBar progress_bar;

        public ChatToViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_msg = itemView.findViewById(R.id.txt_msg);
            txt_file_name = itemView.findViewById(R.id.txt_file_name);
            txt_time = itemView.findViewById(R.id.txt_time);
            img_msg = itemView.findViewById(R.id.img_msg);
            card_img_msg = itemView.findViewById(R.id.card_img_msg);
            progress_bar = itemView.findViewById(R.id.progress_bar);
        }
    }
}
