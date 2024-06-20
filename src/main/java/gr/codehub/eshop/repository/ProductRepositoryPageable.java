package gr.codehub.eshop.repository;

import gr.codehub.eshop.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepositoryPageable  extends PagingAndSortingRepository<Product, Integer> {
}