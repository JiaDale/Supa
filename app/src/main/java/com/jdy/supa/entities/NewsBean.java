package com.jdy.supa.entities;


import java.util.Date;

public class NewsBean {
    private String image;
    private boolean isNew;
    private String title;
    private String content;
    private Date lastRefreshTime;

    public NewsBean(String image, boolean isNew, String title, String content, Date lastRefreshTime) {
        this.image = image;
        this.isNew = isNew;
        this.title = title;
        this.content = content;
        this.lastRefreshTime = lastRefreshTime;
    }

    public String getImage() {
        return image;
    }

    public boolean isNew() {
        return isNew;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getLastRefreshTime() {
        return lastRefreshTime;
    }
}
