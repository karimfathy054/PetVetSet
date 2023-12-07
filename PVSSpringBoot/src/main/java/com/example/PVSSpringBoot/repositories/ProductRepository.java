package com.example.PVSSpringBoot.repositories;

import com.example.PVSSpringBoot.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            select (count(p) > 0) from Product p
            where upper(p.productName) like upper(?1) and upper(p.brandName) like upper(?2)""")
    boolean existsInDB(String productName, String brandName);


    List<Product> findProductByProductNameStartsWithIgnoreCase(String productName);

    List<Product> findByPriceLessThanEqual(Float price);

    List<Product> findByBrandNameLikeIgnoreCase(String brandName);


}