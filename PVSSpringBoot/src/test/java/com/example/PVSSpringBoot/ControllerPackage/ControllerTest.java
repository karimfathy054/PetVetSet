package com.example.PVSSpringBoot.ControllerPackage;
import com.example.PVSSpringBoot.Entities.ProductFront;
import com.example.PVSSpringBoot.Entities.ProductFrontBuilder;
import com.example.PVSSpringBoot.repositories.UsersRepo;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(Controller.class)
@ContextConfiguration(classes = Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class ControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RequestService requestService;
    @MockBean
    UsersRepo usersRepo;

    @Test
    void addNewProductCorrect() throws Exception {
        ProductFrontBuilder productBuilder = new ProductFrontBuilder();
        ProductFront product = productBuilder.get();
        Mockito.when(requestService.addNewProduct(any())).thenReturn("Added to database...");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/addNewProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "   \"name\": \"foodStrong1221111\",\n" +
                                "    \"brandName\": \"petpet8\",\n" +
                                "    \"description\": \"fooooooood\",\n" +
                                "    \"price\": \"1.66\",\n" +
                                "    \"categoryName\": \"food\",\n" +
                                "    \"targetAnimal\": \"cat\",\n" +
                                "    \"userEmail\": \"abdo2@gmail.com\"\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Added to database..."));
        Mockito.verify(requestService,Mockito.times(1)).addNewProduct(any());
    }
    @Test
    void addNewProductFailed() throws Exception {
        ProductFrontBuilder productBuilder = new ProductFrontBuilder();
        ProductFront product = productBuilder.get();
        Mockito.when(requestService.addNewProduct(any())).thenReturn("The email of user is wrong");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/addNewProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "   \"name\": \"foodStrong1221111\",\n" +
                                "    \"brandName\": \"petpet8\",\n" +
                                "    \"description\": \"fooooooood\",\n" +
                                "    \"price\": \"1.66\",\n" +
                                "    \"categoryName\": \"food\",\n" +
                                "    \"targetAnimal\": \"cat\",\n" +
                                "    \"userEmail\": \"abdo4@gmail.com\"\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The email of user is wrong"));
        Mockito.verify(requestService,Mockito.times(1)).addNewProduct(any());
    }

    @Test
    void getRequestProductByUserEmailCorrect() throws Exception {
        ProductFrontBuilder productBuilder = new ProductFrontBuilder();
        ProductFront product = productBuilder.get();
        product.setUserEmail("abdo4@gmail.com");
        product.setTargetAnimal("cat");
        product.setPrice(15);
        product.setDescription("food");
        product.setApproved(false);
        product.setCategoryName("food");
        product.setBrandName("pet");
        product.setProductName("foodStrong1221111");
        product.setPhoto("photo_2023-09-29_13-41-12.jpg");
        product.setProductId(1);
        Mockito.when(requestService.getProductsByUserEmail(any())).thenReturn((List.of(product)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/getProductByUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\": \"abdo4@gmail.com\"\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\n" +
                                "   \"productName\": \"foodStrong1221111\",\n" +
                                "    \"price\": 15.0,\n" +
                                "    \"brandName\": \"pet\",\n" +
                                "    \"description\": \"food\",\n" +
                                "    \"categoryName\": \"food\",\n" +
                                "    \"targetAnimal\": \"cat\",\n" +
                                "    \"userEmail\": \"abdo4@gmail.com\",\n" +
                                "     \"productId\": 1,\n" +
                                "     \"approved\": false,\n" +
                                "    \"photo\": \"photo_2023-09-29_13-41-12.jpg\"\n" +
                                "}]"
                ));
        Mockito.verify(requestService,Mockito.times(1)).getProductsByUserEmail(any());
    }
    @Test
    void getRequestProductByUserEmailError() throws Exception {
        List<ProductFront> listProductFront = new ArrayList<>();
        Mockito.when(requestService.getProductsByUserEmail(any())).thenReturn(listProductFront);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/getProductByUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\": \"abdo4@gmail.com\"\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
        Mockito.verify(requestService,Mockito.times(1)).getProductsByUserEmail(any());
    }
    @Test
    void deleteRequestProductByIdCorrect() throws Exception {
        Mockito.when(requestService.deleteProductById(any())).thenReturn("delete success from database...");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/deleteRequestProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("delete success from database..."));
        Mockito.verify(requestService,Mockito.times(1)).deleteProductById(any());
    }
    @Test
    void deleteRequestProductByIdError() throws Exception {
        Mockito.when(requestService.deleteProductById(any())).thenReturn("this product is not found...");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/deleteRequestProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 100\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("this product is not found..."));
        Mockito.verify(requestService,Mockito.times(1)).deleteProductById(any());
    }
}