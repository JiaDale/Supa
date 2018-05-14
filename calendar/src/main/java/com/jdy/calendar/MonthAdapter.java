package com.jdy.calendar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jdy.base.adapter.BaseRecyclerHolder;
import com.jdy.base.adapter.BaseRecyclerViewAdapter;
import com.jdy.base.adapter.ListenerWithPosition.OnClickWithPositionListener;
import com.jdy.base.decoration.GridSpacingItemDecoration;
import com.jdy.base.utils.DateUtil;
import com.jdy.base.utils.L;
import com.jdy.base.utils.ResourceUtil;
import com.jdy.base.utils.SharedPreferencesUtil;
import com.jdy.calendar.bean.MonthBean;
import com.jdy.calendar.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthAdapter extends BaseRecyclerViewAdapter<MonthBean> {
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    private OnDateFestivalObserver festivalObserver;
    private static int itemClickCount = 0;
    private static List<DateAdapter> adapterList;
    private OnTimeConfirm confirm;

    public MonthAdapter(Context context, List<MonthBean> list) {
        super(context, list, R.layout.item_month);
        if (adapterList == null)
            adapterList = new ArrayList<>();
    }

    @Override
    protected void convert(BaseRecyclerHolder holder, MonthBean bean) {
        if (bean == null)
            return;
        holder.setText(R.id.item_month_title, bean.toString());
        RecyclerView recyclerView = holder.getView(R.id.item_month_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(7, 16, true));
        DateAdapter dateAdapter = new DateAdapter(mContext, bean.getYear(), bean.getMonth(), holder);
        adapterList.add(dateAdapter);
        recyclerView.setAdapter(dateAdapter);
    }

    public void setConfirm(OnTimeConfirm confirm) {
        this.confirm = confirm;
    }

    public void clear() {
        setTime(0, 0);
        for (int i = 0; i < adapterList.size(); i++) {
            adapterList.get(i).notifyDataSetChanged();
        }
    }

    public interface OnDateFestivalObserver {
        boolean isRestDay(int year, int month, int day);

        String getFestivalName(int year, int month, int day);
    }


    public void setFestivalObserver(OnDateFestivalObserver observer) {
        this.festivalObserver = observer;
    }


    private class DateAdapter extends BaseRecyclerViewAdapter implements OnClickWithPositionListener {
        private int year, month, dasOfMonth, firstDayMonthOfWeek;
        private TextView indicator, rest, text;
        private View item;
        private BaseRecyclerHolder parent;

        public DateAdapter(Context context, int year, int month, BaseRecyclerHolder parent) {
            super(context, (List) null, R.layout.item_day);
            this.year = year;
            this.month = month;
            dasOfMonth = CalendarUtil.getDaysOfMonth(year, month);
            firstDayMonthOfWeek = CalendarUtil.getFirstDayMonthOfWeek(year, month) - 1;
            this.parent = parent;
        }

        @Override
        public int getItemCount() {
            return dasOfMonth + firstDayMonthOfWeek;
        }

        @Override
        public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
            item = holder.getView(R.id.item_day);
            indicator = holder.getView(R.id.item_day_indicator);
            rest = holder.getView(R.id.item_day_rest);
            text = holder.getView(R.id.item_day_text);
            int i = position - firstDayMonthOfWeek + 1;
            if (i > 0) {
                holder.position = i;
                if (festivalObserver.isRestDay(year, month, i)) {
                    setRestStyle();
                } else {
                    setNormalStyle(holder);
                }
                String festivalName = festivalObserver.getFestivalName(year, month, i);
                if (festivalName != null) {
                    indicator.setText(festivalName);
                    indicator.setVisibility(View.VISIBLE);
                } else {
                    indicator.setVisibility(View.INVISIBLE);
                }
                text.setText(String.valueOf(i));
                setStyle(holder);
            } else {
                item.setVisibility(View.INVISIBLE);
            }
        }

        private void setNormalStyle(BaseRecyclerHolder holder) {
            holder.getView(R.id.item_day_pass_image).setVisibility(View.INVISIBLE);
            text.setTextColor(ResourceUtil.getColor(R.color.black));
            rest.setVisibility(View.INVISIBLE);
            indicator.setTextColor(ResourceUtil.getColor(R.color.black));
            item.setBackgroundColor(ResourceUtil.getColor(R.color.main_style));
        }

        private void setRestStyle() {
            rest.setVisibility(View.VISIBLE);
            rest.setTextColor(ResourceUtil.getColor(R.color.blue));
            rest.setBackgroundResource(R.drawable.item_day_rest_pointer);
            text.setTextColor(ResourceUtil.getColor(R.color.blue));
            indicator.setTextColor(ResourceUtil.getColor(R.color.blue));
        }

        private void setStyle(BaseRecyclerHolder holder) {
            Date date = CalendarUtil.getDate(year, month, holder.position);
            long beganTime = SharedPreferencesUtil.getLong(START_TIME, 0);
            long endTime = SharedPreferencesUtil.getLong(END_TIME, 0);

            if (DateUtil.compare(date, new Date(), "yyyy-MM-dd") < 0) {
                setPastStyle(holder);
            } else {
                if (beganTime > 0 && DateUtil.compare(date, new Date(beganTime)) == 0) {
                    indicator.setText(ResourceUtil.getString(R.string.calendar_check_in));
                    confirm.noticeUpdate(beganTime, endTime);
                    setSelectedStyle();
                } else if (DateUtil.compare(date, new Date(beganTime)) > 0 && DateUtil.compare(date, new Date(endTime)) < 0) {
                    setSelectedStyle();
                } else if (DateUtil.compare(date, new Date(endTime)) == 0){
                    indicator.setText(ResourceUtil.getString(R.string.calendar_check_out));
                    confirm.noticeUpdate(beganTime, endTime);
                    setSelectedStyle();
                }else {
                    setNormalStyle(holder);
                }
                holder.setOnClickListener(this);
            }
        }

        @Override
        protected void convert(BaseRecyclerHolder baseRecyclerHolder, Object o) {

        }

        private void setSelectedStyle() {
            item.setBackgroundColor(ResourceUtil.getColor(R.color.blue));
            indicator.setTextColor(ResourceUtil.getColor(R.color.white));
            rest.setTextColor(ResourceUtil.getColor(R.color.white));
            text.setTextColor(ResourceUtil.getColor(R.color.white));
            rest.setBackgroundResource(R.drawable.item_day_rest_pointer_selected);
        }

        private void setPastStyle(BaseRecyclerHolder holder) {
//            L.i("item : " +DateUtil.dateFormat(CalendarUtil.getDate(year, month, holder.position), "yyyy年MM月dd日") + "为past");
            holder.getView(R.id.item_day_pass_image).setVisibility(View.VISIBLE);
            text.setTextColor(ResourceUtil.getColor(R.color.gray));
            rest.setTextColor(ResourceUtil.getColor(R.color.gray));
            rest.setBackgroundResource(R.drawable.item_day_rest_pointer_gray);
            indicator.setTextColor(ResourceUtil.getColor(R.color.gray));
            item.setBackgroundColor(ResourceUtil.getColor(R.color.main_style));
            holder.setOnClickListener(null);
        }

        @Override
        public void onClick(View view, BaseRecyclerHolder holder) {
            long beganTime = SharedPreferencesUtil.getLong(START_TIME, 0);
            long endTime = SharedPreferencesUtil.getLong(END_TIME, beganTime);
            switch (itemClickCount) {
                case 0:
                    beganTime = CalendarUtil.getDate(year, month, holder.position).getTime();
                    endTime = beganTime;
                    itemClickCount = 1;
                    break;
                case 1:
                    long tempTime = CalendarUtil.getDate(year, month, holder.position).getTime();
                    if (tempTime > beganTime){
                        endTime = tempTime;
                    } else {
                        beganTime = tempTime;
                    }
                    itemClickCount = 0;
                    break;
            }
            L.i("开始时间：" + DateUtil.dateFormat(new Date(beganTime)) + ", 结束时间： " + DateUtil.dateFormat(new Date(endTime)));
            setTime(beganTime, endTime);
            updateParentItem();
        }
    }

    private void updateParentItem() {
        for (int i = 0; i < adapterList.size(); i++) {
            adapterList.get(i).notifyDataSetChanged();
        }
    }

    private void setTime(long beganTime, long endTime) {
        SharedPreferencesUtil.putLong(START_TIME, beganTime);
        SharedPreferencesUtil.putLong(END_TIME, endTime);
    }
}
