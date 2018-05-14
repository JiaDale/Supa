package com.jdy.base.adapter;

import android.view.View;
import android.view.View.OnClickListener;

public class ListenerWithPosition implements OnClickListener {
    private BaseRecyclerHolder mHolder;
    private OnClickWithPositionListener mOnClickListener;

    public ListenerWithPosition(BaseRecyclerHolder holder) {
        this.mHolder = holder;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null)
            mOnClickListener.onClick(v, mHolder);
    }

    public interface OnClickWithPositionListener {
        void onClick(View v, BaseRecyclerHolder holder);
    }

    public void setOnClickListener(OnClickWithPositionListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
