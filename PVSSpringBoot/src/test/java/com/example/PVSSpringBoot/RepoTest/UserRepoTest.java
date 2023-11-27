package com.example.PVSSpringBoot.RepoTest;

import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepoTest {

    UsersRepo usersRepo;

    @Autowired
    public UserRepoTest(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Test
    public void testAddingAUserAndGettingItsValuesByitsEmail() {
        User x = User.builder().email("kono").is_admin(false).join_date(new Date(100L)).build();
        usersRepo.save(x);
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(x);
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
        User x = User.builder().email("kono").is_admin(false).join_date(new Date(100L)).build();
        usersRepo.save(x);
        usersRepo.updateEmail("kono","admin");
        Optional<User> result  = usersRepo.findByEmail(x.getEmail());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(x);
    }

}
