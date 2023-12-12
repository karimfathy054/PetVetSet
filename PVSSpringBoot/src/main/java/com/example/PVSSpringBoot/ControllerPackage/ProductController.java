package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.services.ProductManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    @Autowired
    ProductManagementService productManagementService;
    @Autowired
    public ProductController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping("/all")
    List<Product> findAll(){
        return productManagementService.findAll();
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

    @GetMapping("/category={category}")
    List<Product> filterCategory(@PathVariable String category){
        return productManagementService.searchForCategory(category);
    }
    
    @GetMapping("/Target_Animal={Animal}")
    List<Product> filterTargetAnimal(@PathVariable String Animal){
        return productManagementService.searchByTargetAnimal(Animal);
    }
    
    @GetMapping("/filter/price>={startPrice}&&price<={endPrice}")
    List<Product> filterPriceBetween(@PathVariable Float startPrice, @PathVariable Float endPrice){
        return productManagementService.searchForPriceBetween(startPrice, endPrice);
    }

    @PostMapping("/add")
    String addProduct(@RequestBody Product body){
        if(productManagementService.addNewProduct(body)) return "product added";
        return "adding new product failed";
    }

    @GetMapping("/rate={rate}&&id={id}")
    String addRate(@PathVariable Float rate, @PathVariable Long id){
        if(productManagementService.addRate(rate,id))return "rate added";
        return "adding new rate failed";
    }

    @DeleteMapping("/id={id}")
    String deleteProduct(@PathVariable Long id){
        if(productManagementService.delete(id)) return "deletion done";
        return "deletion failed";
    }
}
