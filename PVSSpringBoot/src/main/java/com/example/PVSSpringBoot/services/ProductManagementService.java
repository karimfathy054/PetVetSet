package com.example.PVSSpringBoot.services;


import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductManagementService {

    ProductRepository repo;

    @Autowired
    public ProductManagementService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> searchForProduct(String productName){
        if(productName == null) return null;
        return repo.findProductByProductNameStartsWithIgnoreCase(productName);
    }
    public List<Product> searchForPrice(Float price){
        if(price == null || price <0f) return null;
        return repo.findByPriceLessThanEqual(price);
    }
    public List<Product> searchForBrand(String brandName){
        if(brandName == null) return null;
        return repo.findByBrandNameLikeIgnoreCase(brandName);
    }

    public Product findById(Long id){
        if(id == null) return null;
        Optional<Product> queryResult = repo.findById(id);

        return queryResult.orElse(null);
    }

    public boolean addNewProduct(Product product){
        if(product == null) return false;
        if(repo.existsInDB(product.getProductName(), product.getBrandName()))return false;
        return repo.save(product).equals(product);
    }


    public Boolean delete(Long id) {
        if(id == null) return false;
        if(!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public List<Product> findAll() {
        if(repo.count() == 0) return List.of();
        return repo.findAll();
    }
}
