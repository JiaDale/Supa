package com.jdy.base.adapter;

import com.jdy.base.BaseFragment;

public class BaseTabItem {

    private BaseFragment mFragment;
    private int titleResource;
    private int imageResource;

    public BaseTabItem(BaseFragment fragment, int titleResource) {
        this(fragment,titleResource, 0);
    }

    public BaseTabItem(BaseFragment fragment, int titleResource, int imageResource) {
        this.mFragment = fragment;
        this.titleResource = titleResource;
        this.imageResource = imageResource;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    public int getTitle() {
        return titleResource;
    }

    public int getImage() {
        return imageResource;
    }
}
