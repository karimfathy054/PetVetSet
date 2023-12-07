package com.example.PVSSpringBoot.ProductTest;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


//TODO: run tests sequentially or figure out a way to fix this
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductsRepoTest {

    @Autowired
    ProductRepository repository;

    @Test
    void pobulateDataBase(){
        Product p1 = Product.builder()
                .productName("litter box")
                .brandName("tom the cat")
                .price(50f).build();
        Product p2 = Product.builder()
                .productName("catnip")
                .brandName("garfield")
                .price(20f).build();
        Product p3 = Product.builder()
                .productName("blue collar")
                .brandName("petly")
                .price(70f).build();
        repository.save(p1);
        repository.save(p2);
        repository.save(p3);

        Assertions.assertThat(repository.count()).isEqualTo(3);
    }
    //happy cases
    //test adding to the db
    @Test
    void addToDB(){
        Product p = Product.builder()
                .productName("chewing toy")
                .brandName("petly")
                .price(60f).build();
        Product saved = repository.save(p);
        Assertions.assertThat(saved).isEqualTo(p);
        Assertions.assertThat(repository.count()).isEqualTo(4);
    }

    //test getting data from the db by id
    @Test
    void testGetProductByID(){
        Optional<Product> queryResult = repository.findById(1L);
        Assertions.assertThat(queryResult).isPresent();
        Assertions.assertThat(queryResult.get().getProductName()).isEqualTo("chewing toy");
    }

    //test getting data from the db by product name
    @Test
    void testGetProductByName(){
        List<Product> queryResult = repository.findProductByProductNameStartsWithIgnoreCase("litter box");
        Assertions.assertThat(queryResult).hasSize(1);
        Assertions.assertThat(queryResult.get(0).getBrandName()).isEqualTo("tom the cat");
    }

    //test getting data from the db by brand name
    @Test
    void testGetProductByBrand(){
        List<Product> queryResult = repository.findByBrandNameLikeIgnoreCase("petly");
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("blue collar");
        Assertions.assertThat(queryResult.get(1).getProductName()).isEqualTo("chewing toy");
    }

    //test filtering data by price
    @Test
    void testFilterByPrice(){
        List<Product> queryResult = repository.findByPriceLessThanEqual(50f);
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("litter box");
        Assertions.assertThat(queryResult.get(1).getProductName()).isEqualTo("catnip");
    }

    //bad cases
    //test getting data from the db by id that does not exist
    @Test
    void testUnDefinedID(){
        Optional<Product> queryResult = repository.findById(5200L);
        Assertions.assertThat(queryResult).isNotPresent();
    }

    //test getting data from the db by product name that does not exist
    @Test
    void testUnDefinedName(){
        List<Product> queryResult = repository.findProductByProductNameStartsWithIgnoreCase("fur remover");
        Assertions.assertThat(queryResult).hasSize(0);
    }

    //test getting data from the db by brand name that does not exist
    @Test
    void testUnDefinedBrand(){
        List<Product> queryResult = repository.findProductByProductNameStartsWithIgnoreCase("Bluey");
        Assertions.assertThat(queryResult).hasSize(0);
    }

    //test filtering data by price that does not exist
    @Test
    void testFilterByPriceNotInRange(){
        List<Product> queryResult = repository.findByPriceLessThanEqual(10f);
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }

    //test filtering data by price that is not a number
//    @Test
//    void testFilterByPriceNotaNumber(){
//        List<Product> queryResult = repository.findByPriceLessThanEqual(Float.valueOf("none"));
//        Assertions.assertThat(queryResult.size()).isEqualTo(0);
//    }

    //test filtering data by price that is negative
    @Test
    void testFilterByPriceNegative(){
        List<Product> queryResult = repository.findByPriceLessThanEqual(-10f);
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }
    //test adding inappropriate input
//    @Test
//    void testAddInappropriateInput(){
//
//    }

}
