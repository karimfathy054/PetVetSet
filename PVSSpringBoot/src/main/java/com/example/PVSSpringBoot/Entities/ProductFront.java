package com.example.PVSSpringBoot.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductFront {
    private String description;

    //image handled separately
    private float price;
    private String brandName;
    private String categoryName;
    private String targetAnimal;
    private String productName;
    private boolean isApproved;
    private String userEmail;
    private long productId;

    private String photo;

    public ProductFront(Product productOriginal) {
        this.description = productOriginal.getDescription();
        this.price = productOriginal.getPrice();
        this.brandName = productOriginal.getBrandName();
        this.categoryName = productOriginal.getCategory();
        this.targetAnimal = productOriginal.getTargetAnimal();
        this.productName = productOriginal.getProductName();
        this.isApproved = true;
        this.userEmail = productOriginal.getUser().getEmail();
        this.productId = productOriginal.getId();
        this.photo = productOriginal.getImageLink();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTargetAnimal() {
        return targetAnimal;
    }

    public void setTargetAnimal(String targetAnimal) {
        this.targetAnimal = targetAnimal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
