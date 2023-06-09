package com.example.demo.entity;

/**
 * @author changmk
 * @version 1.0
 * @date 2023/6/9 17:42
 */
public class UserHolder{
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}