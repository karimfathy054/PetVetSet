package com.example.PVSSpringBoot.ProductTest;


import com.example.PVSSpringBoot.ControllerPackage.PetController;
import com.example.PVSSpringBoot.ControllerPackage.ProductController;
import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.services.ProductManagementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
public class ProductsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductManagementService service;

//     //smoke test
//     @Test
//     void test() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders.get("/products/hello")).andDo(print())
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().string("Hello"));
//     }

    //happy cases
    //ask for a product by id

    @Test
    void testAskForID() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        Mockito.when(service.findById(1L)).thenReturn(p);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/id=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null,category:null}"));
        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }

    //ask for all products

    @Test
    void testAskForAll() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = List.of(p);
        Mockito.when(service.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null,category:null}]"));
        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    //ask for a product by name
    @Test
    void testAskForName() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(service.searchForProduct("Pname")).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/name=Pname"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null,category:null}]"));
        Mockito.verify(service, Mockito.times(1)).searchForProduct("Pname");
    }
    //ask for a product by price
    @Test
    void testAskForPrice() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(service.searchForPrice(10f)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/price<=10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null,category:null}]"));
        Mockito.verify(service, Mockito.times(1)).searchForPrice(10f);
    }

    @Test
    void testAskForPriceRange() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(service.searchForPriceBetween(0f,10f)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/filter/price>=0&&price<=10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null,category:null}]"));
        Mockito.verify(service, Mockito.times(1)).searchForPriceBetween(0f,10f);
    }

    //ask for a product by brand
    @Test
    void testAskForBrand() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(service.searchForBrand("Bname")).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/brand/Bname"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null}]"));
        Mockito.verify(service, Mockito.times(1)).searchForBrand("Bname");
    }

    @Test
    void testAskForCategory() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .category("FOOD")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(service.searchForCategory("FOOD")).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/category=FOOD"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:null,imageLink:null,category:FOOD}]"));
        Mockito.verify(service, Mockito.times(1)).searchForCategory("FOOD");
    }

    @Test
    void testAskForTargetAnimal() throws Exception {
        Product p = Product.builder()
                .id(1L)
                .productName("Pname")
                .brandName("Bname")
                .category("FOOD")
                .targetAnimal("CAT")
                .price(10f)
                .build();
        List<Product> list = Collections.singletonList(p);
        Mockito.when(service.searchByTargetAnimal("CAT")).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/Target_Animal=CAT"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[{id:1,productName:Pname,brandName:Bname,price:10.0,targetAnimal:CAT,imageLink:null,category:FOOD}]"));
        Mockito.verify(service, Mockito.times(1)).searchByTargetAnimal("CAT");
    }


    //add product
    @Test
    void testAddingProduct() throws Exception {
        Product body = new Product();
        Mockito.when(service.addNewProduct(any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"Pname\",\"brandName\":\"Bname\",\"price\":\"10.0\"}"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("product added"));
        Mockito.verify(service, Mockito.times(1)).addNewProduct(any());
    }
    //delete product
    @Test
    void testDeleteProduct() throws Exception {
        Mockito.when(service.delete(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/id=1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string("deletion done"));
        Mockito.verify(service, Mockito.times(1)).delete(1L);
    }
    @Test
    void testDeleteNonExistant() throws Exception {
        Mockito.when(service.delete(1L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/id=1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string("deletion failed"));
        Mockito.verify(service, Mockito.times(1)).delete(1L);
    }

}
