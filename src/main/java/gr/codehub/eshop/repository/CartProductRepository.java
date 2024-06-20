package gr.codehub.eshop.repository;

import gr.codehub.eshop.model.CartProduct;
import gr.codehub.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct,Integer> {

    @Query("Select bp from CartProduct bp where bp.cart.id = :cartId and bp.product.id= :productId")
    Optional<CartProduct> findByCartAndProduct(int cartId, int productId);

    @Query("Select bp.product from CartProduct bp where bp.cart.id = :cartId")
    List<Product> findProductInCart(int cartId);

    @Query("Delete from  CartProduct bp where bp.cart.id = :cartId")
    void deleteProductInCart(int cartId);
}