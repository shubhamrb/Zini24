package com.mamits.zini24user.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.mamits.zini24user.R;
import com.mamits.zini24user.data.model.home.BannerListItem;

public class BannerPagerFragment extends Fragment {
    int position;
    BannerListItem item;
    private ImageView image_banner;
    private Context context;

    public BannerPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static BannerPagerFragment newInstance(int pos, BannerListItem item) {
        BannerPagerFragment fragment = new BannerPagerFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putSerializable("obj", item);
        fragment.setArguments(args);
        return fragment;
    }

    public static BannerPagerFragment newInstance(int pos, String item) {
        BannerPagerFragment fragment = new BannerPagerFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putString("item", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("pos");
            item = (BannerListItem) getArguments().getSerializable("obj");
            if (item == null) {
                item = new BannerListItem();
                item.setImage(getArguments().getString("item"));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_banner_pager, container, false);
        image_banner = root.findViewById(R.id.image_banner);
        Glide.with(context).load(item.getImage()).into(image_banner);

//        new ClickShrinkEffect(image_banner);
        image_banner.setOnClickListener(view -> {
            if (item!=null && item.getProduct_id()!=null){
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", Integer.parseInt(item.getProduct_id()));
                Navigation.findNavController(view).navigate(R.id.nav_stores, bundle);
            }
        });
        return root;
    }
}