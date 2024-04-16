package com.example.econcept.repository;

import com.example.econcept.model.CartProduct;
import com.example.econcept.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct,Integer> {

    @Query("Select bp from CartProduct bp where bp.basket.id = :basketId and bp.product.id= :productId")
    Optional<CartProduct> findByBasketAndProduct(int basketId, int productId);

    @Query("Select bp.product from CartProduct bp where bp.basket.id = :basketId")
    List<Product> findProductIBasket(int basketId);

    @Query("Delete from  CartProduct bp where bp.basket.id = :basketId")
    void deleteProductIBasket(int basketId);
}