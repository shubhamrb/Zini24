package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.form.ValueModel;
import com.mamits.zini24user.ui.utils.listeners.OnItemClickListener;

import java.util.List;

public class ChkboxRadiobtnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Activity activity;
    private List<ValueModel> list;
    private int VIEWTYPE_CHKBOX = 1;
    private int VIEWTYPE_RADIOBTN = 2;
    private OnItemClickListener listener;
    private String type, selectedItems;
    private int position;

    public ChkboxRadiobtnAdapter(Context mContext, OnItemClickListener listener, List<ValueModel> chkboxItemList, String type, String selectedItems, int position) {
        this.mContext = mContext;
        activity = ((Activity) mContext);
        list = chkboxItemList;
        this.listener = listener;
        this.type = type;
        this.selectedItems = selectedItems;
        this.position = position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (type.equals("chkbox")) {
            return VIEWTYPE_CHKBOX;
        } else {
            return VIEWTYPE_RADIOBTN;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_CHKBOX) {
            return new ChkBoxViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_chkbox,
                            parent,
                            false
                    )
            );
        } else {
            return new RadioBtnViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_radiobtn,
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChkBoxViewHolder) {
            ChkBoxViewHolder checkboxViewHolder = (ChkBoxViewHolder) holder;

            checkboxViewHolder.chkbox_item.setText(list.get(position).getValue());
            checkboxViewHolder.chkbox_item.setChecked(selectedItems.contains(list.get(position).getValue()));

            checkboxViewHolder.chkbox_item.setOnCheckedChangeListener((compoundButton, b) -> listener.onClick(this.position, checkboxViewHolder.chkbox_item, list.get(position)));

        } else if (holder instanceof RadioBtnViewHolder) {
            RadioBtnViewHolder radioBtnViewHolder = (RadioBtnViewHolder) holder;
            radioBtnViewHolder.rbtn_item.setText(list.get(position).getValue());
            radioBtnViewHolder.rbtn_item.setChecked(selectedItems.contains(list.get(position).getValue()));

            radioBtnViewHolder.rbtn_item.setOnCheckedChangeListener((compoundButton, b) -> {
                listener.onClick(this.position, radioBtnViewHolder.rbtn_item, list.get(position));
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ChkBoxViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkbox_item;

        public ChkBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            chkbox_item = itemView.findViewById(R.id.chkbox_item);
        }
    }

    public static class RadioBtnViewHolder extends RecyclerView.ViewHolder {
        private RadioButton rbtn_item;

        public RadioBtnViewHolder(@NonNull View itemView) {
            super(itemView);
            rbtn_item = itemView.findViewById(R.id.rbtn_item);
        }
    }
}
