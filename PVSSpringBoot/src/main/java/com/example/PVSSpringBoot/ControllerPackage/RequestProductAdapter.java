package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.ProductFront;
import com.example.PVSSpringBoot.Entities.ProductFrontBuilder;
import com.example.PVSSpringBoot.Entities.RequestProduct;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.RequestProductRepo;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class RequestProductAdapter {
    private RequestProductRepo requestProductRepo;
    private UsersRepo usersRepo;

    static final String OWNER_NOT_FOUND = "Owner of product not found";
    static final String OWNER_NEW_PRODUCT_NOT_FOUND = "Owner of new product not found";
    public RequestProductAdapter(RequestProductRepo requestProductRepo, UsersRepo usersRepo){
        this.requestProductRepo = requestProductRepo;
        this.usersRepo = usersRepo;
    }
    public ProductFront dataToFront(RequestProduct product){

        Optional<User> userContainer = this.usersRepo.findById(product.getUserId());
        if(userContainer.isEmpty()){
            throw new RuntimeException(OWNER_NOT_FOUND);
        }
        String userEmail = userContainer.get().getEmail();

        return new ProductFrontBuilder()
                .setBrandName(product.getBrandName())
                .setApproved(false)
                .setCategoryName(product.getCategoryName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setTargetAnimal(product.getTargetAnimal())
                .setProductName(product.getProductName())
                .setUserEmail(userEmail)
                .setProductId(product.getProductId())
                .setProductPhoto(product.getProductPhoto())
                .get();
    }

    public RequestProduct frontToNewData(ProductFront productFront){
        //should only be used when adding a new product
        //returns a RequestProduct with no id, and join date = current date
        Optional<User> container = this.usersRepo
                .findByEmail(productFront.getUserEmail());
        if(container.isEmpty()){
            throw new RuntimeException(OWNER_NEW_PRODUCT_NOT_FOUND);
        }
        return RequestProduct.builder()
                .productName(productFront.getProductName())
                .categoryName(productFront.getCategoryName())
                .price(productFront.getPrice())
                .brandName(productFront.getBrandName())
                .join_date(Date.valueOf(LocalDate.now()))
                .description(productFront.getDescription())
                .targetAnimal(productFront.getTargetAnimal())
                .userId(container.get().getUser_id())
                .productPhoto(productFront.getPhoto())
                .build();
    }

}
