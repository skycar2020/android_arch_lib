package com.mvvm.demo.core.presentation;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mvvm.demo.R;

public class LayoutBindingAdapter {

    @BindingAdapter("dataList")
    public static void setRecyclerViewDataList(RecyclerView recyclerView , List dataList){
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if(adapter instanceof SimpleItemListAdapter){
            SimpleItemListAdapter mAdapter = (SimpleItemListAdapter) adapter;
            mAdapter.setDataList(dataList);
        } else if(adapter instanceof MultiTypeItemListAdapter) {
            MultiTypeItemListAdapter mAdapter = (MultiTypeItemListAdapter) adapter;
            mAdapter.setDataList(dataList);
        } else {
            throw new IllegalArgumentException("Adapter Type is unrecognized");
        }
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url){
        Glide.with(view.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.mipmap.ic_launcher)
                .into(view);
    }

}