package com.example.PVSSpringBoot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PVSSpringBoot.Entities.Pet;
import com.example.PVSSpringBoot.repositories.PetRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetManagementService {
    @Autowired
    PetRepository repo;

    @Autowired
    public PetManagementService(PetRepository repo) {
        this.repo = repo;
    }

    //add a pet
    public boolean addPet(Pet pet){
        if(pet == null) return false;
        if(repo.existsInDB(pet.getName(),pet.getType(),pet.getBreed())) return false;
        repo.save(pet);
        return true;
    }

    //delete a pet
    public boolean removePet(Long id){
        if(id == null) return false;
        if(!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    //find a pet by id
    public Pet getById(Long id){
        if(id == null) return null;
        Optional<Pet> result = repo.findById(id);
        return result.orElse(new Pet());
    }
    //find a pet by name
    public List<Pet> getByName(String name){
        if(name == null) return Collections.emptyList();
        return repo.findByNameContainsIgnoreCase(name);
    }
    //find a pet by type
    public List<Pet> findByType(String type){
        if(type == null) return Collections.emptyList();
        return repo.findByTypeLikeIgnoreCase(type);
    }
    //find a pet by breed
    public List<Pet> findByBreed(String breed){
        if(breed == null) return Collections.emptyList();
        return repo.findByBreedLikeIgnoreCase(breed);
    }
    //find a pet by age
    public List<Pet> findByAgeYoungerThanOrEqual(long age){
        Date birthDate = Date.valueOf(LocalDate.now().minusYears(age));
        return repo.findByBirthDateAfter(birthDate);
    }

    public List<Pet> findByAgeBetween(long youngAge,long oldAge){
        Date oldBirthDate = Date.valueOf(LocalDate.now().minusYears(oldAge));
        Date youngBirthDate = Date.valueOf(LocalDate.now().minusYears(youngAge));
        return repo.findByBirthDateBetween(youngBirthDate,oldBirthDate);
    }
    //return all pets
    public List<Pet> getAll(){
        return repo.findAll();
    }

}
