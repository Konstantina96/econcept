package com.example.econcept.repository;

import com.example.econcept.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepositoryPageable  extends PagingAndSortingRepository<Product, Integer> {
}