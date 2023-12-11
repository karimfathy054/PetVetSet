package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Blob;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "RequestProduct")
public class RequestProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "productId", nullable = false)

    private Long productId;

    @Column(name = "userId", nullable = false, length = 30)
    private long userId;

    @Column(name = "productName", nullable = false, length = 30)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private float price ;

    @Lob
    @Column(name = "productPhoto")
    @JdbcTypeCode(SqlTypes.BLOB)
    private Blob productPhoto;

    @Column(name = "requestDate", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date join_date;
    @Column(name="brandName",nullable = false)
    private String brandName;

    @Column(name="categoryName",nullable = false)
    private String categoryName;


    public Long getProductId() {
        return productId;
    }

    public long getUserId() {
        return userId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public Blob getProductPhoto() {
        return productPhoto;
    }

    public Date getJoin_date() {
        return join_date;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTargetAnimal() {
        return targetAnimal;
    }

    @Column(name="targetAnimal",nullable = false)
    private String targetAnimal;
///
}
