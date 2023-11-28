package com.example.PVSSpringBoot;

import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.OAuth2.AOuth2Service;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
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
    @Autowired
    private UsersRepo usersRepo;

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "hello World";
    }

    @GetMapping(path = "/add")
    String add(){
        User user = User.builder().user_id(10).email("kono").join_date(new Date(10000L)).is_admin(false).build();
        User x = usersRepo.save(user);
        return x.toString();
    }

    @GetMapping("/del")
    String del(){
        usersRepo.deleteById(504L);
        return "Done";
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
