package com.example.PVSSpringBoot.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserFront {
    private long id;
    private String userName;
    private String email;
    private String image;
    private boolean isAdmin;
//    public UserFront(long id, String userName, String email, boolean isAdmin,String image) {
//        this.id = id;
//        this.userName = userName;
//        this.email = email;
//        this.image = image;
//        this.isAdmin = isAdmin;
//    }

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
                .id(user.getUserId())
                .image(user.getProfile_photo()).build();
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
