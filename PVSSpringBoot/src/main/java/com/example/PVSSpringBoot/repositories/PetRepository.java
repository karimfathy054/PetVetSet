package com.example.PVSSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.PVSSpringBoot.Entities.Pet;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNameContainsIgnoreCase(String name);

    List<Pet> findByTypeLikeIgnoreCase(String type);

    List<Pet> findByBreedLikeIgnoreCase(String breed);


    List<Pet> findByBirthDateBetween(Date birthDateStart, Date birthDateEnd);

    List<Pet> findByBirthDateAfter(Date birthDate);

    @Query("""
            select (count(p) > 0) from Pet p
            where upper(p.name) like upper(?1) and upper(p.type) like upper(?2) and upper(p.breed) like upper(?3)""")
    boolean existsInDB(String name, String type, String breed);


}