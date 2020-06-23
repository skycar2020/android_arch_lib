package com.mvvm.demo.core.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemListAdapter<T> extends RecyclerView.Adapter<SimpleItemListAdapter.CustomViewHolder> {

    List<T> mDataList = new ArrayList<>();

    private int mLayoutId = -1;
    private int mDataVariableId = -1;

    public SimpleItemListAdapter() {
    }


    public SimpleItemListAdapter(int layoutId, int dataVariableId) {
        mLayoutId = layoutId;
        mDataVariableId = dataVariableId;
    }

    public void setDataList(List<T> dataList) {
        if (dataList != null) {
            this.mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    public void addDataList(List<T> dataList) {
        if (dataList != null) {
            int ps = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(ps, dataList.size());
        }
    }


    public void setLayoutId(int layoutId) {
        mLayoutId = layoutId;
    }

    public void setDataVariableId(int dataVariableId) {
        mDataVariableId = dataVariableId;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutId != -1 && mDataVariableId != -1) {
            ViewDataBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
            return new CustomViewHolder(binding);
        } else {
            throw new IllegalArgumentException("Error: layoutId and dataVariableId is invalid");
        }
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        T itemInfo = mDataList.get(position);
        holder.mBinding.setVariable(mDataVariableId, itemInfo);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding mBinding;

        public CustomViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}