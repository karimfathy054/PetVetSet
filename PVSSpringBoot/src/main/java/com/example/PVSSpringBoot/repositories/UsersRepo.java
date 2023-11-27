package com.example.PVSSpringBoot.repositories;

import com.example.PVSSpringBoot.Entities.Native_Auth_User;
import com.example.PVSSpringBoot.Entities.User;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UsersRepo extends CrudRepository<User,Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.email = ?1 where u.email like ?2")
    void updateEmail(String email, String newEmail);
    @Transactional
    @Modifying
    @Query("delete from User u where u.email like ?1")
    void deleteByEmail(String email);


    Optional<User> findByEmail(String email);


}
