package com.jdy.supa.entities;

public class BrilliantBean extends BaseBean {
    private String title;
    private boolean more;


    public BrilliantBean(String title, boolean more) {
        this.title = title;
        this.more = more;

    }

    public String getTitle() {
        return title;
    }

    public boolean hasMore() {
        return more;
    }
}
