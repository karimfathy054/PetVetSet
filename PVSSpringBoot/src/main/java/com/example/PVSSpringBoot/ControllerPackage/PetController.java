package com.example.PVSSpringBoot.ControllerPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.PVSSpringBoot.Entities.Pet;
import com.example.PVSSpringBoot.services.PetManagementService;

import java.util.List;


@RestController
@RequestMapping("/pet")
@CrossOrigin
public class PetController {

    @Autowired
    PetManagementService service;

    @GetMapping("/all")
    public List<Pet> getAll(){
        return service.getAll();
    }

    @PostMapping("/add")
    public String addPet(@RequestBody Pet pet){
        boolean status = service.addPet(pet);
        if(status) return "your pet is added";
        else return "Can not add Your Pet";
    }

    @DeleteMapping("/id={id}")
    public String removePet(@PathVariable long id){
        if(service.removePet(id)) return "pet is remove successfully";
        return "Can not remove pet";
    }

    @GetMapping("/id={id}")
    public Pet findByID(@PathVariable long id){
        return service.getById(id);
    }

    @GetMapping("/name={name}")
    public List<Pet> findByName(@PathVariable String name){
        return service.getByName(name);
    }

    @GetMapping("/type={type}")
    public List<Pet> findByType(@PathVariable String type){
        return service.findByType(type);
    }

    @GetMapping("/breed={breed}")
    public List<Pet> findByBreed(@PathVariable String breed){
        return service.findByBreed(breed);
    }

    @GetMapping("/age<={age}")
    public List<Pet> findByAge(@PathVariable int age){
        return service.findByAgeYoungerThanOrEqual(age);
    }

    @GetMapping("/age>={youngAge}&&age<={oldAge}")
    public List<Pet> findByAgeBetween(@PathVariable int youngAge,@PathVariable int oldAge){
        return service.findByAgeBetween(youngAge,oldAge);
    }

}
