package com.jdy.base.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ViewPager通用
 *
 * Created by Dale on 2017/9/25.
 * 项目名：Supa
 * 描述：
 */
public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    protected Context mContext;
    protected List<BaseTabItem> mTabItems;

    public BaseViewPagerAdapter(FragmentActivity context, List<BaseTabItem> tabItems) {
        super(context.getSupportFragmentManager());
        mContext = context;
        mTabItems = tabItems;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mTabItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(mTabItems.get(position).getTitle());
    }
}
