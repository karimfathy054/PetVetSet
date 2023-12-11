package com.example.PVSSpringBoot.ControllerPackage;


import com.example.PVSSpringBoot.Entities.*;
import com.example.PVSSpringBoot.repositories.RequestProductRepo;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RequestProductRepo requestProductRepo;

    public String setAdmin(Long adminId, Long userId) {
        var enrtyAdmin = usersRepo.findById(userId);
        var enrtyUser = usersRepo.findById(userId);
        if(enrtyAdmin.isEmpty()){
            return "Wrong admin id";
        }
        if(enrtyUser.isEmpty()){
            return "Wrong user id";
        }
        User admin = enrtyAdmin.get();
        User user = enrtyUser.get();
        if(!admin.getIs_admin()){
            return "User granting admin access is not admin";
        }
        user.setIs_admin(true);
        usersRepo.save(user);
        return "Admin access granted successfully";
    }

    public String removeAdminAccess(Long adminId, Long userId) {
        var enrtyAdmin = usersRepo.findById(adminId);
        var enrtyUser = usersRepo.findById(userId);
        if(enrtyAdmin.isEmpty()){
            return "Wrong admin id";
        }
        if(enrtyUser.isEmpty()){
            return "Wrong user id";
        }
        User admin = enrtyAdmin.get();
        User user = enrtyUser.get();
        if(!admin.getIs_admin()){
            return "User removing admin access is not admin";
        }
        user.setIs_admin(false);
        usersRepo.save(user);
        return "Admin access removed successfully";
    }

    public UserFront getUser(Integer id) {
        var entry = usersRepo.findById(Long.valueOf(id));
        if( entry.isEmpty()){
            return new UserFront(-1, "Id not valid", "", false);
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
        var enrtyAdmin = usersRepo.findById(adminId);
        var enrtyUser = usersRepo.findById(userID);
        if(enrtyAdmin.isEmpty()){
            return "Wrong admin id";
        }
        if(enrtyUser.isEmpty()){
            return "Wrong user id";
        }
        User admin = enrtyAdmin.get();
        User user = enrtyUser.get();
        if(!admin.getIs_admin()){
            return "Deleting user doesn't have admin access";
        }
        if(user.getUser_id()==0){
            return "Can't delete master admin";
        }
        usersRepo.deleteById(Long.valueOf(user.getUser_id()));
        return "User deleted successfully";
    }
    public String addNewProduct(ProductFront product){
        String date = java.time.LocalDate.now().toString();
        RequestProduct reqProduct = RequestProduct.builder().productName(product.getProductName()).categoryName(product.getCategoryName())
                        .price(product.getPrice()).brandName(product.getBrandName()).join_date(Date.valueOf(date)).description(product.getDescription())
                        .targetAnimal(product.getTargetAnimal()).userId(product.getUserId()).build();
        requestProductRepo.save(reqProduct);
        return "Added to database...";

    }

    public ProductFront getProductById(Long id) {
        Optional<RequestProduct> reqProduct = requestProductRepo.findById(id);
        if(reqProduct.isEmpty()){
            return null;
        }
        RequestProduct product = reqProduct.get();
        ProductFrontBuilder productFrontBuilder = new ProductFrontBuilder();
        productFrontBuilder.convertFromRequestToFront(product);
        return productFrontBuilder.get();


    }

    public String deleteProductById(Long admin, Long id) {
        var enrtyAdmin = usersRepo.findById(admin);
        if(enrtyAdmin.isEmpty()){
            return "Wrong admin id";
        }
        User admin2 = enrtyAdmin.get();
        if(!admin2.getIs_admin()){
            return "Deleting request product doesn't have admin access";
        }
        Optional<RequestProduct> reqProduct = requestProductRepo.findById(id);
        if(reqProduct.isEmpty()){
            return "this product is not found...";
        }
        requestProductRepo.deleteById(id);
        return "delete success from database...";


    }

    public List<ProductFront> getProductByUserId(Long id) {
        var enrtyUser = usersRepo.findById(id);
        if(enrtyUser.isEmpty()){
            return null;
        }
        List<RequestProduct> listProduct = requestProductRepo.findByUserId(id);
        List<ProductFront> listProductFront = new ArrayList<>();
        for(RequestProduct it: listProduct){
            ProductFrontBuilder productFrontBuilder = new ProductFrontBuilder();
            listProductFront.add(productFrontBuilder.convertFromRequestToFront(it).get());
        }
        return listProductFront;

    }

//    public UserFront login(String email, String password) {
//        System.out.println("body.get(\"email\") = " + email);
//        System.out.println("body.get(\"password\") = " +password);
//        var entry = usersRepo.findByEmail(email);
//        if(entry.isEmpty()){
//            return new UserFront(-1, "Email not valid", "", false);
//        }
//        User user = entry.get();
//        System.out.println("psee" + user.getPassword());
//        System.out.println(password);
//        password = passwordEncoder.encode(password);
//        System.out.println(password);
//        System.out.println("psww" + passwordEncoder.equals(password));
//        if(!user.getPassword().equals(passwordEncoder.encode(password))){
//            return new UserFront(-1, "Password is wrong", "", false);
//        }
//        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
//    }
}
