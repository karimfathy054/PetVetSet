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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepoTest {


    UsersRepo usersRepo;

    @Autowired
    public UserRepoTest(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @AfterEach
    public void wipeDB(){
        usersRepo.deleteAll();
    }

    @Test
    public void testAddingAUserAndGettingItsValuesByitsEmail() {
        User x = User.builder().email("kono").joinDate(new Date(10L)).isAdmin(false).userName("dsjbasdkb").build();
        usersRepo.save(x);
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUserId()).isEqualTo(x.getUserId());
        Assertions.assertThat(result.get().getEmail()).isEqualTo(x.getEmail());
        Assertions.assertThat(result.get().getUserName()).isEqualTo(x.getUserName());
    }

    @Test
    public void testDeletingAUUserByItsId() {
        User x = User.builder().email("kono").userName("kimo").isAdmin(false).joinDate(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.deleteById(x.getUserId());
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void testDeletingAUUserByItsEmail() {
        User x = User.builder().email("kono").userName("kimo").isAdmin(false).joinDate(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.deleteByEmail("kono");
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void TestChangingEmail() {
        User x = User.builder().email("kono").isAdmin(false).userName("dashbk").joinDate(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.updateEmail("admin","kono");
        Optional<User> result  = usersRepo.findByEmail("admin");
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUserId()).isEqualTo(x.getUserId());
        Assertions.assertThat(result.get().getEmail()).isEqualTo("admin");
        Assertions.assertThat(result.get().getUserName()).isEqualTo(x.getUserName());
    }

    @Test
    public void testChangingPassword(){
        User x = User.builder().email("kono").userName("dashbk").isAdmin(false).joinDate(new Date(100L)).password("07775000").build();
        usersRepo.save(x);
        usersRepo.changePassword("55005",x.getEmail(),x.getPassword());
        Optional<User> result  = usersRepo.findByEmail("kono");
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getPassword()).isEqualTo("55005");
    }

    @Test
    public  void testReturningAdmins(){
        User admin = User.builder().userName("kimo").isAdmin(true).email("admin").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("kimo").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(false).email("kono").joinDate(new Date(10L)).build();

        usersRepo.save(admin);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findByIsAdminTrue();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("admin");
    }

    @Test
    public void testFindingAllNonNativeUsers(){
        User nativeUser = User.builder().userName("kimo").isAdmin(true).email("admin").password("7777").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("kimo").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(false).email("kono").joinDate(new Date(10L)).build();

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
        User admin = User.builder().userName("kimo").isAdmin(true).email("admin").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("kimo").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(false).email("kono").joinDate(new Date(10L)).build();

        usersRepo.save(admin);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findByIsAdminFalse();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("kimo");
        Assertions.assertThat(result.get(1).getEmail()).isEqualTo("kono");
    }

    @Test
    public void testFindingAllNativeUsers(){
        User nativeUser = User.builder().userName("kimo").isAdmin(true).email("admin").password("7777").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("kimo").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(false).email("kono").joinDate(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findNativeUsers();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("admin");
    }

    @Test
    void testFindAllUsers(){
        User nativeUser = User.builder().userName("kimo").isAdmin(true).email("admin").password("7777").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("Kimo").isAdmin(false).email("Kimo").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("Kimo").isAdmin(false).email("Kono").joinDate(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findAllUsers();
        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("admin");
        Assertions.assertThat(result.get(1).getEmail()).isEqualTo("Kimo");
        Assertions.assertThat(result.get(2).getEmail()).isEqualTo("Kono");
    }

    @Test
    void testSearchUsersByEmail(){
        User nativeUser = User.builder().userName("kimo").isAdmin(true).email("admin").password("7777").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("Karem").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(false).email("Omar").joinDate(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findUsersByEmail("re");
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("Karem");
    }
    @Test
    void testSearchAdminsByEmail(){
        User nativeUser = User.builder().userName("kimo").isAdmin(true).email("admin").password("7777").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("Karem").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(true).email("Omar").joinDate(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findAdminsByEmail("in");
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("admin");
    }

    @Test
    void testSearchAllUsersByEmail(){
        User nativeUser = User.builder().userName("kimo").isAdmin(true).email("Admin").password("7777").joinDate(new Date(10L)).build();
        User user1 = User.builder().userName("kimo").isAdmin(false).email("Karem").joinDate(new Date(10L)).build();
        User user2 = User.builder().userName("kimo").isAdmin(false).email("Omar").joinDate(new Date(10L)).build();

        usersRepo.save(nativeUser);
        usersRepo.save(user1);
        usersRepo.save(user2);

        List<User> result = usersRepo.findAllUsersByEmail("ar");
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getEmail()).isEqualTo("Karem");
        Assertions.assertThat(result.get(1).getEmail()).isEqualTo("Omar");
    }

}
