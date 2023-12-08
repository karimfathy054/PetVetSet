package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.services.ProductManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductManagementService productManagementService;
    @Autowired
    public ProductController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

<<<<<<< HEAD
    @GetMapping("/all")
=======
    @GetMapping()
>>>>>>> b6ee88146414874a7d00d51c8072ca0a820084f2
    List<Product> findAll(){
        return productManagementService.findAll();
    }

    @GetMapping("/hello")
    String hello(){
        return "Hello";
    }

    @GetMapping("/id={id}")
    Product findProduct(@PathVariable Long id){
        return productManagementService.findById(id);
    }

    @GetMapping("/brand/{brandName}")
    List<Product> findProductsByBrand(@PathVariable String brandName){
        return productManagementService.searchForBrand(brandName);
    }

    @GetMapping("/price<={price}")
    List<Product> filterPrice(@PathVariable Float price){
        return productManagementService.searchForPrice(price);
    }
    @GetMapping("/name={productName}")
    List<Product> searchForProduct(@PathVariable String productName){
        return productManagementService.searchForProduct(productName);
    }

    @PostMapping("/add")
    String addProduct(@RequestBody Product body){
        if(productManagementService.addNewProduct(body)) return "product added";
        return "adding new product failed";
    }


    @DeleteMapping("/id={id}")
    String deleteProduct(@PathVariable Long id){
        if(productManagementService.delete(id)) return "deletion done";
        return "deletion failed";
    }
}
