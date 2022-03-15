package com.mamits.zini24user.ui.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mamits.zini24user.R;

public class CameraGalleryFragment extends BottomSheetDialogFragment {

    private Listener mListener;
    private RecyclerView recyclerView;
    private Context mContext;


    public CameraGalleryFragment(Listener mListener, Context mContext) {
        this.mListener = mListener;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera_gallery_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CameraGalleryAdapter());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface Listener {
        void onCameraGalleryClicked(int position);
    }


    public class CameraGalleryAdapter extends RecyclerView.Adapter<CameraGalleryAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.fragment_camera_gallery_item,
                            parent,
                            false
                    )
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (position == 0) {
                holder.text_name.setText("Camera");
                holder.image_icon.setImageResource(R.drawable.ic_camera_option);
            } else {
                holder.text_name.setText("Gallery");
                holder.image_icon.setImageResource(R.drawable.ic_gallery);
            }

            holder.itemView.setOnClickListener(view -> {
                mListener.onCameraGalleryClicked(position);
                dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private RelativeLayout relative_cam_gallery;
            private AppCompatTextView text_name;
            private AppCompatImageView image_icon;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                relative_cam_gallery = itemView.findViewById(R.id.relative_cam_gallery);
                text_name = itemView.findViewById(R.id.text_name);
                image_icon = itemView.findViewById(R.id.image_icon);
            }
        }

    }

}
