package com.example.PVSSpringBoot.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Blob;
import java.sql.Date;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)

    private Long userId;


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
    @Column(name="password",length=80)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(name = "Users_products",
            joinColumns = @JoinColumn(name = "user_user_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Product> bookmarks = new LinkedHashSet<>();

    public void addBookmark(Product product){
        product.getBookmarkingUsers().add(this);
        this.bookmarks.add(product);
    }

    public void removeBookmark(Product product){
        this.bookmarks.remove(product);
    }

    public void removeBookmark(Long productId){
        Product p = this.bookmarks.stream().filter( x->x.getId() == productId).findFirst().orElse(null);
        if(p != null){
            p.getBookmarkingUsers().remove(this);
            this.bookmarks.remove(p);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role.name())
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
