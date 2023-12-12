package com.example.PVSSpringBoot.PetTest;

import org.assertj.core.api.Assertions;
import static  org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static  org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.PVSSpringBoot.Entities.Pet;
import com.example.PVSSpringBoot.repositories.PetRepository;
import com.example.PVSSpringBoot.services.PetManagementService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PetManagementServiceTest {
    @Mock
    PetRepository repo;
    @Autowired
    @InjectMocks
    PetManagementService service;

    @Test
    void testAddingAPet(){
        Pet pet = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.EPOCH)).build();
        Mockito.when(repo.existsInDB(pet.getName(), pet.getType(), pet.getBreed()))
                .thenReturn(false);
        Mockito.when(repo.save(pet)).thenReturn(pet);
        assertThat(service.addPet(pet)).isTrue();
        verify(repo,Mockito.times(1)).existsInDB(anyString(),anyString(),anyString());
        verify(repo,Mockito.times(1)).save(pet);
    }

    @Test
    void testAddingAnExistantPet(){
        Pet pet = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.EPOCH)).build();
        Mockito.when(repo.existsInDB(pet.getName(), pet.getType(), pet.getBreed()))
                .thenReturn(true);
        assertThat(service.addPet(pet)).isFalse();
        verify(repo,Mockito.times(1)).existsInDB(anyString(),anyString(),anyString());
    }

    @Test
    void testRemovePet(){
        Mockito.when(repo.existsById(1L)).thenReturn(true);
        assertThat(service.removePet(1L)).isTrue();
        verify(repo,Mockito.times(1)).existsById(1L);
    }
    @Test
    void testRemoveNonExistantPet(){
        Mockito.when(repo.existsById(1L)).thenReturn(false);
        assertThat(service.removePet(1L)).isFalse();
        verify(repo,Mockito.times(1)).existsById(1L);

    }

    @Test
    void testGetById(){
        Pet p = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.EPOCH)).build();
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(p));
        assertThat(service.getById(1L).getName()).isEqualTo("pet");
        verify(repo, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetByNonExistantId(){//return empty Pet object
        Mockito.when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThat(service.getById(1L).getName()).isNull();
        verify(repo, Mockito.times(1)).findById(1L);

    }

    @Test
    void testGetByName(){
        Pet p = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.EPOCH)).build();
        Mockito.when(repo.findByNameContainsIgnoreCase("pet")).thenReturn(List.of(p));
        List<Pet> queryResult = service.getByName("pet");
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet");
        verify(repo,Mockito.times(1)).findByNameContainsIgnoreCase("pet");
    }

    @Test
    void testGetByNonExistantName(){//return empty Pet object
        Mockito.when(repo.findByNameContainsIgnoreCase("pet")).thenReturn(Collections.emptyList());
        List<Pet> queryResult = service.getByName("pet");
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testGetByType(){
        Pet p = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.EPOCH)).build();
        Mockito.when(repo.findByTypeLikeIgnoreCase("cat")).thenReturn(List.of(p));
        List<Pet> queryResult = service.findByType("cat");
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet");
        verify(repo,Mockito.times(1)).findByTypeLikeIgnoreCase("cat");
    }

    @Test
    void testGetByNonExistantType(){//return empty Pet object
        Mockito.when(repo.findByTypeLikeIgnoreCase("cat")).thenReturn(Collections.emptyList());
        List<Pet> queryResult = service.findByType("cat");
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testGetByBreed(){
        Pet p = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.EPOCH)).build();
        Mockito.when(repo.findByBreedLikeIgnoreCase("persian")).thenReturn(List.of(p));
        List<Pet> queryResult = service.findByBreed("persian");
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet");
        verify(repo,Mockito.times(1)).findByBreedLikeIgnoreCase("persian");
    }

    @Test
    void testGetByNonExistantBreed(){//return empty Pet object
        Mockito.when(repo.findByBreedLikeIgnoreCase("persian")).thenReturn(Collections.emptyList());
        List<Pet> queryResult = service.findByBreed("persian");
        assertThat(queryResult.size()).isEqualTo(0);
    }

    @Test
    void testfindByAgeLessThanOrEqual(){
        Pet p = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2020,6,7)))
                .build();
        List<Pet> mockAnswer = List.of(p);
        Mockito.when(repo.findByBirthDateAfter(Date.valueOf(LocalDate.now().minusYears(3))))
                .thenReturn(mockAnswer);
        List<Pet> queryResult = service.findByAgeYoungerThanOrEqual(3L);
        assertThat(queryResult.size()).isEqualTo(1);;
        assertThat(queryResult).isEqualTo(mockAnswer);;
        verify(repo,Mockito.times(1)).findByBirthDateAfter(Date.valueOf(LocalDate.now().minusYears(3)));
    }

    @Test
    void testfindByAgeLessThanOrEqualWithNoValues(){
        List<Pet> mockAnswer = Collections.emptyList();
        Mockito.when(repo.findByBirthDateAfter(Date.valueOf(LocalDate.now().minusYears(3))))
                .thenReturn(mockAnswer);
        List<Pet> queryResult = service.findByAgeYoungerThanOrEqual(3L);
        assertThat(queryResult.size()).isEqualTo(0);;
        assertThat(queryResult).isEqualTo(mockAnswer);;
        verify(repo,Mockito.times(1))
                .findByBirthDateAfter(Date.valueOf(LocalDate.now().minusYears(3)));
    }

    @Test
    void testfindByAgeBetweenTwoValues(){
        Pet p = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2020,6,7)))
                .build();
        List<Pet> mockAnswer = List.of(p);
        Mockito.when(repo.findByBirthDateBetween(Date.valueOf(LocalDate.now().minusYears(2)),Date.valueOf(LocalDate.now().minusYears(5))))
                .thenReturn(mockAnswer);
        List<Pet> queryResult = service.findByAgeBetween(2L,5L);
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult).isEqualTo(mockAnswer);
        verify(repo,Mockito.times(1))
                .findByBirthDateBetween(Date.valueOf(LocalDate.now().minusYears(2)),Date.valueOf(LocalDate.now().minusYears(5)));
    }

    @Test
    void testfindByAgeBetweenTwoValuesNonExistantRange(){
        List<Pet> mockAnswer = Collections.emptyList();
        Mockito.when(repo.findByBirthDateBetween(Date.valueOf(LocalDate.now().minusYears(2)),Date.valueOf(LocalDate.now().minusYears(5))))
                .thenReturn(mockAnswer);
        List<Pet> queryResult = service.findByAgeBetween(2L,5L);
        assertThat(queryResult.size()).isEqualTo(0);;
        assertThat(queryResult).isEqualTo(mockAnswer);;
        verify(repo,Mockito.times(1))
                .findByBirthDateBetween(Date.valueOf(LocalDate.now().minusYears(2)),Date.valueOf(LocalDate.now().minusYears(5)));
    }

    @Test
    void testGetAll(){
        Pet p1 = Pet.builder()
                .name("pet1")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2020,6,7)))
                .build();
        Pet p2 = Pet.builder()
                .name("pet2")
                .type("dog")
                .breed("dalmatian")
                .birthDate(Date.valueOf(LocalDate.of(2018,6,7)))
                .build();
        List<Pet> mockAnswer = List.of(p1,p2);
        Mockito.when(repo.findAll()).thenReturn(mockAnswer);
        List<Pet> queryResult = service.getAll();
        assertThat(queryResult.size()).isEqualTo(2);
        assertThat(queryResult.get(0).getName()).isEqualTo("pet1");
        verify(repo,Mockito.times(1)).findAll();
    }

}
