package com.example.PVSSpringBoot;

import com.example.PVSSpringBoot.Entities.User;

import com.example.PVSSpringBoot.Entities.UserFront;


import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

import com.example.PVSSpringBoot.OAuth2.AOuth2Service;

import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;


import java.sql.Date;

import java.util.Map;

@RestController
public class Controller {
//    UsersRepo UserRepo;


import java.sql.Date;

@RestController
public class Controller {
    @Autowired
    private UsersRepo usersRepo;



    @PostMapping(path = "/signup")
    String signUp(@RequestBody Map<String, String> body){
        var e = usersRepo.findByEmail(body.get("email"));
        if(!e.isEmpty()){
            return "Email already used";
        }
        var now = LocalDateTime.now();
        Date date = new Date(now.getYear()-1900, now.getMonth().getValue()-1, now.getDayOfMonth());
        User user = User.builder()
            .email(body.get("email"))
            .join_date(date)
            .is_admin(false)
            .password(body.get("password"))
            .user_name(body.get("user_name"))
            .build();
        User x = usersRepo.save(user);
        return x.toString();
    }
    @GetMapping("/login")
    public UserFront logIn(@RequestBody Map<String, String> body){
        var entry = usersRepo.findByEmail(body.get("email"));
        if(entry.isEmpty()){
            return new UserFront(-1, "Email not valid", "", false);
        }
        User user = entry.get();
        if(!user.getPassword().equals(body.get("password"))){
                return new UserFront(-1, "Password is wrong", "", false);
        }
        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
    }
    @PostMapping("/setadmin")
    public String setAdmin(@RequestBody Map<String, Long> body){
        var enrtyAdmin = usersRepo.findById(body.get("admin"));
        var enrtyUser = usersRepo.findById(body.get("user"));
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
    @PostMapping("/removeadmin")
    public String removeAdminAccess(@RequestBody Map<String, Long> body){
        var enrtyAdmin = usersRepo.findById(body.get("admin"));
        var enrtyUser = usersRepo.findById(body.get("user"));
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
    @GetMapping("/getuser")
    public UserFront getUser(@RequestBody Map<String, Integer> body){
        var entry = usersRepo.findById(Long.valueOf(body.get("id")));
        if(entry.isEmpty()){
            return new UserFront(-1, "Id not valid", "", false);
        }
        User user = entry.get();
        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
    }
    @GetMapping("/getuserbyemail")
    public UserFront getUserByEmail(@RequestBody Map<String, String> body){
        var entry = usersRepo.findByEmail(body.get("email"));
        if(entry.isEmpty()){
            return new UserFront(-1, "Email not valid", "", false);
        }
        User user = entry.get();
        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
    }
    @DeleteMapping("/deleteuser")
    public String deleteUser(@RequestBody Map<String, Long> body){
        var enrtyAdmin = usersRepo.findById(body.get("admin"));
        var enrtyUser = usersRepo.findById(body.get("user"));
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


    @GetMapping("/oauthLogin")
    String authLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.toString();
        AOuth2Service aOuth2Service = new AOuth2Service(usersRepo);
        return aOuth2Service.BuildOAuthSignIn(auth);
    }
    @GetMapping("/oauthSignUp")
    String authSignUp(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.toString();

        AOuth2Service aOuth2Service = new AOuth2Service(usersRepo);

        return aOuth2Service.BuildOAuthSignUp(auth);
    }

}
