package com.example.econcept.repository;

import com.example.econcept.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "Select p from Product p  join  p.CartProductList bp   where bp.basket.id = :basketId")
    List<Product> findAllProduct(int basketId);

}
