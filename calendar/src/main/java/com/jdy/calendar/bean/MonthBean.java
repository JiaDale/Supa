package com.jdy.calendar.bean;

public class MonthBean {
    private int year, month;
    public MonthBean(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    @Override
    public String toString() {
        return year + "\u5e74" + month + "\u6708";
    }
}
