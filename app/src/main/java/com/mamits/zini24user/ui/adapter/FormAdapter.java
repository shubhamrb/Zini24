package com.mamits.zini24user.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.form.CustomFieldObject;
import com.mamits.zini24user.data.model.form.ValueModel;
import com.mamits.zini24user.ui.customviews.CustomInputEditText;
import com.mamits.zini24user.ui.customviews.CustomTextView;
import com.mamits.zini24user.ui.utils.listeners.OnItemClickListener;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Activity activity;
    private List<CustomFieldObject> list;
    private int VIEWTYPE_TEXT = 1;
    private int VIEWTYPE_MULT_TEXT = 2;
    private int VIEWTYPE_DROPDOWN = 3;
    private int VIEWTYPE_CHECKBOX = 4;
    private int VIEWTYPE_RADIOBTN = 5;
    private int VIEWTYPE_BUTTON = 6;
    private int VIEWTYPE_FILEPICKER = 7;
    private int VIEWTYPE_LOCATION = 8;
    private boolean isAllowedForListener = false;
    private OnItemClickListener listener;

    public FormAdapter(Context context, List<CustomFieldObject> storeList, OnItemClickListener listener) {
        this.mContext = context;
        activity = ((Activity) context);
        list = storeList;
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if ((!list.get(position).getId().equals("location"))
                && (list.get(position).getFieldType().equals("text")
                || list.get(position).getFieldType().equals("number"))) {
            return VIEWTYPE_TEXT;
        } else if (list.get(position).getFieldType().equals("textarea")) {
            return VIEWTYPE_MULT_TEXT;
        } else if (list.get(position).getFieldType().equals("location")
                || list.get(position).getFieldType().equals("date")) {
            return VIEWTYPE_BUTTON;
        } else if (list.get(position).getFieldType().equals("checkbox")) {
            return VIEWTYPE_CHECKBOX;
        } else if (list.get(position).getFieldType().equals("file")) {
            return VIEWTYPE_FILEPICKER;
        } else if (list.get(position).getFieldType().equals("radio")) {
            return VIEWTYPE_RADIOBTN;
        } else if (list.get(position).getFieldType().equals("select")) {
            return VIEWTYPE_DROPDOWN;
        }
        return VIEWTYPE_FILEPICKER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEWTYPE_TEXT) {
            return new TextViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_edittext,
                            parent,
                            false
                    )
            );
        } else if (viewType == VIEWTYPE_MULT_TEXT) {
            return new TextMultViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_edittext,
                            parent,
                            false
                    )
            );
        } else if (viewType == VIEWTYPE_DROPDOWN) {
            return new DropdownViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_spinner,
                            parent,
                            false
                    )
            );
        } else if (viewType == VIEWTYPE_CHECKBOX) {
            return new CheckboxViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_chkbox_custfield,
                            parent,
                            false
                    )
            );
        } else if (viewType == VIEWTYPE_RADIOBTN) {
            return new RadiobtnViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_chkbox_custfield,
                            parent,
                            false
                    )
            );
        } else if (viewType == VIEWTYPE_BUTTON) {
            return new ButtonViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_btn,
                            parent,
                            false
                    )
            );
        } else if (viewType == VIEWTYPE_FILEPICKER) {
            return new FilePickerViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_fileupload,
                            parent,
                            false
                    )
            );
        } else {
            return new TextViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_fileupload,
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FilePickerViewHolder) {
            FilePickerViewHolder filePickHolder = (FilePickerViewHolder) holder;
            filePickHolder.ll_file_upload_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);

            if (!list.get(position).getAnsValue().isEmpty()) {
                filePickHolder.ll_selected_file.setVisibility(View.VISIBLE);
                filePickHolder.text_filename.setText(list.get(position).getAnsValue());
            } else {
                filePickHolder.ll_selected_file.setVisibility(View.GONE);
            }

            filePickHolder.text_file_label.setText(list.get(position).getName());
            filePickHolder.text_file_upload_label.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());

            filePickHolder.text_file_upload.setOnClickListener(view -> {
                listener.onClick(position, filePickHolder.text_file_upload, list.get(position));

            });

            filePickHolder.image_file_close.setOnClickListener(view -> {
                listener.onClick(position, filePickHolder.image_file_close, list.get(position));

            });

        }
        else if (holder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;

            textViewHolder.ll_edittext_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);

            textViewHolder.text_edt_upload_label.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());
            if (list.get(position).getFieldType().equals("text")) {
                textViewHolder.edt_custom_field.setHint(list.get(position).getName());
                textViewHolder.edt_custom_field.setText(list.get(position).getAnsValue());
                textViewHolder.edt_custom_field.setVisibility(View.VISIBLE);
                textViewHolder.edt_custom_field_number.setVisibility(View.GONE);
                textViewHolder.edt_custom_field_mult.setVisibility(View.GONE);
            } else if (list.get(position).getFieldType().equals("number")) {
                textViewHolder.edt_custom_field.setVisibility(View.GONE);
                textViewHolder.edt_custom_field_number.setHint(list.get(position).getName());
                textViewHolder.edt_custom_field_number.setText(list.get(position).getAnsValue());
                textViewHolder.edt_custom_field_number.setVisibility(View.VISIBLE);
                textViewHolder.edt_custom_field_mult.setVisibility(View.GONE);
            } else {
                textViewHolder.edt_custom_field.setVisibility(View.GONE);
                textViewHolder.edt_custom_field_mult.setVisibility(View.VISIBLE);
                textViewHolder.edt_custom_field_number.setVisibility(View.GONE);
                textViewHolder.edt_custom_field_mult.setHint(list.get(position).getName());
            }

            textViewHolder.edt_custom_field.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (list.get(position).getFieldType().equals("text")) {

                        CustomFieldObject customFieldObj = list.get(position);

                        customFieldObj.setAnsValue(s.toString());
                        listener.onClick(
                                position,
                                textViewHolder.edt_custom_field,
                                customFieldObj
                        );
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            textViewHolder.edt_custom_field_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (list.get(position).getFieldType().equals("number")) {

                        CustomFieldObject customFieldObj = list.get(position);
                        customFieldObj.setAnsValue(s.toString());
                        listener.onClick(
                                position,
                                textViewHolder.edt_custom_field,
                                customFieldObj
                        );
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
        else if (holder instanceof TextMultViewHolder) {
            TextMultViewHolder textMultViewHolder = (TextMultViewHolder) holder;

            textMultViewHolder.ll_edittext_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);

            textMultViewHolder.text_edt_upload_label.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());

            if (list.get(position).getFieldType().equals("text")) {
                textMultViewHolder.edt_custom_field.setHint(list.get(position).getName());
                textMultViewHolder.edt_custom_field.setText(list.get(position).getAnsValue());
                textMultViewHolder.edt_custom_field.setVisibility(View.VISIBLE);
                textMultViewHolder.edt_custom_field_number.setVisibility(View.GONE);
                textMultViewHolder.edt_custom_field_mult.setVisibility(View.GONE);
            } else if (list.get(position).getFieldType().equals("number")) {
                textMultViewHolder.edt_custom_field.setVisibility(View.GONE);
                textMultViewHolder.edt_custom_field.setHint(list.get(position).getName());
                textMultViewHolder.edt_custom_field.setText(list.get(position).getAnsValue());
                textMultViewHolder.edt_custom_field_number.setVisibility(View.VISIBLE);
                textMultViewHolder.edt_custom_field_mult.setVisibility(View.GONE);
            } else {
                textMultViewHolder.edt_custom_field.setVisibility(View.GONE);
                textMultViewHolder.edt_custom_field_mult.setVisibility(View.VISIBLE);
                textMultViewHolder.edt_custom_field_number.setVisibility(View.GONE);
                textMultViewHolder.edt_custom_field_mult.setHint(list.get(position).getName());
            }

            textMultViewHolder.edt_custom_field_mult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    CustomFieldObject customFieldObj = list.get(position);
                    customFieldObj.setAnsValue(s.toString());
                    listener.onClick(
                            position,
                            textMultViewHolder.edt_custom_field_mult,
                            customFieldObj
                    );
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
        else if (holder instanceof DropdownViewHolder) {
            DropdownViewHolder dropdownViewHolder = (DropdownViewHolder) holder;

            dropdownViewHolder.ll_spn_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);

            if (!list.get(position).getValue().isEmpty()) {
                dropdownViewHolder.text_spn_title.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());

                dropdownViewHolder.bind(mContext, list.get(position), listener, dropdownViewHolder, position);

            }
        }
        else if (holder instanceof CheckboxViewHolder) {
            CheckboxViewHolder checkboxViewHolder = (CheckboxViewHolder) holder;

            checkboxViewHolder.ll_radio_checkbox_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);
            List<ValueModel> chkboxItemList = list.get(position).getValue();

            checkboxViewHolder.text_chkbox_title.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());

            checkboxViewHolder.rv_chkbox.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            checkboxViewHolder.rv_chkbox.setAdapter(new ChkboxRadiobtnAdapter(
                    mContext,
                    listener, chkboxItemList,
                    "chkbox",
                    list.get(position).getAnsValue(),
                    position
            ));
        }
        else if (holder instanceof RadiobtnViewHolder) {
            RadiobtnViewHolder radiobtnViewHolder = (RadiobtnViewHolder) holder;

            radiobtnViewHolder.ll_radio_checkbox_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);

            radiobtnViewHolder.text_chkbox_title.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());
            List<ValueModel> radiobtnItemList = list.get(position).getValue();

            radiobtnViewHolder.rv_chkbox.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            radiobtnViewHolder.rv_chkbox.setAdapter(new ChkboxRadiobtnAdapter(
                    mContext,
                    listener, radiobtnItemList,
                    "radiobtn",
                    list.get(position).getAnsValue(),
                    position
            ));
        }
        else if (holder instanceof ButtonViewHolder) {
            ButtonViewHolder buttonViewHolder = (ButtonViewHolder) holder;

            buttonViewHolder.ll_btn_container.setVisibility(list.get(position).isVisible() ? View.VISIBLE : View.GONE);
            buttonViewHolder.text_btn_label.setText(list.get(position).getIsRequired().equals("Yes")?list.get(position).getLabel()+" *":list.get(position).getLabel());
            buttonViewHolder.btn.setText(!list.get(position).getAnsValue().isEmpty() ? list.get(position).getAnsValue() : list.get(position).getName());

            buttonViewHolder.btn.setOnClickListener(view -> {
                listener.onClick(position, buttonViewHolder.btn, list.get(position));
            });
        }

        if (position == list.size() - 1) {
            isAllowedForListener = false;
        }
    }

   public void onRefreshAdapter(List<CustomFieldObject>list, boolean flag) {
        this.list = list;
        isAllowedForListener = flag;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_edittext_container;
        private CustomTextView text_edt_upload_label;
        private CustomInputEditText edt_custom_field, edt_custom_field_number, edt_custom_field_mult;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_edittext_container = itemView.findViewById(R.id.ll_edittext_container);
            text_edt_upload_label = itemView.findViewById(R.id.text_edt_upload_label);
            edt_custom_field = itemView.findViewById(R.id.edt_custom_field);
            edt_custom_field_number = itemView.findViewById(R.id.edt_custom_field_number);
            edt_custom_field_mult = itemView.findViewById(R.id.edt_custom_field_mult);
        }
    }

    public static class TextMultViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_edittext_container;
        private CustomTextView text_edt_upload_label;
        private CustomInputEditText edt_custom_field, edt_custom_field_number, edt_custom_field_mult;

        public TextMultViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_edittext_container = itemView.findViewById(R.id.ll_edittext_container);
            text_edt_upload_label = itemView.findViewById(R.id.text_edt_upload_label);
            edt_custom_field = itemView.findViewById(R.id.edt_custom_field);
            edt_custom_field_number = itemView.findViewById(R.id.edt_custom_field_number);
            edt_custom_field_mult = itemView.findViewById(R.id.edt_custom_field_mult);
        }
    }

    public static class DropdownViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_spn_container;
        private CustomTextView text_spn_title;
        public TextView spn_text;

        public DropdownViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_spn_container = itemView.findViewById(R.id.ll_spn_container);
            text_spn_title = itemView.findViewById(R.id.text_spn_title);
            spn_text = itemView.findViewById(R.id.spn_text);
        }

        public void bind(Context mContext, CustomFieldObject customFieldObject, OnItemClickListener listener, DropdownViewHolder dropdownViewHolder, int position) {
            List<ValueModel> dropdownItemList = customFieldObject.getValue();

            if (!customFieldObject.getAnsValue().isEmpty()) {
                dropdownViewHolder.spn_text.setText(customFieldObject.getAnsValue());
            } else {
                dropdownViewHolder.spn_text.setText("Select");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item);

            if (dropdownItemList.size() > 0) {
                for (ValueModel s : dropdownItemList) {
                    arrayAdapter.add(s.getValue());
                }
            }


            builder.setAdapter(arrayAdapter, (dialogInterface, pos) -> {
                listener.onClick(
                        position,
                        dropdownViewHolder.spn_text,
                        dropdownItemList.get(pos)//customFieldObj
                );
            });

            // create and show the alert dialog
            Dialog dialog = builder.create();

            dialog.setCanceledOnTouchOutside(true);
            dropdownViewHolder.spn_text.setOnClickListener(view -> {
                dialog.show();
            });
        }

    }

    public static class CheckboxViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_radio_checkbox_container;
        private CustomTextView text_chkbox_title;
        private RecyclerView rv_chkbox;

        public CheckboxViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_radio_checkbox_container = itemView.findViewById(R.id.ll_radio_checkbox_container);
            text_chkbox_title = itemView.findViewById(R.id.text_chkbox_title);
            rv_chkbox = itemView.findViewById(R.id.rv_chkbox);
        }
    }

    public static class RadiobtnViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_radio_checkbox_container;
        private CustomTextView text_chkbox_title;
        private RecyclerView rv_chkbox;

        public RadiobtnViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_radio_checkbox_container = itemView.findViewById(R.id.ll_radio_checkbox_container);
            text_chkbox_title = itemView.findViewById(R.id.text_chkbox_title);
            rv_chkbox = itemView.findViewById(R.id.rv_chkbox);
        }
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_btn_container;
        private CustomTextView text_btn_label, btn;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_btn_container = itemView.findViewById(R.id.ll_btn_container);
            text_btn_label = itemView.findViewById(R.id.text_btn_label);
            btn = itemView.findViewById(R.id.btn);
        }
    }

    public static class FilePickerViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout ll_file_upload_container;
        private LinearLayout ll_selected_file;
        private ImageView image_file_close;
        private CustomTextView text_filename, text_file_label, text_file_upload_label, text_file_upload;

        public FilePickerViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_file_upload_container = itemView.findViewById(R.id.ll_file_upload_container);
            ll_selected_file = itemView.findViewById(R.id.ll_selected_file);
            text_filename = itemView.findViewById(R.id.text_filename);
            text_file_label = itemView.findViewById(R.id.text_file_label);
            text_file_upload_label = itemView.findViewById(R.id.text_file_upload_label);
            text_file_upload = itemView.findViewById(R.id.text_file_upload);
            image_file_close = itemView.findViewById(R.id.image_file_close);
        }
    }
}
