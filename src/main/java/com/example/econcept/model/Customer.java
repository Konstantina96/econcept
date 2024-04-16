package com.example.econcept.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private int age;
    private LocalDateTime registrationDate;
    private CustomerType customerType;


    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

}