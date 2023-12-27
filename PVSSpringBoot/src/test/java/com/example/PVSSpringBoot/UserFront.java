package com.example.PVSSpringBoot;

import com.example.PVSSpringBoot.Entities.UserFront;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserFrontTest {
    @Test
    public void classTest(){
        UserFront user = new UserFront(1, "omar", "om@gmail.com", false,"");
        Assertions.assertThat(user.getUser_name()).isEqualTo("omar");
        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getEmail()).isEqualTo("om@gmail.com");
        Assertions.assertThat(user.isIs_admin()).isEqualTo(false);
        user.setUser_name("ahmed");
        user.setEmail("omomom@gmail.com");
        user.setId(2);
        user.setIs_admin(true);
    }
}
