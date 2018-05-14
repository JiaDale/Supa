package com.jdy.supa.entities;

public class PersonCountBean {
    private int count;
    private String detail;

    public PersonCountBean(int count, String detail) {
        this.count = count;
        this.detail = detail;
    }

    public int getCount() {
        return count;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return "PersonCountBean{" +
                "count=" + count +
                ", detail='" + detail + '\'' +
                '}';
    }
}
