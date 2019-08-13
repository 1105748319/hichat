package com.hichat.common.dto;

import com.hichat.common.pojo.Comment;
import com.hichat.common.pojo.User;

public class CommentUserDTO extends Comment {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
