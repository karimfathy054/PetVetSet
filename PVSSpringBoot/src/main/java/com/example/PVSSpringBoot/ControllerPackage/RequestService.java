package com.example.PVSSpringBoot.ControllerPackage;


import com.example.PVSSpringBoot.Entities.*;
import com.example.PVSSpringBoot.repositories.*;
import com.example.PVSSpringBoot.services.PetManagementService;
import com.example.PVSSpringBoot.services.ProductManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RequestService {

    public static final String ADDED_TO_DATABASE = "Added To Database...";
    public static final String WRONG_USER_EMAIL = "Wrong User Email!!";
    public static final String PRODUCT_NOT_FOUND = "Product Not Found...";
    public static final String PRODUCT_DELETE_SUCCESS = "Deleted From Database...";
    private static final String PET_NOT_FOUND = "Pet Not Found...";
    public static final String WRONG_ADMIN_ID = "Wrong Admin ID!!";
    public static final String WRONG_USER_ID = "Wrong User ID";
    public static final String USER_GRANTING_ACCESS_ERROR = "User Granting Admin Access Is Not Admin";
    public static final String ACCESS_GRANTED = "Admin Access Granted Successfully";
    public static final String USER_REMOVING_ACCESS_ERROR = "User Removing Admin Access Is Not Admin";
    public static final String USER_DELETING_ERROR = "Deleting User Doesn't Have Admin Access";
    public static final String ACCESS_REMOVED = "Admin Access Removed Successfully";
    public static final String MASTER_ADMIN_REMOVE_ERROR = "Can't Remove The Master Admin";
    public static final String MASTER_ADMIN_ONLY_ERROR = "Only Master Admin Can Can Perform This Operation!!";
    public static final String USER_DELETED = "User Deleted Successfully";
    public static final String USER_CHANGE_NAME = "User Name Changed Successfully!!";
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
   private  ProductRepository repo;
    @Autowired
    private RequestProductRepo requestProductRepo;
    @Autowired
    private RequestPetRepo requestPetRepo;
    @Autowired
    private PetRepository petRepository;

    public String setAdmin(Long adminId, Long userId) {
        var entryAdmin = usersRepo.findById(adminId);
        var entryUser = usersRepo.findById(userId);
        if(entryAdmin.isEmpty()){
            return WRONG_ADMIN_ID;
        }
        if(entryUser.isEmpty()){
            return WRONG_USER_ID;
        }
        User admin = entryAdmin.get();
        User user = entryUser.get();
        if(!admin.getIsAdmin()){
            return USER_GRANTING_ACCESS_ERROR;
        }
        user.setIsAdmin(true);
        usersRepo.save(user);
        return ACCESS_GRANTED;
    }

    public String removeAdminAccess(Long adminId, Long userId) {
        var entryAdmin = usersRepo.findById(adminId);
        var entryUser = usersRepo.findById(userId);
        if(entryAdmin.isEmpty()){
            return WRONG_ADMIN_ID;
        }
        if(entryUser.isEmpty()){
            return WRONG_USER_ID;
        }
        User admin = entryAdmin.get();
        User user = entryUser.get();
        if(!admin.getIsAdmin()){
            return USER_REMOVING_ACCESS_ERROR;
        }
        if(user.getUserId()==0) return MASTER_ADMIN_REMOVE_ERROR;
        user.setIsAdmin(false);
        usersRepo.save(user);
        return ACCESS_REMOVED;
    }

    public UserFront getUserById(long id) {
        var entry = usersRepo.findById(id);
        if( entry.isEmpty()){
            return new UserFront(-1, WRONG_USER_ID, "", false);
        }
        User user = entry.get();
        return UserFront.getUserFront(user);
    }

    public UserFront getUserByEmail(String email) {
        var entry = usersRepo.findByEmail(email);
        if(entry.isEmpty()){
            return new UserFront(-1, WRONG_USER_EMAIL, "", false);
        }
        User user = entry.get();
        return UserFront.getUserFront(user);
    }

    public String deleteUser(Long adminId, Long userID) {
        var entryAdmin = usersRepo.findById(adminId);
        var entryUser = usersRepo.findById(userID);
        if(entryAdmin.isEmpty()){
            return WRONG_ADMIN_ID;
        }
        if(entryUser.isEmpty()){
            return WRONG_USER_ID;
        }
        User admin = entryAdmin.get();
        User user = entryUser.get();
        if(!admin.getIsAdmin()){
            return USER_DELETING_ERROR;
        }
        if(user.getUserId()==0){
            return MASTER_ADMIN_REMOVE_ERROR;
        }
        if(user.getIsAdmin() && admin.getUserId() != 0){
            return MASTER_ADMIN_ONLY_ERROR;
        }
        usersRepo.deleteById(Long.valueOf(user.getUserId()));
        return USER_DELETED;
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
            return WRONG_USER_ID;
        }
        return user.get().getJoinDate().toString();
    }

    public String changeUserName(long id, String newName) {
        var userEntry = usersRepo.findById(id);
        if(userEntry.isEmpty())
            return WRONG_USER_ID;
        User user = userEntry.get();
        user.setUserName(newName);
        usersRepo.save(user);
        return USER_CHANGE_NAME;
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
        List<RequestProduct> listProduct = requestProductRepo.findByUserId(entryUser.get().getUserId());
        for(RequestProduct it: listProduct){
            listProductFront.add(new RequestProductAdapter(requestProductRepo, usersRepo)
                    .dataToFront(it));
        }
        return listProductFront;

    }
    // three functions to handle get all requests to admin and accept one and refuse one......
    public List<ProductFront> getAllRequestProducts() {
        List<ProductFront> listProductFront = new ArrayList<>();
        List<RequestProduct> listProduct = (List<RequestProduct>) requestProductRepo.findAll();
        for(RequestProduct it: listProduct){
            listProductFront.add(new RequestProductAdapter(requestProductRepo, usersRepo)
                    .dataToFront(it));
        }
        return listProductFront;
    }


    public String acceptProductById(Long id) {
        Optional<RequestProduct> reqProduct = requestProductRepo.findById(id);
        ProductManagementService productManagementService = new ProductManagementService(repo);
        if(reqProduct.isEmpty()){
            return PRODUCT_NOT_FOUND;
        }
        RequestProduct requestProduct = reqProduct.get();
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
        deleteProductById(id);
        if(productManagementService.addNewProduct(product)){
            return "Accept success.";
        }else{
            return "Accept Failed..";
        }
    }
   // For Pet
    public boolean addRequestPet(requestPet reqPet) {
        if(reqPet == null) return false;
        if(requestPetRepo.existsInDB(reqPet.getName(),reqPet.getType(),reqPet.getBreed())) return false;
        var entry = usersRepo.findByEmail(reqPet.getUserEmail());
        if(entry.isEmpty()){
            return false;
        }
        requestPetRepo.save(reqPet);
        return true;
    }

    public List<requestPet> getRequestPetsByUserEmail(String email) {
        return requestPetRepo.findByUserEmail(email);
    }

    public List<requestPet> getAllRequestPets() {
        return  requestPetRepo.findAll();
    }

    public String acceptPetById(Long id) {
        Optional<requestPet> dataPet = requestPetRepo.findById(id);
        PetManagementService petManagementService = new PetManagementService(petRepository);
        if(dataPet.isEmpty()){
            return PET_NOT_FOUND;
        }
        requestPet reqPet = dataPet.get();
        Pet pet = new Pet();
        pet.setAge(reqPet.getAge());
        pet.setDescription(reqPet.getDescription());
        pet.setName(reqPet.getName());
        pet.setBreed(reqPet.getBreed());
        pet.setType(reqPet.getType());
        pet.setImageLink(reqPet.getImageLink());
        pet.setBirthDate(reqPet.getBirthDate());
        requestPetRepo.deleteById(id);
        if(petManagementService.addPet(pet)){
            return "Accept success.";
        }else{
            return "Accept Failed..";
        }
    }

    public String refuseRequestPetById(Long id) {
        Optional<requestPet> requestPet = requestPetRepo.findById(id);
        if (requestPet.isEmpty()) {
            return PET_NOT_FOUND;
        }
        requestPetRepo.deleteById(id);
        return PRODUCT_DELETE_SUCCESS;
    }
    public List<UserFront> getAllUsers() {
        return usersRepo.findAllUsers()
                .stream()
                .map(u -> UserFront.getUserFront(u))
                .toList();
    }

    public List<UserFront> searchAllUsers(String email) {
        return usersRepo.findAllUsersByEmail(email)
                .stream()
                .map(u -> UserFront.getUserFront(u))
                .toList();
    }

    public List<UserFront> getUsers() {
        return usersRepo.findByIsAdminFalse()
                .stream()
                .map(u -> UserFront.getUserFront(u))
                .toList();
    }

    public List<UserFront> getAdmins() {
        return usersRepo.findByIsAdminTrue()
                .stream()
                .map(u -> UserFront.getUserFront(u))
                .toList();
    }
    public List<UserFront> searchUsers(String email) {
        return usersRepo.findUsersByEmail(email)
                .stream()
                .map(u -> UserFront.getUserFront(u))
                .toList();
    }

    public List<UserFront> searchAdmins(String email) {
        return usersRepo.findAdminsByEmail(email)
                .stream()
                .map(u -> UserFront.getUserFront(u))
                .toList();
    }
}
