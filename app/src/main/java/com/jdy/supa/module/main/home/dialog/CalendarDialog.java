package com.jdy.supa.module.main.home.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.jdy.base.BaseDialog;
import com.jdy.base.utils.DateUtil;
import com.jdy.calendar.MonthAdapter;
import com.jdy.calendar.MonthAdapter.OnDateFestivalObserver;
import com.jdy.calendar.OnTimeConfirm;
import com.jdy.calendar.bean.MonthBean;
import com.jdy.calendar.utils.CalendarUtil;
import com.jdy.calendar.utils.LunarCalendar;
import com.jdy.calendar.utils.LunarCalendarConfig;
import com.jdy.supa.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CalendarDialog extends BaseDialog implements OnDateFestivalObserver, OnTimeConfirm{
    private List<MonthBean> list = new ArrayList<>();
    private MonthAdapter mAdapter;
    private LunarCalendar lunarCalendar;
    private long startTime, endTime;
    private String dayCount, format = "MM月dd日";
    private RecyclerView mRecyclerView;
    private OnDatePickerConfirm datePickerConfirm;

    @Override
    public void noticeUpdate(long beganTime, long endTime) {
        this.startTime = beganTime;
        this.endTime = endTime;
        setIndicator();
    }

    public interface OnDatePickerConfirm {
        void confirm(long startTime, long endTime, String count);
    }

    public CalendarDialog(Context context) {
        super(context, R.style.Fullscreen);
    }

    @Override
    public int getRootLayoutID() {
        return R.layout.dialog_calendar;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list.clear();
        int month = CalendarUtil.getMonth();
        int year = CalendarUtil.getYear();
        for (int i = 0; i < 5; i++) {
            list.add(new MonthBean(year, month++));
            if (month > 12) {
                year++;
                month = 1;
            }
        }
        lunarCalendar = new LunarCalendar();
        mAdapter = new MonthAdapter(mContext, list);
        mAdapter.setFestivalObserver(this);
        mAdapter.setConfirm(this);
        startTime = new Date().getTime();
        endTime = DateUtil.getNextDate(1).getTime();
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.calendar_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void bendData() {
        setIndicator();
        showView(R.id.toolbar_cancel);
        setText(R.id.toolbar_clear, "清空");
        showView(R.id.toolbar_clear);
    }

    private void setIndicator() {
        setText(R.id.check_in, DateUtil.dateFormat(startTime, format));
        String text1 = "周" + LunarCalendarConfig.ChineseNumber[CalendarUtil.getDayOfWeek(startTime) - 1];
        setText(R.id.check_in_week, text1);
        if (endTime > startTime) {
            setText(R.id.check_out, DateUtil.dateFormat(endTime, format));
            String text2 = "周" + LunarCalendarConfig.ChineseNumber[CalendarUtil.getDayOfWeek(endTime) - 1];
            setText(R.id.check_out_week, text2);
            dayCount = "共" + DateUtil.calculateDValue(endTime, startTime) + "晚";
            setText(R.id.calendar_date_count, dayCount);
        } else {
            setText(R.id.check_out, "");
            setText(R.id.check_out_week, "");
            setText(R.id.calendar_date_count, "共0晚");
        }
    }

    @Override
    public void setListener() {
        setOnClickListener(R.id.calendar_action);
        setOnClickListener(R.id.toolbar_clear);
        setOnClickListener(R.id.toolbar_cancel);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case R.id.toolbar_cancel:
                dialog.cancel();
                break;
            case R.id.toolbar_clear:
                mAdapter.clear();
                break;
            case R.id.calendar_action:
                datePickerConfirm.confirm(startTime, endTime, dayCount);
                dialog.cancel();
                break;
        }
    }


    public void setDatePickerConfirm(OnDatePickerConfirm datePickerConfirm) {
        this.datePickerConfirm = datePickerConfirm;
    }

//    @Override
//    public void pickerStartDate(long beganTime) {
//        startTime = beganTime;
//        if (startTime > endTime) {
//            endTime = 0;
//        }
//        setIndicator();
//    }
//
//    @Override
//    public void pickerOverDate(long endTime) {
//        this.endTime = endTime;
//        setIndicator();
//    }

    @Override
    public boolean isRestDay(int year, int month, int day) {
        int dayOfWeek = CalendarUtil.getDayOfWeek(year, month, day);
        return dayOfWeek == 1 || dayOfWeek == 7;
    }

    @Override
    public String getFestivalName(int year, int month, int day) {
        return lunarCalendar.getLunarDate(year, month, day);
    }
}
