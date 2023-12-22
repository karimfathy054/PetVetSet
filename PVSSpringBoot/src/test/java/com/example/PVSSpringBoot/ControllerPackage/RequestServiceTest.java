package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.ProductFront;
import com.example.PVSSpringBoot.Entities.ProductFrontBuilder;
import com.example.PVSSpringBoot.Entities.RequestProduct;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.RequestProductRepo;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class RequestServiceTest {


    @Mock
    UsersRepo usersRepo;
    @Mock
    RequestProductRepo requestProductRepo;
    @Mock
    RequestProductAdapter adapter;

    @InjectMocks
    private RequestService serviceUnderTest;

    @BeforeTestMethod
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewProduct() {

        ProductFront productFront = new ProductFrontBuilder()
                .setProductName("KatzFood")
                .setCategoryName("Cat food")
                .setPrice(100)
                .setBrandName("Catso")
                .setDescription("Delicious cat food")
                .setTargetAnimal("cat")
                .setUserEmail("abcd132@gmail.com")
                .get();

        User user = User.builder()
                .email("abcd132@gmail.com")
                .user_id(123L)
                .build();

        BDDMockito.given(this.usersRepo.findByEmail(productFront.getUserEmail()))
                .willReturn(Optional.of(user));

        String res = serviceUnderTest.addNewProduct(productFront);

        assertEquals(res, RequestService.ADDED_TO_DATABASE);

        //check adapter receives productFront
        //check repo saves requestProduct
        //assert return value
    }
    @Test
    void ProductAdderNotFound(){
        ProductFront productFront = new ProductFrontBuilder()
                .setProductName("KatzFood")
                .setCategoryName("Cat food")
                .setPrice(100)
                .setBrandName("Catso")
                .setDescription("Delicious cat food")
                .setTargetAnimal("cat")
                .setUserEmail("abcd132@gmail.com")
                .get();

        BDDMockito.given(this.usersRepo.findByEmail(productFront.getUserEmail()))
                .willReturn(Optional.empty());

        String res = serviceUnderTest.addNewProduct(productFront);

        assertEquals(res, RequestService.WRONG_USER_EMAIL);
    }

    @Test
    void canDeleteProductById() {

        ProductFront productFront = new ProductFrontBuilder()
                .setProductName("KatzFood")
                .setCategoryName("Cat food")
                .setPrice(100)
                .setBrandName("Catso")
                .setDescription("Delicious cat food")
                .setTargetAnimal("cat")
                .setUserEmail("abcd132@gmail.com")
                .setProductId(5656L)
                .get();

        BDDMockito.given(this.requestProductRepo.findById(productFront.getProductId()))
                .willReturn(Optional.of(RequestProduct.builder().build()));
        String res = serviceUnderTest.deleteProductById(productFront.getProductId());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(requestProductRepo).deleteById(captor.capture());

        assertEquals(res, RequestService.PRODUCT_DELETE_SUCCESS);
    }

    @Test
    void productNotFoundOnDelete(){
        ProductFront productFront = new ProductFrontBuilder()
                .setUserEmail("abcd132@gmail.com")
                .setProductId(5656L)
                .get();

        BDDMockito.given(this.requestProductRepo.findById(productFront.getProductId()))
                .willReturn(Optional.empty());

        String res = serviceUnderTest.deleteProductById(productFront.getProductId());
        assertEquals(res, RequestService.PRODUCT_NOT_FOUND);
    }

    @Test
    void canGetProductByUserEmail() {
        ProductFront productFront = new ProductFrontBuilder()
                .setProductName("KatzFood")
                .setCategoryName("Cat food")
                .setPrice(100)
                .setBrandName("Catso")
                .setDescription("Delicious cat food")
                .setTargetAnimal("cat")
                .setUserEmail("abcd132@gmail.com")
                .setProductId(5656L)
                .get();

        RequestProduct requestProduct = RequestProduct
                .builder()
                .productName("KatzFood")
                .categoryName("Cat food")
                .price(100)
                .brandName("Catso")
                .description("Delicious cat food")
                .targetAnimal("cat")
                .userId(123L)
                .productId(5656L)
                .build();

        User user = User.builder()
                .email("abcd132@gmail.com")
                .user_id(123L)
                .build();

        BDDMockito.given(this.usersRepo.findByEmail(user.getEmail()))
                .willReturn(Optional.of(user));

        BDDMockito.given(this.usersRepo.findById(user.getUser_id()))
                .willReturn(Optional.of(user));

        BDDMockito.given(this.requestProductRepo.findByUserId(user.getUser_id()))
                .willReturn(List.of(requestProduct));

        List<ProductFront> res = serviceUnderTest.getProductByUserEmail(productFront.getUserEmail());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(requestProductRepo).findByUserId(captor.capture());

        Assertions.assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(List.of(productFront));

    }

    @Test
    void userNotFoundWhenGettingProductsByEmail(){
        List<ProductFront> listProductFront = new ArrayList<>();
        User user = User.builder()
                .email("abcd132@gmail.com")
                .user_id(123L)
                .build();

        BDDMockito.given(this.usersRepo.findByEmail(user.getEmail()))
                .willReturn(Optional.empty());
        assertEquals(listProductFront,serviceUnderTest.getProductByUserEmail(user.getEmail()));
    }
}
