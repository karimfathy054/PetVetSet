package com.example.PVSSpringBoot.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
@RequiredArgsConstructor
public class Product implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name", nullable = false)
    @NonNull
    private String productName;

    @Column(name = "brand_name", nullable = false)
    @NonNull
    private String brandName;

    @Column(name = "price", nullable = false)
    @NonNull
    private Float price;

    @Column(name = "target_animal")
    private String targetAnimal;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "category")
    private String category;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "noOfRating")
    private Long noOfRating;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    @JsonIgnore
    private User user;




    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public Product clone() {
        try {
            Product clone = (Product) super.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
