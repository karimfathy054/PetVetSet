package com.example.PVSSpringBoot.repositories;

import com.example.PVSSpringBoot.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarksRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p inner join p.bookmarkingUsers bookmarkingUsers where bookmarkingUsers.userId = ?1")
    List<Product> findByBookmarkingUsers_UserId(Long userId);


}