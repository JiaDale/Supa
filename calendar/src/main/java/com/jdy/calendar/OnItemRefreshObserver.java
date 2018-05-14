package com.jdy.calendar;

import com.jdy.base.adapter.BaseRecyclerHolder;

public interface OnItemRefreshObserver {
    void updateLastItem(BaseRecyclerHolder holder);

    void updateNextItem(BaseRecyclerHolder holder);
}
