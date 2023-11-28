package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Blob;
import java.sql.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)
    private Long user_id;


    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "is_admin", nullable = false)
    private Boolean is_admin = false;

    @Lob
    @Column(name = "profile_photo")
    @JdbcTypeCode(SqlTypes.BLOB)
    private Blob profile_photo;

    @Column(name = "join_date", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date join_date;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "username", length = 20)
    private String username;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return this.user_id.equals(user.user_id) && this.email.equals(user.email) && this.is_admin.equals(user.is_admin) && this.join_date.equals(user.join_date);
//    }

}
