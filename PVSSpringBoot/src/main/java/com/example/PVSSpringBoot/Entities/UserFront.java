package com.example.PVSSpringBoot.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Objects;
@Builder
public class UserFront {
    private long id;
    private String userName;
    private String email;
    private boolean isAdmin;

    public UserFront(long id, String userName, String email, boolean isAdmin) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }
    public static UserFront getUserFront(User user){
        return UserFront.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .isAdmin(user.getIsAdmin())
                .id(user.getUserId()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFront userFront)) return false;
        return id == userFront.id && isAdmin == userFront.isAdmin && Objects.equals(userName, userFront.userName) && email.equals(userFront.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, isAdmin);
    }
}
