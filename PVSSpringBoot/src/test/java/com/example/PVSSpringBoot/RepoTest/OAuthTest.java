//package com.example.PVSSpringBoot.RepoTest;
//import com.example.PVSSpringBoot.repositories.UsersRepo;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//public class OAuthTest {
//
//    @Autowired
//    private UsersRepo usersRepo;
//
//    @Autowired
//    public void UserRepoTest(UsersRepo usersRepo) {
//        this.usersRepo = usersRepo;
//    }
//    // @AfterEach
//    // public void wipeDB(){
//    //     usersRepo.deleteAll();
//    // }
//
//// -------Test for SignUp for User New in database
//
//    @Test
//    public void Test_for_SignUp_for_User_New_in_data_base() {
//        AOuth2Service aouth2Service = new AOuth2Service(this.usersRepo);
//        String res = aouth2Service.SignUpOAuthUser("abdelrahmanelsayd223@gmail.com","abdelrahman","elsayd");
//        assertEquals("Success Add in DataBase",res);
//
//    }
//
//    // -------Test for Login for User already in database
//    @Test
//    public void Test_for_Login_for_User_already_in_data_base() {
//        AOuth2Service aouth2Service = new AOuth2Service(this.usersRepo);
//        String res = aouth2Service.LogInOAuthUser("abdelrahmanelsayd223@gmail.com","abdelrahman","elsayed");
//        assertEquals(" User Found and Email :abdelrahmanelsayd223@gmail.com",res);
//
//    }
//// -------Test for Login for User Not in database
//
//    @Test
//    public void Test_for_Login_for_User_Not_in_data_base() {
//        AOuth2Service aouth2Service = new AOuth2Service(this.usersRepo);
//        String res = aouth2Service.LogInOAuthUser("abdelrahmanelsayd222@gmail.com","abdelrahman","elsayed");
//        assertEquals(" User Not Found!!!!",res);
//
//    }
//
//// -------Test for SignUp for User already in database
//
//    @Test
//    public void Test_for_SignUp_for_already_in_data_base() {
//        AOuth2Service aouth2Service = new AOuth2Service(this.usersRepo);
//        String res = aouth2Service.SignUpOAuthUser("abdelrahmanelsayd223@gmail.com","abdelrahman","elsayd");
//        assertEquals("User is already existing !!!!",res);
//
//    }
//
//
//
//}
