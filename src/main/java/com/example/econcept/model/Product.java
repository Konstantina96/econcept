package com.example.econcept.model;

import com.example.econcept.model.CartProduct;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;
    private String name;
    private double price;
    @OneToMany(mappedBy = "product")
    private List<CartProduct> CartProductList;
}
