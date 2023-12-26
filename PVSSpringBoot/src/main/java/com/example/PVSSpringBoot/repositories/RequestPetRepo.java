package com.example.PVSSpringBoot.repositories;
import com.example.PVSSpringBoot.Entities.requestPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestPetRepo extends JpaRepository<requestPet, Long> {


    @Query("""
            select (count(p) > 0) from requestPet p
            where upper(p.name) like upper(?1) and upper(p.type) like upper(?2) and upper(p.breed) like upper(?3)""")
    boolean existsInDB(String name, String type, String breed);
    @Query("select p from requestPet p where p.userEmail like ?1")
    List<requestPet> findByUserEmail(String UserEmail);
}
