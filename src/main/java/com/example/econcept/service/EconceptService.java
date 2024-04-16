package com.example.econcept.service;


import com.example.econcept.dto.ResponseResult;
import com.example.econcept.dto.CartDto;
import com.example.econcept.dto.ProductDto;
import com.example.econcept.model.Customer;
import com.example.econcept.model.Product;

import java.util.List;

public interface EconceptService {
    Customer createCustomer(Customer customer);
    ResponseResult<List<Customer>> readCustomer();
    Customer readCustomer(int customerId);
    Customer updateCustomer(int customerId, Customer customer);
    ResponseResult<Boolean> deleteCustomer(int customerId);

    Product createProduct(Product product);
    List<ProductDto> readProduct();

    List<ProductDto> readProduct(String pageCount, String pageSize);
    Product readProduct(int productId);
    Product updateProduct(int productId, Product product);
    boolean deleteProduct(int productId);


    ResponseResult<Integer> createCart(int customerId);
    boolean addToCart(int basketId, int productId);
    List<ProductDto> productsInCart(int basketId);
    List<ProductDto> productsInCart1(int basketId);

    boolean addToBasket(int basketId, int productId);

    List<ProductDto> productsInBasket(int basketId);

    List<ProductDto> productsInBasket1(int basketId);

    ResponseResult<List<CartDto>> customerBasketProducts(int customerId);

    ResponseResult<Boolean> deleteCustomerBasket(int customerId);


    ResponseResult<Boolean> deleteCustomerCart(int customerId);
}
