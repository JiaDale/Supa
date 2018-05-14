package com.jdy.supa.entities;

import java.util.List;

public class ItemBean extends BaseBean{
    private String title;
    private String content;
    private String image;
    private String location;
    private String detail;
    private boolean isCollect;
    private List<CommentBean> commentBeanList;

    /**
     * Style or Brand
     */
    public ItemBean(String title, String image) {
        this(title, null, image);
    }

    /**
     * Story
     */
    public ItemBean(String title, String content, String image) {
        this(title, content, image, null);
    }

    /**
     * Journey
     */
    public ItemBean(String title, String content, String image, String location) {
        this(title, content, image, location, null, false, null);
    }

    /**
     * House
     */
    public ItemBean(String image, String location, String detail, boolean isCollect, List<CommentBean> commentBeanList) {
        this(null, null, image, location, detail, isCollect, commentBeanList);
    }

    public ItemBean(String title, String content, String image, String location, String detail, boolean isCollect, List<CommentBean> commentBeanList) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.location = location;
        this.detail = detail;
        this.isCollect = isCollect;
        this.commentBeanList = commentBeanList;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public List<CommentBean> getCommentBeanList() {
        return commentBeanList;
    }
}
