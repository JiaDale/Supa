package com.jdy.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends Adapter<BaseRecyclerHolder> {
    protected Context mContext;
    protected List<T> mData;
    protected int layoutId;
    protected T[] mArray;

    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        this(context, list, 0);
    }

    public BaseRecyclerViewAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.mData = (list == null ? new ArrayList<T>() : list);
        this.layoutId = layoutId;
    }

    public BaseRecyclerViewAdapter(Context context, T[] array, int layoutId) {
        this.mContext = context;
        this.mArray = array;
        this.layoutId = layoutId;
    }

    public void addData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        mData.clear();
        addData(data);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new BaseRecyclerHolder(onCreateViewHolder(view, viewType));
    }

    protected View onCreateViewHolder(View view, int viewType) {
        return view;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (holder instanceof BaseRecyclerHolder) {
            BaseRecyclerHolder baseViewHolder = holder;
            baseViewHolder.position = position;
            if (mData != null) {
                convert(baseViewHolder, mData.get(position));
            } else if (mArray != null) {
                convert(holder, mArray[position]);
            }
        }
    }

    protected abstract void convert(BaseRecyclerHolder holder, T bean);

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : (mArray != null ? mArray.length : 0);
    }
}
