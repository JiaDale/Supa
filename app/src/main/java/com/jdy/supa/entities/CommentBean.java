package com.jdy.supa.entities;

public class CommentBean extends BaseBean {
    private String userName;
    private String userImage;
    private String userContent;

    public CommentBean(String userName, String userImage, String userContent) {
        this.userName = userName;
        this.userImage = userImage;
        this.userContent = userContent;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserContent() {
        return userContent;
    }
}
