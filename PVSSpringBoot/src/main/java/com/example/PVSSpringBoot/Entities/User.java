package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Blob;
import java.sql.Date;

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
    @Unsigned
    private Integer user_id;

    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "user_name", nullable = false, length = 30)
    private String user_name;

    @Column(name = "is_admin", nullable = false)
    private Boolean is_admin = false;

    @Lob
    @Column(name = "profile_photo")
    @JdbcTypeCode(SqlTypes.BLOB)
    private Blob profile_photo;

    @Column(name = "join_date", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date join_date;
    @Column(name="password",length=20)
    private String password;


}