package com.example.PVSSpringBoot.OAuth2;

import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.stereotype.Service;


import java.sql.Date;
@Service
public class AOuth2Service {


    com.example.PVSSpringBoot.repositories.UsersRepo usersRepo ;

    public void InsertOAuthUser(String OAuth){
        String[] arrOfStr = OAuth.split("Credentials=");
        String[] arrOfStr2 = arrOfStr[0].split("email=");
        String[] arrOfStr3 = arrOfStr[0].split("name=");
        arrOfStr3 = arrOfStr3[1].split(",");
        String[] arrOfStr4 = arrOfStr[0].split("family_name=");
        arrOfStr4 = arrOfStr4[1].split(",");
        String UserName="" ;
        String email= arrOfStr[0].substring(arrOfStr2[0].length()+6,arrOfStr[0].length()-4);
        User x = User.builder().email(email).join_date(new Date(10L)).is_admin(false).user_name(arrOfStr3[0]+" "+arrOfStr4[0]).build();

        usersRepo.save(x);
    }
}
