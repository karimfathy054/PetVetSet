package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.*;
import com.example.PVSSpringBoot.repositories.RequestProductRepo;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestProductAdapterTest {

    @Mock
    private UsersRepo usersRepo;
    @Mock
    private RequestProductRepo requestProductRepo;

    private RequestProductAdapter underTest;

    @BeforeEach
    void setup(){
        underTest = new RequestProductAdapter(requestProductRepo, usersRepo);
    }

    @Test
    void canConvertDataToFront() {
        RequestProduct rp = RequestProduct.builder()
                .price(100)
                .brandName("Catso")
                .productId(123L)
                .categoryName("Cat Food")
                .description("Delicious cat food")
                .targetAnimal("cat")
                .userId(122L)
                .productName("KatzFood")
                .join_date(Date.valueOf(LocalDate.now()))
                .build();

        User user = User.builder()
                .email("abcd132@gmail.com")
                .build();

        BDDMockito.given(usersRepo.findById(rp.getUserId()))
                .willReturn(Optional.of(user));

        ProductFront productFront = underTest.dataToFront(rp);

//        ArgumentCaptor<RequestProduct> captor =
//                ArgumentCaptor.forClass(RequestProduct.class);
//        Mockito.verify(usersRepo).findById(captor.capture().getProductId());

        assertEquals(productFront.getBrandName(), rp.getBrandName());
        assertFalse(productFront.isApproved());
        assertEquals(productFront.getCategoryName(), rp.getCategoryName());
        assertEquals(productFront.getDescription(), rp.getDescription());
        assertEquals(productFront.getPrice(), rp.getPrice());
        assertEquals(productFront.getTargetAnimal(), rp.getTargetAnimal());
        assertEquals(productFront.getProductName(), rp.getProductName());
        assertEquals(productFront.getUserEmail(), user.getEmail());
        assertEquals(productFront.getProductId(), rp.getProductId());
    }

    @Test
    void productOwnerNotFound(){
        RequestProduct rp = RequestProduct.builder().userId(456).build();
        BDDMockito.given(usersRepo.findById(rp.getUserId()))
                .willReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->underTest.dataToFront(rp))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(RequestProductAdapter.OWNER_NOT_FOUND);
    }

    @Test
    void canConvertFrontToNewData() {

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

        BDDMockito.given(usersRepo.findByEmail(productFront.getUserEmail()))
                .willReturn(Optional.of(user));

        RequestProduct requestProduct = underTest.frontToNewData(productFront);

        assertEquals(requestProduct.getProductName(),
                productFront.getProductName());
        assertEquals(requestProduct.getCategoryName(),
                productFront.getCategoryName());
        assertEquals(requestProduct.getPrice(), productFront.getPrice());
        assertEquals(requestProduct.getBrandName(),
                productFront.getBrandName());
        assertNotNull(requestProduct.getJoin_date());
        assertEquals(requestProduct.getDescription(),
                productFront.getDescription());
        assertEquals(requestProduct.getTargetAnimal(),
                productFront.getTargetAnimal());
        assertEquals(requestProduct.getUserId(), user.getUser_id());
    }

    @Test
    void ProductUploaderNotFound(){
        ProductFront productFront = new ProductFrontBuilder()
                .setUserEmail("haha@gmail.com")
                .get();
        BDDMockito.given(usersRepo
                        .findByEmail(productFront.getUserEmail()))
                .willReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->underTest.frontToNewData(productFront))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(RequestProductAdapter.OWNER_NEW_PRODUCT_NOT_FOUND);
    }
}
