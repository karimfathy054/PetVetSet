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

    public static final String ADDED_TO_DATABASE = "Added to database...";
    public static final String WRONG_USER_EMAIL = "The email of user is wrong";
    public static final String PRODUCT_NOT_FOUND = "this product is not found...";
    public static final String PRODUCT_DELETE_SUCCESS = "delete success from database...";
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RequestProductRepo requestProductRepo;

//    public String setAdmin(Long adminId, Long userId) {
//        var enrtyAdmin = usersRepo.findById(userId);
//        var enrtyUser = usersRepo.findById(userId);
//        if(enrtyAdmin.isEmpty()){
//            return "Wrong admin id";
//        }
//        if(enrtyUser.isEmpty()){
//            return "Wrong user id";
//        }
//        User admin = enrtyAdmin.get();
//        User user = enrtyUser.get();
//        if(!admin.getIs_admin()){
//            return "User granting admin access is not admin";
//        }
//        user.setIs_admin(true);
//        usersRepo.save(user);
//        return "Admin access granted successfully";
//    }
//
//    public String removeAdminAccess(Long adminId, Long userId) {
//        var enrtyAdmin = usersRepo.findById(adminId);
//        var enrtyUser = usersRepo.findById(userId);
//        if(enrtyAdmin.isEmpty()){
//            return "Wrong admin id";
//        }
//        if(enrtyUser.isEmpty()){
//            return "Wrong user id";
//        }
//        User admin = enrtyAdmin.get();
//        User user = enrtyUser.get();
//        if(!admin.getIs_admin()){
//            return "User removing admin access is not admin";
//        }
//        user.setIs_admin(false);
//        usersRepo.save(user);
//        return "Admin access removed successfully";
//    }

//    public UserFront getUser(Integer id) {
//        var entry = usersRepo.findById(Long.valueOf(id));
//        if( entry.isEmpty()){
//            return new UserFront(-1, "Id not valid", "", false);
//        }
//        User user = entry.get();
//        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
//    }

    public UserFront getUserByEmail(String email) {
        var entry = usersRepo.findByEmail(email);
        if(entry.isEmpty()){
            return new UserFront(-1, "Email not valid", "", false,"");
        }
        User user = entry.get();
        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin(),user.getProfile_photo());
    }

//    public String deleteUser(Long adminId, Long userID) {
//        var enrtyAdmin = usersRepo.findById(adminId);
//        var enrtyUser = usersRepo.findById(userID);
//        if(enrtyAdmin.isEmpty()){
//            return "Wrong admin id";
//        }
//        if(enrtyUser.isEmpty()){
//            return "Wrong user id";
//        }
//        User admin = enrtyAdmin.get();
//        User user = enrtyUser.get();
//        if(!admin.getIs_admin()){
//            return "Deleting user doesn't have admin access";
//        }
//        if(user.getUser_id()==0){
//            return "Can't delete master admin";
//        }
//        usersRepo.deleteById(Long.valueOf(user.getUser_id()));
//        return "User deleted successfully";
//    }
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

    public List<ProductFront> getProductByUserEmail(String email) {
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
