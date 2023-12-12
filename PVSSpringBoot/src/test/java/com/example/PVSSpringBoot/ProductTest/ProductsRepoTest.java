package com.example.PVSSpringBoot.ProductTest;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


//TODO: run tests sequentially or figure out a way to fix this
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductsRepoTest {

    @Autowired
    ProductRepository repository;

    @Order(1)
    @Test
    void pobulateDataBase(){
        Product p1 = Product.builder()
                .productName("litter box")
                .brandName("tom the cat")
                .targetAnimal("CAT")
                .rating(5f)
                .noOfRating(1L)
                .price(50f).build();
        Product p2 = Product.builder()
                .productName("catnip")
                .brandName("garfield")
                .targetAnimal("ACCESSORIES")
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
    @Order(2)
    @Test
    void changeRate(){
        repository.updateRating(9f,1L);
        Optional<Product> queryResult = repository.findById(1L);
        Assertions.assertThat(queryResult).isPresent();
        Assertions.assertThat(queryResult.get().getRating()).isEqualTo(4.5f);
        Assertions.assertThat(queryResult.get().getNoOfRating()).isEqualTo(2L);
    }
    //happy cases
    //test adding to the db
    @Order(3)
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
    @Order(4)
    @Test
    void testGetProductByID(){
        Optional<Product> queryResult = repository.findById(1L);
        Assertions.assertThat(queryResult).isPresent();
        Assertions.assertThat(queryResult.get().getProductName()).isEqualTo("litter box");
    }

    //test getting data from the db by product name
    @Order(5)
    @Test
    void testGetProductByName(){
        List<Product> queryResult = repository.findProductByProductNameStartsWithIgnoreCase("litter box");
        Assertions.assertThat(queryResult).hasSize(1);
        Assertions.assertThat(queryResult.get(0).getBrandName()).isEqualTo("tom the cat");
    }

    //test getting data from the db by brand name
    @Order(6)
    @Test
    void testGetProductByBrand(){
        List<Product> queryResult = repository.findByBrandNameLikeIgnoreCase("petly");
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("blue collar");
        Assertions.assertThat(queryResult.get(1).getProductName()).isEqualTo("chewing toy");
    }

    //test getting data from the db by target animal
    @Order(7)
    @Test
    void testGetProductByTargetAnimal(){
        List<Product> queryResult = repository.findByTargetAnimalLikeIgnoreCase("CAT");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("litter box");
    }

    //test getting data from the db by category
    @Order(8)
    @Test
    void testGetProductByCategory(){
        List<Product> queryResult = repository.findByTargetAnimalLikeIgnoreCase("ACCESSORIES");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("catnip");
    }

    //test filtering data by price
    @Order(9)
    @Test
    void testFilterByPrice(){
        List<Product> queryResult = repository.findByPriceLessThanEqual(50f);
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("litter box");
        Assertions.assertThat(queryResult.get(1).getProductName()).isEqualTo("catnip");
    }

    @Order(10)
    @Test
    void testFilterByPrice2(){
        List<Product> queryResult = repository.findByPriceBetween(0f,50f);
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("litter box");
        Assertions.assertThat(queryResult.get(1).getProductName()).isEqualTo("catnip");
    }

    //bad cases
    //test getting data from the db by id that does not exist
    @Order(11)
    @Test
    void testUnDefinedID(){
        Optional<Product> queryResult = repository.findById(5200L);
        Assertions.assertThat(queryResult).isNotPresent();
    }

    //test getting data from the db by product name that does not exist
    @Order(12)
    @Test
    void testUnDefinedName(){
        List<Product> queryResult = repository.findProductByProductNameStartsWithIgnoreCase("fur remover");
        Assertions.assertThat(queryResult).hasSize(0);
    }

    //test getting data from the db by brand name that does not exist
    @Order(13)
    @Test
    void testUnDefinedBrand(){
        List<Product> queryResult = repository.findProductByProductNameStartsWithIgnoreCase("Bluey");
        Assertions.assertThat(queryResult).hasSize(0);
    }

    @Order(14)
    @Test
    void testGetProductByNonExistantTargetAnimal(){
        List<Product> queryResult = repository.findByTargetAnimalLikeIgnoreCase("DOG");
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }

    //test getting data from the db by category
    @Order(15)
    @Test
    void testGetProductByNonExistantCategory(){
        List<Product> queryResult = repository.findByTargetAnimalLikeIgnoreCase("FOOD");
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }

    //test filtering data by price that does not exist
    @Order(16)
    @Test
    void testFilterByPriceNotInRange(){
        List<Product> queryResult = repository.findByPriceLessThanEqual(10f);
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }

    //test filtering data by price that is negative
    @Order(17)
    @Test
    void testFilterByPriceNegative(){
        List<Product> queryResult = repository.findByPriceLessThanEqual(-10f);
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }

    //test filtering data by price that does not exist
    @Order(18)
    @Test
    void testFilterByPriceNotInRange2(){
        List<Product> queryResult = repository.findByPriceBetween(0f,10f);
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }

    //test filtering data by price that is negative
    @Order(19)
    @Test
    void testFilterByReversedPrices(){
        List<Product> queryResult = repository.findByPriceBetween(50f,10f);
        Assertions.assertThat(queryResult.size()).isEqualTo(0);
    }
    //test adding inappropriate input
//    @Test
//    void testAddInappropriateInput(){
//
//    }

}
