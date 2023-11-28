package com.example.PVSSpringBoot.OAuth2;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
import java.util.Optional;

public class AOuth2Service {
    UsersRepo usersRepo;
    @Autowired
    public AOuth2Service(UsersRepo uusersRepo) {
        this.usersRepo = uusersRepo;
    }
public String BuildOAuthSignIn(String OAuth){
    String[] arrOfStr = OAuth.split("Credentials=");
    String[] arrOfStr2 = arrOfStr[0].split("email=");
    String[] arrOfStr3 = arrOfStr[0].split("name=");
    arrOfStr3 = arrOfStr3[1].split(",");
    String[] arrOfStr4 = arrOfStr[0].split("family_name=");
    arrOfStr4 = arrOfStr4[1].split(",");
    String email= arrOfStr[0].substring(arrOfStr2[0].length()+6,arrOfStr[0].length()-4);
    return this.LogInOAuthUser(email,arrOfStr3[0],arrOfStr4[0]);
}
    public String BuildOAuthSignUp(String OAuth){
        String[] arrOfStr = OAuth.split("Credentials=");
        String[] arrOfStr2 = arrOfStr[0].split("email=");
        String[] arrOfStr3 = arrOfStr[0].split("name=");
        arrOfStr3 = arrOfStr3[1].split(",");
        String[] arrOfStr4 = arrOfStr[0].split("family_name=");
        arrOfStr4 = arrOfStr4[1].split(",");
        String email= arrOfStr[0].substring(arrOfStr2[0].length()+6,arrOfStr[0].length()-4);
        return this.SignUpOAuthUser(email,arrOfStr3[0],arrOfStr4[0]);
    }
    public String LogInOAuthUser(String email, String Name1, String Name2){
        String date = java.time.LocalDate.now().toString();
        User x = User.builder().email(email).join_date(Date.valueOf(date)).is_admin(false).user_name(Name1+" "+Name2).build();
        Optional<User> result = usersRepo.findByEmail(email);
        if(!result.isPresent()){
            return " User Not Found!!!!";
        }
        else{
            return " User Found and Email :"+email;
        }
    }
    public String SignUpOAuthUser(String email, String Name1, String Name2){
        String date = java.time.LocalDate.now().toString();
        User x = User.builder().email(email).join_date(Date.valueOf(date)).is_admin(false).user_name(Name1+" "+Name2).build();
        Optional<User> result = usersRepo.findByEmail(email);
        if(result.isPresent()){
            return "User is already existing !!!!";
        }else{
            usersRepo.save(x);
            return "Success Add in DataBase";
        }
    }
}
