package com.example.PVSSpringBoot.Entities;

import java.sql.Date;

public class UserFront {
    private long id;
    private String user_name;
    private String email;
    private boolean is_admin;

    public UserFront(long id, String user_name, String email, boolean is_admin) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.is_admin = is_admin;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
