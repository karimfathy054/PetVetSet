package com.example.PVSSpringBoot.ControllerPackage;


import com.example.PVSSpringBoot.Entities.*;
import com.example.PVSSpringBoot.repositories.RequestProductRepo;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    public static final String ADDED_TO_DATABASE = "Added To Database...";
    public static final String WRONG_USER_EMAIL = "Wrong User Email!!";
    public static final String PRODUCT_NOT_FOUND = "Product Not Found...";
    public static final String PRODUCT_DELETE_SUCCESS = "Deleted From Database...";
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RequestProductRepo requestProductRepo;

    public String setAdmin(Long adminId, Long userId) {
        var entryAdmin = usersRepo.findById(adminId);
        var entryUser = usersRepo.findById(userId);
        if(entryAdmin.isEmpty()){
            return "Wrong Admin ID!!";
        }
        if(entryUser.isEmpty()){
            return "Wrong User ID";
        }
        User admin = entryAdmin.get();
        User user = entryUser.get();
        if(!admin.getIs_admin()){
            return "User Granting Admin Access Is Not Admin";
        }
        user.setIs_admin(true);
        usersRepo.save(user);
        return "Admin Access Granted Successfully";
    }

    public String removeAdminAccess(Long adminId, Long userId) {
        var entryAdmin = usersRepo.findById(adminId);
        var entryUser = usersRepo.findById(userId);
        if(entryAdmin.isEmpty()){
            return "Wrong Admin ID!!";
        }
        if(entryUser.isEmpty()){
            return "Wrong User ID";
        }
        User admin = entryAdmin.get();
        User user = entryUser.get();
        if(!admin.getIs_admin()){
            return "User Removing Admin Access Is Not Admin";
        }
        if(user.getUser_id()==0) return "Can't Remove The Master Admin";
        user.setIs_admin(false);
        usersRepo.save(user);
        return "Admin Access Removed Successfully";
    }

    public UserFront getUserById(long id) {
        var entry = usersRepo.findById(id);
        if( entry.isEmpty()){
            return new UserFront(-1, "No User With The Provided ID!!", "", false);
        }
        User user = entry.get();
        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
    }

    public UserFront getUserByEmail(String email) {
        var entry = usersRepo.findByEmail(email);
        if(entry.isEmpty()){
            return new UserFront(-1, "Email not valid", "", false);
        }
        User user = entry.get();
        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
    }

    public String deleteUser(Long adminId, Long userID) {
        var entryAdmin = usersRepo.findById(adminId);
        var entryUser = usersRepo.findById(userID);
        if(entryAdmin.isEmpty()){
            return "Wrong Admin ID";
        }
        if(entryUser.isEmpty()){
            return "Wrong User ID";
        }
        User admin = entryAdmin.get();
        User user = entryUser.get();
        if(!admin.getIs_admin()){
            return "Deleting User Doesn't Have Admin Access";
        }
        if(user.getUser_id()==0){
            return "Can't Delete Master Admin";
        }
        if(user.getIs_admin() && admin.getUser_id() != 0){
            return "Only Master Admin Can Delete Other Admins!!";
        }
        usersRepo.deleteById(Long.valueOf(user.getUser_id()));
        return "User Deleted Successfully";
    }
    public String addNewProduct(ProductFront product){
        RequestProduct reqProduct;
        try {
            reqProduct = new RequestProductAdapter(
                    requestProductRepo,
                    usersRepo
            ).frontToNewData(product);
            requestProductRepo.save(reqProduct);
            return ADDED_TO_DATABASE;
        }catch (Exception e){
            e.printStackTrace();
            return WRONG_USER_EMAIL;
        }

    }

    public String getJoinDate(long id) {
        var user = usersRepo.findById(id);
        if(user.isEmpty()){
            return "Wrong Id!!";
        }
        return user.get().getJoin_date().toString();
    }

    public String changeUserName(long id, String newName) {
        var userEntry = usersRepo.findById(id);
        if(userEntry.isEmpty())
            return "Wrong Id!!";
        User user = userEntry.get();
        user.setUser_name(newName);
        usersRepo.save(user);
        return "User Saved Successfully!!";
    }



    public String deleteProductById( Long id) {
        Optional<RequestProduct> reqProduct = requestProductRepo.findById(id);
        if(reqProduct.isEmpty()){
            return PRODUCT_NOT_FOUND;
        }
        requestProductRepo.deleteById(id);
        return PRODUCT_DELETE_SUCCESS;


    }

    public List<ProductFront> getProductsByUserEmail(String email) {
        var entryUser = usersRepo.findByEmail(email);
        List<ProductFront> listProductFront = new ArrayList<>();
        if(entryUser.isEmpty()){
            return listProductFront;
        }
        List<RequestProduct> listProduct = requestProductRepo.findByUserId(entryUser.get().getUser_id());
        for(RequestProduct it: listProduct){
            listProductFront.add(new RequestProductAdapter(requestProductRepo, usersRepo)
                    .dataToFront(it));
        }
        return listProductFront;

    }
}
