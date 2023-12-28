package com.example.PVSSpringBoot.BookmarkTest;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.BookmarksRepository;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataBaseTest {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BookmarksRepository bookmarksRepository;

    @BeforeAll
    @Order(1)
    void setup(){
        Product product1 = Product.builder()
                .id(1L)
                .productName("p1")
                .brandName("b1")
                .price(10f)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .productName("p2")
                .brandName("b2")
                .price(20f)
                .build();
        Product product3 = Product.builder()
                .id(3L)
                .productName("p3")
                .brandName("b3")
                .price(30f)
                .build();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        User user1 = User.builder()
                .userId(1L)
                .email("user1@email.com")
                .userName("user1")
                .isAdmin(false)
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .build();
        User user2 = User.builder()
                .userId(2L)
                .email("user2@email.com")
                .userName("user2")
                .isAdmin(false)
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .build();
        User user3 = User.builder()
                .userId(3L)
                .email("user3@email.com")
                .userName("user3")
                .isAdmin(false)
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .build();
        usersRepo.save(user1);
        usersRepo.save(user2);
        usersRepo.save(user3);
    }

//    @AfterEach
//    void wipeDB(){
//        productRepository.deleteAll();
//        usersRepo.deleteAll();
//    }

    //test adding a bookmark for a user

    @Order(2)
    @Test
    public void testAddingABookmark(){
        User user = usersRepo.findById(1L).get();
        Product product = productRepository.findById(2L).get();
        user.addBookmark(product);
        usersRepo.save(user);
        List<Product> bookmarks = bookmarksRepository.findByBookmarkingUsers_UserId(1L);
        assertThat(bookmarks.size()).isEqualTo(1);
        assertThat(bookmarks.get(0)).isEqualTo(product);
    }
    //test all users bookmark all products

    @Order(3)

    @Test
    public void testAllUsersBookmarkAllProducts(){
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                User user = usersRepo.findById((long)i).get();
                Product product = productRepository.findById((long)j).get();
                user.addBookmark(product);
                usersRepo.save(user);
            }
        }
        List <Product> products = productRepository.findAll();
        List<Product> bookmarks1 = bookmarksRepository.findByBookmarkingUsers_UserId(1L);
        List<Product> bookmarks2 = bookmarksRepository.findByBookmarkingUsers_UserId(2L);
        List<Product> bookmarks3 = bookmarksRepository.findByBookmarkingUsers_UserId(3L);

        assertThat(bookmarks1.size()).isEqualTo(3);
        assertThat(bookmarks2.size()).isEqualTo(3);
        assertThat(bookmarks3.size()).isEqualTo(3);

        assertThat(bookmarks1).isEqualTo(products);
        assertThat(bookmarks2).isEqualTo(products);
        assertThat(bookmarks3).isEqualTo(products);
    }
    //test user remove a bookmark
    @Order(4)
    @Test
    void testRemoveABookmark(){
        User user = usersRepo.findById(1L).get();
        user.removeBookmark(1L);
        usersRepo.save(user);
        List<Product> bookmarks = bookmarksRepository.findByBookmarkingUsers_UserId(1L);
        assertThat(bookmarks.size()).isEqualTo(2);
        Product product2 = Product.builder()
                .id(2L)
                .productName("p2")
                .brandName("b2")
                .price(20f)
                .build();
        Product product3 = Product.builder()
                .id(3L)
                .productName("p3")
                .brandName("b3")
                .price(30f)
                .build();
        List<Product> trueResult = List.of(product2,product3);
        assertThat(bookmarks).containsAll(trueResult);
    }

    //test removing a user
    @Order(5)
    @Test
    void testRemovingAUser(){
        usersRepo.deleteById(1L);
        List<User> users = usersRepo.findAll();
        List<Product> products = productRepository.findAll();
        User user2 = User.builder()
                .userId(2L)
                .email("user2@email.com")
                .userName("user2")
                .isAdmin(false)
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .build();
        User user3 = User.builder()
                .userId(3L)
                .email("user3@email.com")
                .userName("user3")
                .isAdmin(false)
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .build();
        Product product1 = Product.builder()
                .id(1L)
                .productName("p1")
                .brandName("b1")
                .price(10f)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .productName("p2")
                .brandName("b2")
                .price(20f)
                .build();
        Product product3 = Product.builder()
                .id(3L)
                .productName("p3")
                .brandName("b3")
                .price(30f)
                .build();
        Set<Product> bookmarks = new LinkedHashSet<>();
        bookmarks.add(product1);
        bookmarks.add(product2);
        bookmarks.add(product3);
        user2.setBookmarks(bookmarks);
        user3.setBookmarks(bookmarks);
        List<User> finalUsers = List.of(user2,user3);

        assertThat(users.size()).isEqualTo(2);
        for (Product p :
                products) {
            assertThat(p.getBookmarkingUsers().size()).isEqualTo(2);
//            assertThat(p.getBookmarkingUsers()).containsAll(finalUsers);
        }
    }
    //test removing a product
    @Order(6)
    @Test
    void testRemovingAProduct(){
        productRepository.deleteById(1L);
        List<Product> products = productRepository.findAll();
        List<User>users = usersRepo.findAll();
        Product product2 = Product.builder()
                .id(2L)
                .productName("p2")
                .brandName("b2")
                .price(20f)
                .build();
        Product product3 = Product.builder()
                .id(3L)
                .productName("p3")
                .brandName("b3")
                .price(30f)
                .build();
        List<Product> finalProducts = List.of(product2,product3);
        assertThat(products.size()).isEqualTo(2);
        for (User u :
                users) {
            assertThat(u.getBookmarks().size()).isEqualTo(2);
            assertThat(u.getBookmarks()).containsAll(finalProducts);
        }
    }
    //in service
    //test adding a bookmark for non existant user
    //test adding a bookmark with non existant product
    //test adding a non existant product as a bookmark for non existant user
}
