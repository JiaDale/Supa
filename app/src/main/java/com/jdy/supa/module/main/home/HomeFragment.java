package com.jdy.supa.module.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.jdy.base.BaseFragment;
import com.jdy.base.adapter.BaseTabItem;
import com.jdy.base.adapter.BaseViewPagerAdapter;
import com.jdy.base.utils.DateUtil;
import com.jdy.base.utils.ResourceUtil;
import com.jdy.base.utils.SharedPreferencesUtil;
import com.jdy.calendar.utils.CalendarUtil;
import com.jdy.calendar.utils.LunarCalendarConfig;
import com.jdy.supa.R;
import com.jdy.supa.module.main.home.abroad.AbroadFragment;
import com.jdy.supa.module.main.home.brilliant.BrilliantFragment;
import com.jdy.supa.module.main.home.dialog.CalendarDialog;
import com.jdy.supa.module.main.home.dialog.CalendarDialog.OnDatePickerConfirm;
import com.jdy.supa.module.main.home.dialog.PersonCountDialog;
import com.jdy.supa.module.main.home.dialog.PersonCountDialog.OnDialogItemClickListener;
import com.jdy.supa.module.main.home.discover.DiscoverFragment;
import com.jdy.supa.module.main.home.selling.SellingFragment;
import com.jdy.supa.module.main.home.villa.VillaFragment;
import com.jdy.supa.module.search.SearchActivity;
import com.jdy.supa.utils.LocateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends BaseFragment implements OnOffsetChangedListener {
    private static final float HIDE_START_PERCENT = 0.15f;
    private static final float HIDE_END_PERCENT = 0.25f;
    private static final float HIDE_COLLAPSING_PERCENT = 0.75f;
    private AppBarLayout mAppBarLayout;
    private ImageView appBarImage;
    private FrameLayout mCollapsing;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<BaseTabItem> mTabItems;
    private TextView searchLocation, peopleCount;
    private View locateLoading, locateAction;
    private Handler handler = new Handler();
    protected LocateUtil mLocation;

    @Override
    public int getRootLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTabItems = new ArrayList<>();
        mTabItems.add(new BaseTabItem(new BrilliantFragment(), R.string.home_brilliant));
        mTabItems.add(new BaseTabItem(new VillaFragment(), R.string.home_villa));
        mTabItems.add(new BaseTabItem(new AbroadFragment(), R.string.home_abroad));
        mTabItems.add(new BaseTabItem(new DiscoverFragment(), R.string.home_discover));
        mTabItems.add(new BaseTabItem(new SellingFragment(), R.string.home_selling));
    }


    @Override
    public void initView() {
        appBarImage = getView(R.id.home_app_frame_image);
        mAppBarLayout = getView(R.id.home_app_bar);
        mCollapsing = getView(R.id.home_app_collapsing_frame);
        mToolbar = getView(R.id.home_app_collapsing_toolbar);
        mTabLayout = getView(R.id.home_app_tabs);
        mViewPager = getView(R.id.home_ViewPager);
        searchLocation = getView(R.id.search_location);
        locateAction = getView(R.id.search_locate_action);
        locateLoading = getView(R.id.search_locate_loading);
        peopleCount = getView(R.id.search_people_count);

        //预加载
        mViewPager.setOffscreenPageLimit(mTabItems.size());
        mViewPager.setAdapter(new BaseViewPagerAdapter(this.getActivity(), mTabItems));
        mTabLayout.setupWithViewPager(mViewPager);
        mLocation = LocateUtil.getInstance(getActivity());
    }

    @Override
    public void bendData() {
        Date today = new Date(), tomorrow = DateUtil.getNextDate(1);
        String start = CalendarUtil.getMonth(today) + ResourceUtil.getString(R.string.month) + CalendarUtil.getDayOfMonth(today) + ResourceUtil.getString(R.string.day);
        String end = CalendarUtil.getMonth(tomorrow) + ResourceUtil.getString(R.string.month) + CalendarUtil.getDayOfMonth(tomorrow) + ResourceUtil.getString(R.string.day);
        setText(R.id.search_date_start, start);
        setText(R.id.search_date_end, end);
        setText(R.id.search_date_count, ResourceUtil.getString(R.string.search_date_count));
        setText(R.id.search_date_start_label, getLabel(today.getTime()));
        setText(R.id.search_date_end_label, getLabel(tomorrow.getTime()));
        String pCount = SharedPreferencesUtil.getInt(PersonCountDialog.FLAG, 1) + ResourceUtil.getString(R.string.search_people);
        peopleCount.setText(pCount);
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.search_action);
        locateAction.setOnClickListener(this);
        searchLocation.setOnClickListener(this);
        setOnClickListener(R.id.search_date);
        setOnClickListener(R.id.search_people);
        mAppBarLayout.addOnOffsetChangedListener(this);
        mToolbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_action:
                startActivity(SearchActivity.class);
                break;
            case R.id.search_locate_action:
                locate();
                break;
            case R.id.search_location:
                showToast("跳转....");
                break;
            case R.id.search_date:
                showDateDialog();
                break;
            case R.id.search_people:
                showPersonCountDialog();
                break;
            case R.id.home_app_collapsing_toolbar:
                showToast("toolbar ...");
                break;
        }
    }

    private void showDateDialog() {
        CalendarDialog calendarDialog = new CalendarDialog(getActivity());
        calendarDialog.setDatePickerConfirm(new OnDatePickerConfirm() {
            @Override
            public void confirm(long startTime, long endTime, String count) {
                String start = CalendarUtil.getMonth(startTime) + ResourceUtil.getString(R.string.month) + CalendarUtil.getDayOfMonth(startTime) + ResourceUtil.getString(R.string.day);
                String end = CalendarUtil.getMonth(endTime) + ResourceUtil.getString(R.string.month) + CalendarUtil.getDayOfMonth(endTime) + ResourceUtil.getString(R.string.day);
                setText(R.id.search_date_start, start);
                setText(R.id.search_date_end, end);
                setText(R.id.search_date_count, count);
                setText(R.id.search_date_start_label, getLabel(startTime));
                setText(R.id.search_date_end_label, getLabel(endTime));
            }
        });
        calendarDialog.show();
    }

    private String getLabel(long time) {
        if (DateUtil.compare(time, new Date().getTime()) == 0) {
            return ResourceUtil.getString(R.string.today);
        }
        if (DateUtil.compare(time, DateUtil.getNextDate(1).getTime()) == 0){
            return  ResourceUtil.getString(R.string.tomorrow);
        }
        return ResourceUtil.getString(R.string.week) + LunarCalendarConfig.ChineseNumber[CalendarUtil.getDayOfWeek(time) - 1];
    }


    private void showPersonCountDialog() {
        PersonCountDialog personCountDialog = new PersonCountDialog(getActivity());
        personCountDialog.setOnDialogItemClickListener(new OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(int position, String item) {
                if (item != null)
                    peopleCount.setText(item);
            }
        });
        personCountDialog.show();
    }

    private void locate() {
        hidden(locateAction);
        show(locateLoading);
        mLocation.locate(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                    final String location = aMapLocation.getAddress() + "省" + aMapLocation.getProvince() + "城市" + aMapLocation.getCity() +
                            "地区" + aMapLocation.getDistrict() + "街道" + aMapLocation.getStreet();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hidden(locateLoading);
                            show(locateAction);
                            searchLocation.setText(location);
                            mLocation.stop();
                        }
                    }, 3000);
                }
            }
        });
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float height = appBarLayout.getHeight();
        verticalOffset = Math.abs(verticalOffset);
        if (verticalOffset < height * HIDE_START_PERCENT) {
        } else if (verticalOffset < height * HIDE_END_PERCENT) {
            show(appBarImage);
            float imagePercent = (height * HIDE_END_PERCENT - verticalOffset) / (height * (HIDE_END_PERCENT - HIDE_START_PERCENT));
            appBarImage.setAlpha(imagePercent);
        } else if (verticalOffset < height * HIDE_COLLAPSING_PERCENT) {
            hidden(appBarImage);
            show(mCollapsing);
            hidden(mToolbar);
            float collapsingPercent = (height * HIDE_COLLAPSING_PERCENT - verticalOffset) / (height * (HIDE_COLLAPSING_PERCENT - HIDE_END_PERCENT));
            mCollapsing.setAlpha(collapsingPercent);
        } else {
            hidden(mCollapsing);
            show(mToolbar);
        }
    }

    private void show(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    private void hidden(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
