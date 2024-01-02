package com.example.PVSSpringBoot.ControllerPackage;


import com.example.PVSSpringBoot.Entities.Pet;
import com.example.PVSSpringBoot.Entities.ProductFront;
import com.example.PVSSpringBoot.Entities.ProductFrontBuilder;
import com.example.PVSSpringBoot.Entities.UserFront;


import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.PVSSpringBoot.Entities.*;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Controller {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RequestService requestService;

    @PutMapping("/addImage/user={userId}&&image={image}")
    public Boolean addImage(@PathVariable Long userId, @PathVariable String image){
        return requestService.changeImage(userId,image);
    }

    @PostMapping("/setAdmin")
    public String setAdmin(@RequestBody Map<String, Long> body){
        return requestService.setAdmin(body.get("admin"), body.get("user"));
    }
    @PostMapping("/removeAdmin")
    public String removeAdminAccess(@RequestBody Map<String, Long> body){
        return requestService.removeAdminAccess(body.get("admin"), body.get("user"));
    }
    @GetMapping("/getUserById/{id}")
    public UserFront getUser(@PathVariable long id){
        return requestService.getUserById(id);
    }
    @PostMapping("/getUserByEmail")
    public UserFront getUserByEmail(@RequestBody Map<String, String> body){
        return requestService.getUserByEmail(body.get("email"));
    }
    @GetMapping("/getJoinDate/{id}")
    public String getJoinDate(@PathVariable long id){
        Gson G = new Gson();
        return G.toJson(requestService.getJoinDate(id));
    }
    @PostMapping("/changeUserName")
    public ResponseEntity<String> changeUserName(@RequestBody Map<String, String> body){
        return ResponseEntity.ok(requestService.changeUserName(Long.parseLong(body.get("id")), body.get("newName")));
    }
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestBody Map<String, Long> body){
        return requestService.deleteUser(body.get("admin"), body.get("user"));
    }

    @GetMapping("/getAllUsers")
    public List<UserFront> getAllUsers(){
        return requestService.getAllUsers();
    }
    @GetMapping("/searchAllUsers/{email}")
    public List<UserFront> searchAllUsers(@PathVariable String email){
        return requestService.searchAllUsers(email);
    }
    @GetMapping("/getUsers")
    public List<UserFront> getUsers(){
        return requestService.getUsers();
    }
    @GetMapping("/searchUsers/{email}")
    public List<UserFront> searchUsers(@PathVariable String email){
        return requestService.searchUsers(email);
    }

    @GetMapping("/getAdmins")
    public List<UserFront> getAdmins(){
        return requestService.getAdmins();
    }

    @GetMapping("/searchAdmins/{email}")
    public List<UserFront> searchAdmins(@PathVariable String email){
        return requestService.searchAdmins(email);
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@RequestBody Map<String, String> body){System.out.println("111");
        //in RequestService
        //public String addNewProduct(ProductFront)
        return requestService.addNewProduct(
                new ProductFrontBuilder()
                        .setProductName(body.get("name"))
                        .setApproved(false)
                        .setBrandName(body.get("brandName"))
                        .setDescription(body.get("description"))
                        .setPrice(Float.parseFloat(body.get("price")))
                        .setTargetAnimal(body.get("targetAnimal"))
                        .setCategoryName(body.get("categoryName"))
                        .setUserEmail(body.get("userEmail"))
                        .setProductPhoto(body.get("photo"))
                        .get()
        );
    }
    @GetMapping("/getProductByUser")
    public List<ProductFront> getRequestProductsByUserEmail(@RequestBody Map<String, String> body){
        return requestService.getProductsByUserEmail(body.get("email"));
    }

    // three functions to handle get all requests to admin and accept one and refuse one
    @PostMapping("/deleteRequestProduct")
    public String deleteRequestProductById(@RequestBody Map<String, Long> body){
        return requestService.deleteProductById(body.get("id"));
    }
    @PostMapping("/checkOutCart")
    public boolean checkOutCart(@RequestBody Map<String, List<JsonNode>> body){
        return requestService.checkOutCart(
                body.get("list").stream().map(node->{
                    if(node.get("breed") != null){
                        //other data skipped, not needed
                        return new Object[]{
                                "pet",
                                node.get("id").asLong()
                        };
                    }else{
                        return new Object[]{
                                "product",
                                node.get("id").asLong()
                        };
                    }
                }).toList()
        );
    }
    @GetMapping("/getAllRequestProducts")
    public List<ProductFront> getAllRequestProducts(){
        return requestService.getAllRequestProducts();
    }
    @PostMapping("/acceptRequestProduct")
    public String acceptRequestProduct(@RequestBody Map<String, Long> body){
        return requestService.acceptProductById(body.get("id"));
    }
//     For Pet
    @PostMapping("/addNewPet")
    public String addNewPet(@RequestBody Map<String, String> body){
        requestPet reqpet = new requestPet();
        reqpet.setName(body.get("name"));
        reqpet.setBreed(body.get("breed"));
        reqpet.setDescription(body.get("description"));
        reqpet.setImageLink(body.get("image_link"));
        reqpet.setUserEmail(body.get("userEmail"));
        reqpet.setType(body.get("type"));
        reqpet.setBirthDate(Date.valueOf(body.get("birthDate")));
        boolean status = requestService.addRequestPet(reqpet);
        if(status) return "your request pet is added";
        else return "Can not add Your request Pet";
    }

    @GetMapping("/getRequestPetByUserEmail")
    public List<requestPet> getRequestPetsByUserEmail(@RequestBody Map<String, String> body){
        return requestService.getRequestPetsByUserEmail(body.get("email"));
    }

     //three functions to handle get all requests to admin and accept one and refuse one.......

    @GetMapping("/getAllRequestPets")
    public List<requestPet> getAllRequestPets(){
        return requestService.getAllRequestPets();
    }
    @PostMapping("/acceptRequestPet")
    public String acceptRequestPet(@RequestBody Map<String, Long> body){
        return requestService.acceptPetById(body.get("id"));
    }
    @PostMapping("/refuseRequestPet")
    public String refuseRequestPetById(@RequestBody Map<String, Long> body){
        return requestService.refuseRequestPetById(body.get("id"));
    }
}