package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetFront {

    private Long id;

    private String name;

    private String type;

    private String breed;

    private String description;

    private Date birthDate;

    private short age;

    private String imageLink;

    private String userEmail;

    public PetFront(Pet original) {
        this.id = original.getId();
        this.name = original.getName();
        this.type = original.getType();
        this.breed = original.getBreed();
        this.description = original.getDescription();
        this.birthDate = original.getBirthDate();
        this.age = original.getAge();
        this.imageLink = original.getImageLink();
        this.userEmail = original.getUser() != null ? original.getUser().getEmail():null;
    }
}
