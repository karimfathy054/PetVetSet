package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
@RequiredArgsConstructor
public class Product {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(productName, product.productName) && Objects.equals(brandName, product.brandName) && Objects.equals(price, product.price);
    }

}