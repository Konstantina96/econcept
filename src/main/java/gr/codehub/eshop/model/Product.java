package gr.codehub.eshop.model;

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

    private String  category;
    private double new_price;
    private double  old_price;

    private double price;
    private int repoQuantity;
    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProducts;
}
