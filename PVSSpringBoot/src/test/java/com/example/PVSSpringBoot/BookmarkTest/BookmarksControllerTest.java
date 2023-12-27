package com.example.PVSSpringBoot.BookmarkTest;

import com.example.PVSSpringBoot.ControllerPackage.BookmarksController;
import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.services.BookmarkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@WebMvcTest(BookmarksController.class)
@ContextConfiguration(classes = BookmarksController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
public class BookmarksControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookmarkService bookmarkService;

    //test getting bookmarks of a user
    @Test
    void test1() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .productName("pname")
                .price(10f)
                .brandName("Bname")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(List.of(product));
        when(bookmarkService.getBookmarksOf(1L)).thenReturn(List.of(product));
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json));
        verify(bookmarkService,times(1)).getBookmarksOf(1L);
    }
    //test getting empty list of bookmarks
    @Test
    void test2() throws Exception {

        when(bookmarkService.getBookmarksOf(1L)).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
        verify(bookmarkService,times(1)).getBookmarksOf(1L);
    }
    //test adding a bookmark
    @Test
    void test3() throws Exception {
        when(bookmarkService.addBookmark(1L,1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/bookmarks/add/user=1&&product=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("bookmark added"));
        verify(bookmarkService,times(1)).addBookmark(1L,1L);
    }
    //test adding an invalid bookmark
    @Test
    void test4() throws Exception {
        when(bookmarkService.addBookmark(1L,1L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.put("/bookmarks/add/user=1&&product=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("can't add bookmark"));
        verify(bookmarkService,times(1)).addBookmark(1L,1L);
    }
    //test removing a bookmark
    @Test
    void test5() throws Exception {
        when(bookmarkService.removeBookmark(1L,1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/bookmarks/remove/user=1&&product=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("bookmark removed"));
        verify(bookmarkService,times(1)).removeBookmark(1L,1L);
    }
    //test removing an invalid bookmark
    @Test
    void test6() throws Exception {
        when(bookmarkService.removeBookmark(1L,1L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.put("/bookmarks/remove/user=1&&product=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("can't remove bookmark"));
        verify(bookmarkService,times(1)).removeBookmark(1L,1L);
    }
}
