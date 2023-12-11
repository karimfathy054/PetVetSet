package com.example.PVSSpringBoot.PetTest;


import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.PVSSpringBoot.Entities.Pet;
import com.example.PVSSpringBoot.repositories.PetRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PetRepoTest {
    
    @Autowired
    PetRepository repo;

    @Test
    void populateDB(){
        Pet p1 = Pet.builder()
                .name("pet1")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2019,5,6)))
                .build();
        Pet p2 = Pet.builder()
                .name("pet2")
                .type("dog")
                .breed("labrador")
                .birthDate(Date.valueOf(LocalDate.of(2018,5,6)))
                .build();
        Pet p3 = Pet.builder()
                .name("pet3")
                .type("dog")
                .breed("golden retriever")
                .birthDate(Date.valueOf(LocalDate.of(2020,5,6)))
                .build();
        repo.save(p1);
        repo.save(p2);
        repo.save(p3);
        Assertions.assertThat(repo.count()).isEqualTo(3);
    }

    @Test
    void testFindByNameExistant(){
        List<Pet> queryResult = repo.findByNameLikeIgnoreCase("pet1");
        Assertions.assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet1");
        assertThat(queryResult.get(0).getType()).isEqualTo("cat");
    }
    @Test
    void testFindByNameNonExistant(){
        List<Pet> queryResult = repo.findByNameLikeIgnoreCase("pet5");
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testFindBYTypeExistant(){
        List<Pet> queryResult = repo.findByTypeLikeIgnoreCase("dog");
        Assertions.assertThat(queryResult.size()).isEqualTo(2);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet2");
        assertThat(queryResult.get(1).getName()).isEqualTo("pet3");
    }
    @Test
    void testFindBYTypeNonExistant(){
        List<Pet> queryResult = repo.findByTypeLikeIgnoreCase("bird");
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testFindByBreed(){
        List<Pet> queryResult = repo.findByBreedLikeIgnoreCase("golden retriever");
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet3");
    }

    @Test
    void testFindByNonExistantBreed(){
        List<Pet> queryResult = repo.findByBreedLikeIgnoreCase("dalmatian");
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testFindByDateOfBirthBetweenRange(){
        List<Pet> queryResult = repo.findByBirthDateBetween(Date.valueOf(LocalDate.of(2019,5,6)),Date.valueOf(LocalDate.of(2020,5,6)));
        assertThat(queryResult.size()).isEqualTo(2);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet1");
        assertThat(queryResult.get(1).getName()).isEqualTo("pet3");
    }

    @Test
    void testFindByDateOfBirthBetweenWrongRange(){
        List<Pet> queryResult = repo.findByBirthDateBetween(Date.valueOf(LocalDate.of(2021,5,6)),Date.valueOf(LocalDate.of(2022,5,6)));
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testExistanceInDB(){
        boolean result = repo.existsInDB("pet1","cat","persian");
        assertThat(result).isTrue();
    }

    @Test
    void testNonExistanceInDB(){
        boolean result = repo.existsInDB("pet1","cat","siamese");
        assertThat(result).isFalse();
    }

}