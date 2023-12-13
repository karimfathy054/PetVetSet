package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "breed")
    private String breed;

    @Column(name = "description")
    private String description;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Transient
    private short age;

    @Column(name = "image_link")
    private String imageLink;

    @PostLoad
    public void postLoad() {
        this.age = (short)Period.between(birthDate.toLocalDate(),LocalDate.now()).getYears();
    }



}
