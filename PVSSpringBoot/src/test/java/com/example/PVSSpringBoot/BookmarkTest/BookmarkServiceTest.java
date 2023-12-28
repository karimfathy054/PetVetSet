package com.example.PVSSpringBoot.BookmarkTest;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.BookmarksRepository;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import com.example.PVSSpringBoot.services.BookmarkService;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {
    @Mock
    UsersRepo usersRepo;
    @Mock
    ProductRepository productRepository;
    @Mock
    BookmarksRepository bookmarksRepository;

    @Autowired
    @InjectMocks
    BookmarkService bookmarkService;




    //test adding a bookmark to a user
    @Test
    void test1(){
        User user = User.builder()
                .userId(1L)
                .email("user1@email.com")
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .userName("user1")
                .isAdmin(false)
                .build();
        Product product = Product.builder()
                .id(1L)
                .price(10f)
                .brandName("bname")
                .productName("Pname")
                .build();
        Set<Product> bookmark = new LinkedHashSet<>();
        user.setBookmarks(bookmark);
        Set<User> users = new LinkedHashSet<>();
        product.setBookmarkingUsers(users);
        when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(usersRepo.save(user)).thenReturn(user);
        assertThat(bookmarkService.addBookmark(1L,1L)).isTrue();
    }
    //test removing a bookmark for a user
    @Test
    void test2(){
        User user = User.builder()
                .userId(1L)
                .email("user1@email.com")
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .userName("user1")
                .isAdmin(false)
                .build();
        Product product = Product.builder()
                .id(1L)
                .price(10f)
                .brandName("bname")
                .productName("Pname")
                .build();
        Set<Product> bookmark = new LinkedHashSet<>();
        bookmark.add(product);
        user.setBookmarks(bookmark);
        Set<User> users = new LinkedHashSet<>();
        users.add(user);
        product.setBookmarkingUsers(users);
        when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
        when(usersRepo.save(user)).thenReturn(user);
        assertThat(bookmarkService.removeBookmark(1L,1L)).isTrue();
    }
    //test getting a list of bookmarks
    @Test
    void test3(){
        Product product = Product.builder()
                .id(1L)
                .price(10f)
                .brandName("bname")
                .productName("Pname")
                .build();
        when(bookmarksRepository.findByBookmarkingUsers_UserId(1L)).thenReturn(List.of(product));
        assertThat(bookmarkService.getBookmarksOf(1L)).contains(product);
    }
    //test getting empty list of bookmarks
    @Test
    void test4(){
        when(bookmarksRepository.findByBookmarkingUsers_UserId(1L)).thenReturn(Collections.emptyList());
        assertThat(bookmarkService.getBookmarksOf(1L).size()).isEqualTo(0);
    }
    //test adding a bookmark to non existant user
    @Test
    void test5(){
        Product product = Product.builder()
                .id(1L)
                .price(10f)
                .brandName("bname")
                .productName("Pname")
                .build();
        when(usersRepo.findById(1L)).thenReturn(Optional.empty());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertThat(bookmarkService.addBookmark(1L,1L)).isFalse();
    }
    //test removing a bookmark from non-existent user
    @Test
    void test6(){
        when(usersRepo.findById(1L)).thenReturn(Optional.empty());
        assertThat(bookmarkService.addBookmark(1L,1L)).isFalse();
    }
    //test getting a list of bookmarks for non-existent user
    @Test
    void test7(){
        when(bookmarksRepository.findByBookmarkingUsers_UserId(1L)).thenReturn(Collections.emptyList());
        assertThat(bookmarkService.getBookmarksOf(1L).size()).isEqualTo(0);
    }
    //test adding a non-existent product to user's bookmark
    @Test
    void test8(){
        User user = User.builder()
                .userId(1L)
                .email("user1@email.com")
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .userName("user1")
                .isAdmin(false)
                .build();
        when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(bookmarkService.addBookmark(1L,1L)).isFalse();
    }
    //test removing a non-existent product from user's bookmarks
    @Test
    void test9(){
        User user = User.builder()
                .userId(1L)
                .email("user1@email.com")
                .joinDate(Date.valueOf(LocalDate.EPOCH))
                .userName("user1")
                .isAdmin(false)
                .build();
        when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
        assertThat(bookmarkService.addBookmark(1L,1L)).isFalse();
    }
    //test both non-existent user and product fail
    @Test
    void test10(){
        when(usersRepo.findById(1L)).thenReturn(Optional.empty());
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(bookmarkService.addBookmark(1L,1L)).isFalse();
    }

}
