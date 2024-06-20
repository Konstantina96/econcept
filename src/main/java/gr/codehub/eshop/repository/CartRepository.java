package gr.codehub.eshop.repository;

import gr.codehub.eshop.model.Cart;
import gr.codehub.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


    @Query( value ="Select bp.product from CartProduct bp where bp.cart.id = :cartId")
    List<Product> getProductsFromCart(int cartId);

    @Query( value ="Select c from Cart c where c.customer.id = :customerId")
    List<Cart> findCartByCustomerId(int customerId);

}
