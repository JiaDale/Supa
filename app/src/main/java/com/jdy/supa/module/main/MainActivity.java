package com.jdy.supa.module.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdy.base.BaseActivity;
import com.jdy.base.adapter.BaseTabItem;
import com.jdy.base.adapter.BaseViewPagerAdapter;
import com.jdy.supa.R;
import com.jdy.supa.module.main.collect.CollectFragment;
import com.jdy.supa.module.main.home.HomeFragment;
import com.jdy.supa.module.main.me.MeFragment;
import com.jdy.supa.module.main.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    public static final String COLLECT = "CollectFragment";
    public static final String NEWS = "NEWSFragment";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<BaseTabItem> mTabItems;

    @Override
    public int getRootLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTabItems = new ArrayList<>();
        mTabItems.add(new BaseTabItem(new HomeFragment(), R.string.main_home, R.drawable.tab_item_home));
        mTabItems.add(new BaseTabItem(new CollectFragment(COLLECT), R.string.main_collect, R.drawable.tab_item_collect));
        mTabItems.add(new BaseTabItem(new CollectFragment(NEWS), R.string.main_news, R.drawable.tab_item_news));
        mTabItems.add(new BaseTabItem(new OrderFragment(), R.string.main_order, R.drawable.tab_item_order));
        mTabItems.add(new BaseTabItem(new MeFragment(), R.string.main_me, R.drawable.tab_item_me));
    }

    @Override
    public void initView() {
        mTabLayout = findViewById(R.id.main_TabLayout);
        mViewPager = findViewById(R.id.main_ViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mTabItems.size());
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(this, mTabItems);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(viewPagerAdapter.getTabView(i));
            }
        }
    }

    @Override
    public void onClick(View v) {
    }

    private class MainViewPagerAdapter extends BaseViewPagerAdapter {

        public MainViewPagerAdapter(BaseActivity context, List<BaseTabItem> tabItems) {
            super(context, tabItems);
        }

        public View getTabView(int position) {
            BaseTabItem tabItem = mTabItems.get(position);
            View view = LayoutInflater.from(mContext).inflate(R.layout.custom_view_tab_item, null);
            TextView title = view.findViewById(R.id.tab_TextView);
            title.setText(tabItem.getTitle());
            ImageView image = view.findViewById(R.id.tab_ImageView);
            image.setImageResource(tabItem.getImage());
            return view;
        }
    }
}
