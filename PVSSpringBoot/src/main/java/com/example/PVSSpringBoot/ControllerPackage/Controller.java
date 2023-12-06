package com.example.PVSSpringBoot.ControllerPackage;


import com.example.PVSSpringBoot.Entities.UserFront;


import org.springframework.web.bind.annotation.*;


import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;




@RestController
@CrossOrigin()
@RequestMapping("/api")
public class Controller {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RequestService requestService;

    @GetMapping("/hello")
    UserFront hello(){
        return new UserFront(-1, "Hello", "", false);
    }

//    @PostMapping("/api/signup")
//    UserFront signUp(@RequestBody Map<String, String> body){
//        var e = usersRepo.findByEmail(body.get("email"));
//        if(!e.isEmpty()){
//            return new UserFront(-1, "Email already used", "", false);
//        }
//        var now = LocalDateTime.now();
//        Date date = new Date(now.getYear()-1900, now.getMonth().getValue()-1, now.getDayOfMonth());
//        User user = User.builder()
//                .email(body.get("email"))
//                .join_date(date)
//                .is_admin(false)
//                .password(body.get("password"))
//                .user_name(body.get("user_name"))
//                .build();
//        User x = usersRepo.save(user);
//        return new UserFront(user.getUser_id(), user.getUser_name(), user.getEmail(), user.getIs_admin());
//    }
//    @PostMapping("/api/login")
//    public UserFront logIn(@RequestBody Map<String, String> body){
//        return requestService.login(body.get("email"), body.get("password"));
//    }
    @PostMapping("/setAdmin")
    public String setAdmin(@RequestBody Map<String, Long> body){
        return requestService.setAdmin(body.get("admin"), body.get("user"));
    }
    @PostMapping("/removeAdmin")
    public String removeAdminAccess(@RequestBody Map<String, Long> body){
        return requestService.removeAdminAccess(body.get("admin"), body.get("user"));
    }
    @PostMapping("/getUser")
    public UserFront getUser(@RequestBody Map<String, Integer> body){
        return requestService.getUser(body.get("id"));
    }
    @PostMapping("/getUserByEmail")
    public UserFront getUserByEmail(@RequestBody Map<String, String> body){
        return requestService.getUserByEmail(body.get("email"));
    }
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestBody Map<String, Long> body){
        return requestService.deleteUser(body.get("admin"), body.get("user"));
    }


//    @GetMapping("/oauthLogin")
//    String authLogin(){
//        System.out.println("HERE :PGIN");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String auth = authentication.toString();
//        AOuth2Service aOuth2Service = new AOuth2Service(usersRepo);
//        return aOuth2Service.BuildOAuthSignIn(auth);
//    }
//    @GetMapping("/oauthSignUp")
//    String authSignUp(){
//        System.out.println("HERE SING");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String auth = authentication.toString();
//
//        AOuth2Service aOuth2Service = new AOuth2Service(usersRepo);
//
//        return aOuth2Service.BuildOAuthSignUp(auth);
//    }

}
