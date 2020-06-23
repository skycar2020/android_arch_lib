package com.mvvm.demo.core.presentation;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MultiTypeItemListAdapter<T> extends RecyclerView.Adapter<MultiTypeItemListAdapter.InnerViewHolder> {

    private List<T> mDataList = new ArrayList<>();
    //key:CellTypeId, value: CellLayoutConfig
    private SparseArray mTypeLayoutMapping;


    private CellLayoutTypeDelegate mLayoutTypeDelegate;

    public MultiTypeItemListAdapter(CellLayoutTypeDelegate cellLayoutTypeDelegate) {
        this.mTypeLayoutMapping = new SparseArray();
        this.mLayoutTypeDelegate = cellLayoutTypeDelegate;
    }

    public MultiTypeItemListAdapter(List<CellLayoutConfig> layoutConfigs, CellLayoutTypeDelegate cellLayoutTypeDelegate) {
        this(cellLayoutTypeDelegate);
        setAllCellType(layoutConfigs);
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

    public void setAllCellType(List<CellLayoutConfig> layoutConfigs) {
        for(CellLayoutConfig item: layoutConfigs){
            this.mTypeLayoutMapping.append(item.getLayoutTypeId(), item);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
       return mLayoutTypeDelegate.getCellLayoutType(position);
    }

    @Override
    public InnerViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        if (mTypeLayoutMapping != null) {
            int layoutId = -1;
            CellLayoutConfig config = (CellLayoutConfig)mTypeLayoutMapping.get(type);
            if(config != null){
                layoutId = config.getLayoutResId();
            }
            if(layoutId == -1){
                throw new IllegalArgumentException("Error: LayoutId is invalid");
            }
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
            return new InnerViewHolder(binding);
        } else {
            throw new IllegalArgumentException("Error: TypeLayoutMapping is null");
        }
    }

    @Override
    public void onBindViewHolder(InnerViewHolder holder, int position) {
        T itemData = mDataList.get(position);
        int type = getItemViewType(position);
        int dataVariableId = ((CellLayoutConfig)mTypeLayoutMapping.get(type)).getDataVariableId();
        holder.mBinding.setVariable(dataVariableId, mLayoutTypeDelegate.transformCellData(type, position, itemData));
        holder.mBinding.executePendingBindings();
    }

    static class InnerViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        public InnerViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }

}
