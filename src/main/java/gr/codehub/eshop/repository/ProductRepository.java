package gr.codehub.eshop.repository;

import gr.codehub.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "Select p from Product p  join  p.cartProducts bp   where bp.cart.id = :cartId")
    List<Product> findAllProduct(int cartId);

}
