package com.example.PVSSpringBoot.Entities;


public class ProductFrontBuilder {
    private ProductFront productFront;

    public ProductFrontBuilder(){
        this.productFront = new ProductFront();
        this.productFront.setProductName("Unnamed");
        this.productFront.setApproved(false);
        this.productFront.setBrandName("No Brand");
        this.productFront.setDescription("no description");
        this.productFront.setPrice(0);
        this.productFront.setCategoryName("any");
        this.productFront.setTargetAnimal("any");
    }

    public ProductFront get(){return this.productFront;}

    public ProductFrontBuilder setProductName(String name){
        this.productFront.setProductName(name);
        return this;
    }

    public ProductFrontBuilder setApproved(boolean approved){
        this.productFront.setApproved(approved);
        return this;
    }

    public ProductFrontBuilder setBrandName(String brandName){
        this.productFront.setBrandName(brandName);
        return this;
    }

    public ProductFrontBuilder setDescription(String description){
        this.productFront.setDescription(description);
        return this;
    }

    public ProductFrontBuilder setPrice(float price){
        this.productFront.setPrice(price);
        return this;
    }

    public ProductFrontBuilder setCategoryName(String categoryName){
        this.productFront.setCategoryName(categoryName);
        return this;
    }

    public ProductFrontBuilder setTargetAnimal(String targetAnimal){
        this.productFront.setTargetAnimal(targetAnimal);
        return this;
    }


}