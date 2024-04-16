package com.example.econcept.repository;

import com.example.econcept.model.Cart;
import com.example.econcept.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


    @Query( value ="Select bp.product from CartProduct bp where bp.basket.id = :basketId")
    List<Product> getProductsFromBasket(int basketId);

    @Query( value ="Select c from Cart c where c.customer.id = :customerId")
    List<Cart> findCartByCustomerId(int customerId);

}
