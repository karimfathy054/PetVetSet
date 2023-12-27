package com.example.PVSSpringBoot.ControllerPackage;
import com.example.PVSSpringBoot.Entities.ProductFront;
import com.example.PVSSpringBoot.Entities.ProductFrontBuilder;
import com.example.PVSSpringBoot.Entities.requestPet;
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

import java.sql.Date;
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
    //
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
    @Test
    void getAllRequestProducts() throws Exception {
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
        Mockito.when(requestService.getAllRequestProducts()).thenReturn((List.of(product)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/getAllRequestProducts"))
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
        Mockito.verify(requestService,Mockito.times(1)).getAllRequestProducts();
    }
    @Test
    void acceptRequestProduct() throws Exception {
        Mockito.when(requestService.acceptProductById(any())).thenReturn("Accept success.");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/acceptRequestProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Accept success."));
        Mockito.verify(requestService,Mockito.times(1)).acceptProductById(any());
    }
    @Test
    void addNewPetSuccess () throws Exception {
        Mockito.when(requestService.addRequestPet(any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/addNewPet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"name\":\"meshmesh\",\n" +
                        " \"type\":\"cat\",\n" +
                        " \"breed\":\"cat2\",\n" +
                        " \"description\":\"cat cat\",\n" +
                        " \"birth_date\":\"2022-1-15\",\n" +
                        " \"image_link\":\"mm.jpg\",\n" +
                        " \"userEmail\":\"abdo@gmail.com\"\n" +
                        "}"
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("your request pet is added"));
        Mockito.verify(requestService,Mockito.times(1)).addRequestPet(any());
    }
    @Test
    void addNewPetFailed () throws Exception {
        Mockito.when(requestService.addRequestPet(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/addNewPet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"name\":\"meshmesh\",\n" +
                                " \"type\":\"cat\",\n" +
                                " \"breed\":\"cat2\",\n" +
                                " \"description\":\"cat cat\",\n" +
                                " \"birth_date\":\"2022-1-15\",\n" +
                                " \"image_link\":\"mm.jpg\",\n" +
                                " \"userEmail\":\"abdo@gmail.com\"\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Can not add Your request Pet"));
        Mockito.verify(requestService,Mockito.times(1)).addRequestPet(any());
    }
    @Test
    void getRequestPetsByUserEmail() throws Exception {
        requestPet reqPet = requestPet
                .builder()
                .age((short) 2)
                .name("meshmesh")
                .breed("cat2")
                .type("cat")
                .birthDate(Date.valueOf("2023-1-15"))
                .description("cat cat")
                .imageLink("mm.jpg")
                .userEmail("abdo@gmail.com")
                .productId(4L)
                .build();
        List<requestPet> reqPetList = new ArrayList<>();
        reqPetList.add(reqPet);
        Mockito.when(requestService.getRequestPetsByUserEmail(any())).thenReturn((reqPetList));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/getRequestPetByUserEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\": \"abdo@gmail.com\"\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\n" +
                                " \"name\":\"meshmesh\",\n" +
                                " \"type\":\"cat\",\n" +
                                " \"breed\":\"cat2\",\n" +
                                " \"description\":\"cat cat\",\n" +
                                " \"birthDate\":\"2023-01-15\",\n" +
                                " \"imageLink\":\"mm.jpg\",\n" +
                                " \"userEmail\":\"abdo@gmail.com\",\n" +
                                " \"productId\":4,\n" +
                                " \"age\":2\n" +
                                "}]"
                ));
        Mockito.verify(requestService,Mockito.times(1)).getRequestPetsByUserEmail(any());
    }
    @Test
    void getAllRequestPets() throws Exception {
        requestPet reqPet = requestPet
                .builder()
                .age((short) 2)
                .name("meshmesh")
                .breed("cat2")
                .type("cat")
                .birthDate(Date.valueOf("2023-1-15"))
                .description("cat cat")
                .imageLink("mm.jpg")
                .userEmail("abdo@gmail.com")
                .productId(4L)
                .build();
        List<requestPet> reqPetList = new ArrayList<>();
        reqPetList.add(reqPet);
        Mockito.when(requestService.getAllRequestPets()).thenReturn((reqPetList));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/getAllRequestPets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\n" +
                                " \"name\":\"meshmesh\",\n" +
                                " \"type\":\"cat\",\n" +
                                " \"breed\":\"cat2\",\n" +
                                " \"description\":\"cat cat\",\n" +
                                " \"birthDate\":\"2023-01-15\",\n" +
                                " \"imageLink\":\"mm.jpg\",\n" +
                                " \"userEmail\":\"abdo@gmail.com\",\n" +
                                " \"productId\":4,\n" +
                                " \"age\":2\n" +
                                "}]"
                ));
        Mockito.verify(requestService,Mockito.times(1)).getAllRequestPets();
    }
    @Test
    void acceptRequestPet() throws Exception {
        Mockito.when(requestService.acceptPetById(any())).thenReturn("Accept success.");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/acceptRequestPet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Accept success."));
        Mockito.verify(requestService,Mockito.times(1)).acceptPetById(any());
    }
    @Test
    void refuseRequestPetById() throws Exception {
        Mockito.when(requestService.refuseRequestPetById(any())).thenReturn("Refuse Success...");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/refuseRequestPet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1\n" +
                                "}"
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Refuse Success..."));
        Mockito.verify(requestService,Mockito.times(1)).refuseRequestPetById(any());
    }
}
