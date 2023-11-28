package com.example.PVSSpringBoot.RepoTest;

import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepoTest {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    public void UserRepoTest(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @AfterEach
    public void wipeDB(){
        usersRepo.deleteAll();
    }
    @Test
    public void testAddingAUserAndGettingItsValuesByitsEmail() {
        User x = User.builder().email("kono").join_date(new Date(10L)).is_admin(false).user_name("dsjbasdkb").build();
        usersRepo.save(x);
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUser_id()).isEqualTo(x.getUser_id());
        Assertions.assertThat(result.get().getEmail()).isEqualTo(x.getEmail());
        Assertions.assertThat(result.get().getUser_name()).isEqualTo(x.getUser_name());


    }

    @Test
    public void testDeleteingAUserByItsId() {
        User x = User.builder().email("kono").is_admin(false).join_date(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.deleteById(Long.valueOf(x.getUser_id()));
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void testDeleteingAUserByItsEmail() {
        User x = User.builder().email("kono").is_admin(false).join_date(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.deleteByEmail("kono");
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void TestChangingEmail() {
        User x = User.builder().email("kono").is_admin(false).user_name("dashbk").join_date(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.updateEmail("admin","kono");
        Optional<User> result  = usersRepo.findByEmail("admin");
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUser_id()).isEqualTo(x.getUser_id());
        Assertions.assertThat(result.get().getEmail()).isEqualTo("admin");
        Assertions.assertThat(result.get().getUser_name()).isEqualTo(x.getUser_name());
    }

    @Test
    public void testChangingPassword(){
        User x = User.builder().email("kono").user_name("dashbk").is_admin(false).join_date(new Date(100L)).password("07775000").build();
        usersRepo.save(x);
        usersRepo.changePassword("55005",x.getEmail(),x.getPassword());
        Optional<User> result  = usersRepo.findByEmail("kono");
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getPassword()).isEqualTo("55005");
    }

    @Test
    public  void testReturningAdmins(){
        User admin = User.builder().is_admin(true).email("admin").join_date(new Date(10L)).build();
        User user1 = User.builder().is_admin(false).email("kimo").join_date(new Date(10L)).build();
        User user2 = User.builder().is_admin(false).email("kono").join_date(new Date(10L)).build();

        usersRepo.save(admin);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findAdmins();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("admin");
    }

    @Test
    public void testFindingAllNonNativeUsers(){
        User nativeUser = User.builder().is_admin(true).email("admin").password("7777").join_date(new Date(10L)).build();
        User user1 = User.builder().is_admin(false).email("kimo").join_date(new Date(10L)).build();
        User user2 = User.builder().is_admin(false).email("kono").join_date(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findNonNativeUsers();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("kimo");
        Assertions.assertThat(result.get(1).getEmail()).isEqualTo("kono");
    }

    @Test
    public  void testReturningNonAdmins(){
        User admin = User.builder().is_admin(true).email("admin").join_date(new Date(10L)).build();
        User user1 = User.builder().is_admin(false).email("kimo").join_date(new Date(10L)).build();
        User user2 = User.builder().is_admin(false).email("kono").join_date(new Date(10L)).build();

        usersRepo.save(admin);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findNormalUsers();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("kimo");
        Assertions.assertThat(result.get(1).getEmail()).isEqualTo("kono");
    }

    @Test
    public void testFindingAllNativeUsers(){
        User nativeUser = User.builder().is_admin(true).email("admin").password("7777").join_date(new Date(10L)).build();
        User user1 = User.builder().is_admin(false).email("kimo").join_date(new Date(10L)).build();
        User user2 = User.builder().is_admin(false).email("kono").join_date(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findNativeUsers();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("admin");
    }


}
