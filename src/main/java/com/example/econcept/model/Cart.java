package com.example.econcept.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    @JsonIgnore
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "Cart")
    private List<CartProduct> CartProductList;
}
