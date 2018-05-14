package com.jdy.base.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.jdy.base.adapter.ListenerWithPosition.OnClickWithPositionListener;
import com.jdy.base.utils.ResourceUtil;
import com.jdy.base.utils.UtilTools;


public class BaseRecyclerHolder extends ViewHolder {
    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;
    private boolean needSetFont = false;
    private ListenerWithPosition listener;

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    /**
     * 得到item上的控件
     *
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(ResourceUtil.checkExistID(viewId));
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 给item上的TextView设置text
     *
     * @param textViewId TextView的ID
     * @param text       要设置上的内容
     * @return this
     */
    public BaseRecyclerHolder setText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText("");
        }
        setFont(tv);
        return this;
    }

    public BaseRecyclerHolder setOnClickListener(OnClickWithPositionListener clickListener,
                                                 @IdRes int... viewIds) {
        listener = new ListenerWithPosition(this);
        listener.setOnClickListener(clickListener);
        if (viewIds == null) {
            mConvertView.setOnClickListener(listener);
        } else {
            for (int id : viewIds) {
                getView(id).setOnClickListener(listener);
            }
        }
        return this;
    }


    public BaseRecyclerHolder setOnClickListener(OnClickWithPositionListener clickListener) {
        return setOnClickListener(clickListener, null);
    }

    private void setFont(TextView textView) {
        if (needSetFont)
            UtilTools.setFont(textView);
    }

    public void setNeedSetFont(boolean needSetFont) {
        this.needSetFont = needSetFont;
    }

    @Override
    public String toString() {
        return "item :" + position + ";  " + getClass().getName();
    }

    public void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    public void hiddenView(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }
}
