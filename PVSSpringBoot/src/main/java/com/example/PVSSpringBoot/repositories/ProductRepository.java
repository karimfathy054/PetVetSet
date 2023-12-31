package com.example.PVSSpringBoot.repositories;

import com.example.PVSSpringBoot.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.user.userId = ?1")
    List<Product> findProductsByUserId(Long userId);

    @Query("select p from Product p where p.user.email = ?1")
    List<Product> findProductsByUserEmail(String email);


    @Query("""
            select (count(p) > 0) from Product p
            where upper(p.productName) like upper(?1) and upper(p.brandName) like upper(?2)""")
    boolean existsInDB(String productName, String brandName);


    List<Product> findProductByProductNameStartsWithIgnoreCase(String productName);

    List<Product> findByPriceLessThanEqual(Float price);

    List<Product> findByBrandNameLikeIgnoreCase(String brandName);

    List<Product> findByCategoryLikeIgnoreCase(String category);

    List<Product> findByTargetAnimalLikeIgnoreCase(String targetAnimal);

    List<Product> findByPriceBetween(Float priceStart, Float priceEnd);

    @Transactional
    @Modifying
    @Query("update Product p set p.rating = (p.rating * (p.noOfRating / (p.noOfRating + 1))) + (?1 / (p.noOfRating+1.0)) , p.noOfRating = p.noOfRating+1  where p.id = ?2")
    void updateRating(Float rate,Long id);
}