package com.example.PVSSpringBoot.ControllerPackage;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.services.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@CrossOrigin
public class BookmarksController {

    @Autowired
    BookmarkService bookmarkService;
    @GetMapping("/{userId}")
    List<Product> getBookmarks(@PathVariable Long userId){
        return bookmarkService.getBookmarksOf(userId);
    }

    @PutMapping("/add/user={userId}&&product={productId}")
    String addBookmark(@PathVariable Long userId, @PathVariable Long productId){
        if(bookmarkService.addBookmark(productId,userId)) return "bookmark added";
        return "can't add bookmark";
    }

    @PutMapping("/remove/user={userId}&&product={productId}")
    String removeBookmark(@PathVariable Long userId , @PathVariable Long productId){
        if(bookmarkService.removeBookmark(userId,productId)) return "bookmark removed";
        return "can't remove bookmark";
    }
}
