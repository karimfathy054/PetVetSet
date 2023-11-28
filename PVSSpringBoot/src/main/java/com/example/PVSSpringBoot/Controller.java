package com.example.PVSSpringBoot;

import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class Controller {

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

}
