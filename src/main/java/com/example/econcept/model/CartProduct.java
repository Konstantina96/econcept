package com.example.econcept.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;

    @ManyToOne
    private Cart basket;
    @ManyToOne
    private Product product;
}