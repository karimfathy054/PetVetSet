package com.example.PVSSpringBoot.ControllerPackage;


import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.Entities.UserFront;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {

    @Autowired
    private UsersRepo usersRepo;

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

    public String getJoinDate(long id) {
        var user = usersRepo.findById(id);
        if(user.isEmpty()){
            return "Wrong Id!!";
        }
        return user.get().getJoin_date().toString();
    }

    public String changeUserName(long id, String newName) {
        var userEntry = usersRepo.findById(id);
        if(userEntry.isEmpty())
            return "Wrong Id!!";
        User user = userEntry.get();
        user.setUser_name(newName);
        usersRepo.save(user);
        return "User Saved Successfully!!";
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
