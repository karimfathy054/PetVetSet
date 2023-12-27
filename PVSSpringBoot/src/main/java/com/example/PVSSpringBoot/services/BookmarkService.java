package com.example.PVSSpringBoot.services;

import com.example.PVSSpringBoot.Entities.Product;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.BookmarksRepository;
import com.example.PVSSpringBoot.repositories.ProductRepository;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {
    @Autowired
    UsersRepo usersRepo;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BookmarksRepository bookmarksRepository;

    public boolean addBookmark(Long productId,Long userId){
        Optional<User> userOptional = usersRepo.findById(userId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if(userOptional.isPresent() && productOptional.isPresent()){
            User user = userOptional.get();
            Product product = productOptional.get();
            user.addBookmark(product);
            usersRepo.save(user);
            return true;
        }else{
            return false;
        }
    }
    public boolean removeBookmark(Long productId,Long userId){
        Optional<User> userOptional = usersRepo.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.removeBookmark(productId)){
                usersRepo.save(user);
                return true;
            }
        }
        return false;
    }

    public List<Product> getBookmarksOf(Long userID){
        return bookmarksRepository.findByBookmarkingUsers_UserId(userID);
    }
}
