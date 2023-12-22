package com.example.PVSSpringBoot.repositories;


import com.example.PVSSpringBoot.Entities.RequestProduct;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface RequestProductRepo extends CrudRepository<RequestProduct,Long> {

    Optional<RequestProduct> findByProductName(String productName);
    @Query("select p from RequestProduct p where p.userId like ?1")
    List<RequestProduct> findByUserId(Long UserId);

}
