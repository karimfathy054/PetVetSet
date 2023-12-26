package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.*;
import com.example.PVSSpringBoot.repositories.*;
import com.example.PVSSpringBoot.services.PetManagementService;
import com.example.PVSSpringBoot.services.ProductManagementService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.sql.Date;
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
    ProductRepository productRepository;
    @Mock
    RequestPetRepo requestPetRepo ;
    @Mock
    PetRepository petRepo;

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

        List<ProductFront> res = serviceUnderTest.getProductsByUserEmail(productFront.getUserEmail());

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
        assertEquals(listProductFront,serviceUnderTest.getProductsByUserEmail(user.getEmail()));
    }
    //-----------------------------------------------------------------------------------
    @Test
    void getAllRequestProducts(){
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
        BDDMockito.given(this.usersRepo.findById(user.getUser_id()))
                .willReturn(Optional.of(user));
        BDDMockito.given(this.requestProductRepo.findAll())
                .willReturn(List.of(requestProduct));
        List<ProductFront> res = serviceUnderTest.getAllRequestProducts();
        Mockito.verify(requestProductRepo).findAll();
        Assertions.assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(List.of(productFront));

    }
    @Test
    void acceptProductByIdFound(){
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
        Product product = new Product();
        product.setProductName(requestProduct.getProductName());
        product.setPrice(requestProduct.getPrice());
        product.setDescription(requestProduct.getDescription());
        product.setBrandName(requestProduct.getBrandName());
        product.setTargetAnimal(requestProduct.getTargetAnimal());
        product.setCategory(requestProduct.getCategoryName());
        product.setImageLink(requestProduct.getProductPhoto());
        product.setRating(0F);
        product.setNoOfRating(0L);

        BDDMockito.given(this.requestProductRepo.findById(requestProduct.getProductId()))
                .willReturn(Optional.of(requestProduct));
        BDDMockito.given(this.productRepository.save(product))
                .willReturn(product);
        String res = serviceUnderTest.acceptProductById(5656L);
        Assertions.assertThat(res)
                .isEqualTo("Accept success.");
    }
    @Test
    void acceptProductByIdNotFound(){
        RequestProduct requestProduct1 = new RequestProduct();

        BDDMockito.given(this.requestProductRepo.findById(55L))
                .willReturn(Optional.empty());
        String res = serviceUnderTest.acceptProductById(55L);
        Assertions.assertThat(res)
                .isEqualTo("Product Not Found...");
    }
    @Test
    void acceptProductByIdFailed(){
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
        Product product = new Product();
        product.setProductName(requestProduct.getProductName());
        product.setPrice(requestProduct.getPrice());
        product.setDescription(requestProduct.getDescription());
        product.setBrandName(requestProduct.getBrandName());
        product.setTargetAnimal(requestProduct.getTargetAnimal());
        product.setCategory(requestProduct.getCategoryName());
        product.setImageLink(requestProduct.getProductPhoto());
        product.setRating(0F);
        product.setNoOfRating(0L);
        Product product1 = new Product();

        BDDMockito.given(this.requestProductRepo.findById(requestProduct.getProductId()))
                .willReturn(Optional.of(requestProduct));
        BDDMockito.given(this.productRepository.save(product))
                .willReturn(product1);
        String res = serviceUnderTest.acceptProductById(5656L);
        Assertions.assertThat(res)
                .isEqualTo("Accept Failed..");
    }
    @Test
    void addRequestPetFalse1(){
        boolean res = serviceUnderTest.addRequestPet(null);
        Assertions.assertThat(res)
                .isEqualTo(false);
    }
    @Test
    void addRequestPetFalse2(){
        requestPet reqPet = new requestPet();
        BDDMockito.given(this.requestPetRepo.existsInDB(reqPet.getName(),reqPet.getType(),reqPet.getBreed()))
                .willReturn(false);
        boolean res = serviceUnderTest.addRequestPet(reqPet);
        Assertions.assertThat(res)
                .isEqualTo(false);

    }
    @Test
    void addRequestPetFalse3(){
        requestPet reqPet = new requestPet();
        reqPet.setUserEmail("abcd132@gmail.com");
        User user = User.builder()
                .email("abcd132@gmail.com")
                .user_id(123L)
                .build();
        BDDMockito.given(this.usersRepo.findByEmail(user.getEmail()))
                .willReturn(Optional.empty());
        boolean res = serviceUnderTest.addRequestPet(reqPet);
        Assertions.assertThat(res)
                .isEqualTo(false);
    }
    @Test
    void addRequestPetTrue(){
        requestPet reqPet = new requestPet();
        reqPet.setUserEmail("abcd132@gmail.com");
        User user = User.builder()
                .email("abcd132@gmail.com")
                .user_id(123L)
                .build();
        BDDMockito.given(this.usersRepo.findByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        boolean res = serviceUnderTest.addRequestPet(reqPet);
        Assertions.assertThat(res)
                .isEqualTo(true);
    }
    @Test
    void getRequestPetByUserEmail(){
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
        BDDMockito.given(this.requestPetRepo.findByUserEmail(reqPet.getUserEmail()))
                .willReturn(reqPetList);
        List<requestPet> res = serviceUnderTest.getRequestPetsByUserEmail(reqPet.getUserEmail());
        Assertions.assertThat(res)
                .isEqualTo(reqPetList);

    }
    @Test
    void getAllRequestPet(){
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
        BDDMockito.given(this.requestPetRepo.findAll())
                .willReturn(reqPetList);
        List<requestPet> res = serviceUnderTest.getAllRequestPets();
        Assertions.assertThat(res)
                .isEqualTo(reqPetList);

    }
    @Test
    void acceptPetByIdNotFound(){
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
        BDDMockito.given(this.requestPetRepo.findById(reqPet.getProductId()))
                .willReturn(Optional.empty());
        String res = serviceUnderTest.acceptPetById(reqPet.getProductId());
        Assertions.assertThat(res)
                .isEqualTo("Pet Not Found...");

    }
    @Test
    void acceptPetByIdFailed(){
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
        Pet pet = Pet
                .builder()
                .age((short) 2)
                .name("meshmesh")
                .breed("cat2")
                .type("cat")
                .birthDate(Date.valueOf("2023-1-15"))
                .description("cat cat")
                .imageLink("mm.jpg")
                .id(4L)
                .build();
        BDDMockito.given(this.requestPetRepo.findById(reqPet.getProductId()))
                .willReturn(Optional.of(reqPet));
        BDDMockito.given(this.petRepo.existsInDB(pet.getName(),pet.getType(),pet.getBreed()))
                .willReturn(true);
        String res = serviceUnderTest.acceptPetById(reqPet.getProductId());
        Assertions.assertThat(res)
                .isEqualTo("Accept Failed..");

    }
    @Test
    void acceptPetByIdSuccess(){
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
            Pet pet = Pet
                    .builder()
                    .age((short) 2)
                    .name("meshmesh")
                    .breed("cat2")
                    .type("cat")
                    .birthDate(Date.valueOf("2023-1-15"))
                    .description("cat cat")
                    .imageLink("mm.jpg")
                    .id(4L)
                    .build();
            BDDMockito.given(this.requestPetRepo.findById(reqPet.getProductId()))
                    .willReturn(Optional.of(reqPet));
            BDDMockito.given(this.petRepo.existsInDB(pet.getName(),pet.getType(),pet.getBreed()))
                    .willReturn(false);
            String res = serviceUnderTest.acceptPetById(reqPet.getProductId());
            Assertions.assertThat(res)
                    .isEqualTo("Accept success.");
    }
    @Test
    void refuseRequestTestByIdFailed(){
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
        BDDMockito.given(this.requestPetRepo.findById(reqPet.getProductId()))
                .willReturn(Optional.empty());
        String res = serviceUnderTest.refuseRequestPetById(reqPet.getProductId());
        Assertions.assertThat(res)
                .isEqualTo("Pet Not Found...");
    }
    @Test
    void refuseRequestTestById(){
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
        BDDMockito.given(this.requestPetRepo.findById(reqPet.getProductId()))
                .willReturn(Optional.of(reqPet));
        String res = serviceUnderTest.refuseRequestPetById(reqPet.getProductId());
        Assertions.assertThat(res)
                .isEqualTo("Deleted From Database...");
    }

}
