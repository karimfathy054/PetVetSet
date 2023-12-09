package com.example.PVSSpringBoot.ProductTest;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import com.example.PVSSpringBoot.services.ProductManagementService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {
    @Mock
    ProductRepository repo;

    @Autowired
    @InjectMocks
    ProductManagementService service;

    @Test
    void test(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Optional<Product> Optional = java.util.Optional.ofNullable(p);
        Mockito.when(repo.findById(1L)).thenReturn(Optional);
        Assertions.assertThat(service.findById(1L).getProductName()).isEqualTo("Pname");
    }

    //happy cases
    //finding a product by name
    @Test
    void testFindByName(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(repo.findProductByProductNameStartsWithIgnoreCase("Pname")).thenReturn(list);
        List<Product> queryResult = service.searchForProduct("Pname");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("Pname");
    }

    //finding a product by price
    @Test
    void testFindByPrice(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Product p1 = Product.builder()
                .id(1L)
                .productName("Pname1")
                .brandName("Bname1")
                .price(20f)
                .build();
        List<Product> list = Arrays.asList(p,p1);
        Mockito.when(repo.findByPriceLessThanEqual(50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPrice(50f);
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("Pname");
        Assertions.assertThat(queryResult.get(1).getProductName()).isEqualTo("Pname1");

    }

    @Test
    void testFindByPriceRange(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Product p1 = Product.builder()
                .id(1L)
                .productName("Pname1")
                .brandName("Bname1")
                .price(20f)
                .build();
        List<Product> list = Arrays.asList(p);
        Mockito.when(repo.findByPriceBetween(0f,15f)).thenReturn(list);
        List<Product> queryResult = service.searchForPriceBetween(0f,15f);
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("Pname");
    }
    //finding a product by brand
    @Test
    void findProductByBrand(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(repo.findByBrandNameLikeIgnoreCase("Bname")).thenReturn(list);
        List<Product> queryResult = service.searchForBrand("Bname");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("Pname");
    }
    //finding a product by id
    @Test
    void findProductById(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Optional<Product> optional = Optional.ofNullable(p);
        Mockito.when(repo.findById(1L)).thenReturn(optional);
        Product queryResult = service.findById(1L);
        Assertions.assertThat(queryResult).isNotNull();
        Assertions.assertThat(queryResult.getProductName()).isEqualTo("Pname");
    }

    @Test
    void findProductByTargetAnimal(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .targetAnimal("CAT")
                .price(10f)
                .build();
        List<Product> list = Arrays.asList(p);
        Mockito.when(repo.findByTargetAnimalLikeIgnoreCase("CAT")).thenReturn(list);
        List<Product> queryResult = service.searchByTargetAnimal("CAT");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("Pname");
    }

    @Test
    void findProductByCategory(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .targetAnimal("CAT")
                .category("ACCESSORIES")
                .price(10f)
                .build();
        List<Product> list = Arrays.asList(p);
        Mockito.when(repo.findByCategoryLikeIgnoreCase("ACCESSORIES")).thenReturn(list);
        List<Product> queryResult = service.searchForCategory("ACCESSORIES");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        Assertions.assertThat(queryResult.get(0).getProductName()).isEqualTo("Pname");
    }
    //adding a product successfully
    @Test
    void testAddingProduct(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Mockito.when(repo.existsInDB(p.getProductName(), p.getBrandName())).thenReturn(false);
        Mockito.when(repo.save(p)).thenReturn(p);
        Assertions.assertThat(service.addNewProduct(p)).isTrue();
    }
    //deleting a product successfully
    @Test
    void testDeletingProduct(){
        Mockito.when(repo.existsById(1L)).thenReturn(true);
        Assertions.assertThat(service.delete(1L)).isTrue();
    }

    //getting all the products in the DB
    @Test
    void testGettingall(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(repo.count()).thenReturn(1L);
        Mockito.when(repo.findAll()).thenReturn(list);
        Assertions.assertThat(service.findAll().size()).isEqualTo(1);
        Assertions.assertThat(service.findAll().get(0)).isEqualTo(p);
    }

    //bad cases
    //finding a product by name that does not exist
    @Test
    void testFindingInvalidName(){
        List<Product> list = List.of();
        Mockito.when(repo.findProductByProductNameStartsWithIgnoreCase("Pname")).thenReturn(list);
        List<Product> queryResult = service.searchForProduct("Pname");
        Assertions.assertThat(queryResult).isEmpty();
    }
    //finding a product by price that does not exist
    @Test
    void testFindingInvalidPrice(){
        List<Product> list = List.of();
        Mockito.when(repo.findByPriceLessThanEqual(50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPrice(50f);
        Assertions.assertThat(queryResult).isEmpty();
    }
    @Test
    void testFindingInvalidPrice2(){
        //        Mockito.when(repo.findByPriceLessThanEqual(-50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPrice(-50f);
        Assertions.assertThat(queryResult).isNull();
    }
    @Test
    void testFindingInvalidPrice3(){
        //        Mockito.when(repo.findByPriceLessThanEqual(50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPrice(null);
        Assertions.assertThat(queryResult).isNull();
    }

    @Test
    void testFindingInvalidPriceRange(){
        List<Product> list = Arrays.asList();
        Mockito.when(repo.findByPriceBetween(10f,50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPriceBetween(10f,50f);
        Assertions.assertThat(queryResult).isEmpty();
    }
    @Test
    void testFindingInvalidPriceRange2(){
        List<Product> list = Arrays.asList();
//        Mockito.when(repo.findByPriceLessThanEqual(-50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPriceBetween(10f,-50f);
        Assertions.assertThat(queryResult).isNull();
    }

    @Test
    void testFindingInvalidPriceRange2comp(){
        List<Product> list = Arrays.asList();
//        Mockito.when(repo.findByPriceLessThanEqual(-50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPriceBetween(-10f,50f);
        Assertions.assertThat(queryResult).isNull();
    }

    @Test
    void testFindingInvalidPriceRange2comp2(){
        List<Product> list = Arrays.asList();
//        Mockito.when(repo.findByPriceLessThanEqual(-50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPriceBetween(-10f,-50f);
        Assertions.assertThat(queryResult).isNull();
    }

    @Test
    void testFindingInvalidPriceRange3(){
        List<Product> list = Arrays.asList();
//        Mockito.when(repo.findByPriceLessThanEqual(50f)).thenReturn(list);
        List<Product> queryResult = service.searchForPriceBetween(50f,10f);
        Assertions.assertThat(queryResult).isNull();
    }

    //finding a product by brand that does not exist
    @Test
    void testFindingInvalidBrand(){
        List<Product> list = List.of();
        Mockito.when(repo.findByBrandNameLikeIgnoreCase("petly")).thenReturn(list);
        List<Product> queryResult = service.searchForBrand("petly");
        Assertions.assertThat(queryResult).isEmpty();
    }
    @Test
    void testFindingInvalidBrand2(){
        //        Mockito.when(repo.findByBrandNameLikeIgnoreCase("petly")).thenReturn(list);
        List<Product> queryResult = service.searchForBrand(null);
        Assertions.assertThat(queryResult).isNull();
    }

    @Test
    void findProductByNonExistantCategory(){
        List<Product> list = Arrays.asList();
        Mockito.when(repo.findByCategoryLikeIgnoreCase("FOOD")).thenReturn(list);
        List<Product> queryResult = service.searchForCategory("FOOD");
        Assertions.assertThat(queryResult).isEmpty();
    }

    @Test
    void findProductByNonExistantTargetAnimal(){
        List<Product> list = Arrays.asList();
        Mockito.when(repo.findByTargetAnimalLikeIgnoreCase("DOG")).thenReturn(list);
        List<Product> queryResult = service.searchByTargetAnimal("DOG");
        Assertions.assertThat(queryResult).isEmpty();
    }
    //finding a product by id that does not exist
    @Test
    void testFindingInvalidId(){
        Optional<Product> optional = Optional.empty();
        Mockito.when(repo.findById(15L)).thenReturn(optional);
        Product queryResult = service.findById(15L);
        Assertions.assertThat(queryResult).isNull();
    }
    @Test
    void testFindingInvalidId2(){
        //        Mockito.when(repo.findById(15L)).thenReturn(optional);
        Product queryResult = service.findById(null);
        Assertions.assertThat(queryResult).isNull();
    }
    //adding a product that already exists
    @Test
    void testAddingAnExistingProduct(){
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Mockito.when(repo.existsInDB(p.getProductName(),p.getBrandName())).thenReturn(true);
        Assertions.assertThat(service.addNewProduct(p)).isFalse();
    }
    @Test
    void testAddingAnNullProduct(){
        Product p = null;
//        Mockito.when(repo.existsInDB(p.getProductName(),p.getBrandName())).thenReturn(true);
        Assertions.assertThat(service.addNewProduct(p)).isFalse();
    }
    //deleting a product that does not exist
    @Test
    void testDeleteProductDoesntExist(){
        Mockito.when(repo.existsById(15L)).thenReturn(false);
        Assertions.assertThat(service.delete(15L)).isFalse();
    }
    @Test
    void testDeleteProductNullID(){
//        Mockito.when(repo.existsById(15L)).thenReturn(false);
        Assertions.assertThat(service.delete(null)).isFalse();
    }
    //adding inappropriate input
    @Test
    void testGettingNone(){
        Mockito.when(repo.count()).thenReturn(0L);
        Assertions.assertThat(service.findAll().size()).isEqualTo(0);
    }
}
