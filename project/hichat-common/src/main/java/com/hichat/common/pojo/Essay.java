package com.hichat.common.pojo;

import java.util.Date;

public class Essay {
    private Long id;

    private Long userId;

    private String content;

        private Date createTime;

    private String imgs;

    private String smallImgs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }

    public String getSmallImgs() {
        return smallImgs;
    }

    public void setSmallImgs(String smallImgs) {
        this.smallImgs = smallImgs == null ? null : smallImgs.trim();
    }
}