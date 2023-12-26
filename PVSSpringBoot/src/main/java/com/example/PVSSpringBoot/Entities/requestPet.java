package com.example.PVSSpringBoot.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requestPet")
public class requestPet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "petId", nullable = false)

    private Long productId;

    @Column(name = "userEmail", nullable = false, length = 30)
    private String userEmail;

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
        this.age = (short) Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears();
    }



}
