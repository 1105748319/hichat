package com.hichat.common.dto;

import com.hichat.common.pojo.Essay;
import com.hichat.common.pojo.User;

public class EssayUserDTO extends Essay {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
